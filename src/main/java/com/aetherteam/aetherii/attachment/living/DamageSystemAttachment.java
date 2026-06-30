package com.aetherteam.aetherii.attachment.living;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AbstractArrowAccessor;
import com.aetherteam.aetherii.network.packet.clientbound.DamageTypeParticlePacket;
import com.aetherteam.aetherii.network.packet.clientbound.ResistanceKnockbackPacket;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import com.aetherteam.aetherii.util.nbt.ValueInput;
import com.aetherteam.aetherii.util.nbt.ValueOutput;
import com.aetherteam.aetherii.util.nbt.ValueIOSerializable;
import com.aetherteam.aetherii.network.PacketDistributor;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class DamageSystemAttachment implements ValueIOSerializable {
    private static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

    private float criticalDamageModifier = 1.0F;
    private double shieldEndurance = 0;
    private int resistantEntity = -1;

    public static final StreamCodec<FriendlyByteBuf, DamageSystemAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, DamageSystemAttachment::getShieldEndurance,
            DamageSystemAttachment::new);

    protected DamageSystemAttachment(double shieldEndurance) {
        this.shieldEndurance = shieldEndurance;
    }

    public DamageSystemAttachment() { }

    public void onJoinLevel(Player player) {
        DamageSystemAttachment attachment = AetherIIDataAttachments.get(player, AetherIIDataAttachments.DAMAGE_SYSTEM);
        double maxEndurance = AetherIIAttributes.getMaxEndurance(player);
        if (attachment.shieldEndurance == 0) {
            attachment.setShieldEndurance(maxEndurance);
            AetherIIDataAttachments.sync(player, AetherIIDataAttachments.DAMAGE_SYSTEM);
        }
    }

    public void postTickUpdate(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
//            AetherII.LOGGER.info(String.valueOf(AetherIIAttributes.getMaxEndurance(player)));
            this.restoreShieldEndurance(player);
        }
    }

    public void restoreShieldEndurance(Player player) {
        if (!player.level().isClientSide()) {
            DamageSystemAttachment attachment = AetherIIDataAttachments.get(player, AetherIIDataAttachments.DAMAGE_SYSTEM);
            double maxEndurance = AetherIIAttributes.getMaxEndurance(player);
            double recovery = player.getAttributeValue(AetherIIAttributes.ENDURANCE_RECOVERY.get());
            if (attachment.getShieldEndurance() < maxEndurance && attachment.getShieldEndurance() > 0 && !player.isBlocking()) {
                attachment.setShieldEndurance(Math.min(maxEndurance, attachment.getShieldEndurance() + recovery));
                AetherIIDataAttachments.sync(player, AetherIIDataAttachments.DAMAGE_SYSTEM);
            }
        }
    }

    public void buildUpShieldStun(LivingEntity entity, Entity source, double damage) {
        if (entity instanceof Player player && isShield(player.getUseItem())) {
            if (source != null && source.getType().is(AetherIITags.EntityTypes.AETHER_MOBS)) {
                double maxEndurance = AetherIIAttributes.getMaxEndurance(player);
                double endurance = player.getAttributeValue(AetherIIAttributes.BLOCKING_STRENGTH.get());

                damage *= 10;
                endurance = damage - (damage * endurance);

                if (!(entity.getUseItem().getItem() instanceof TieredShieldItem)) {
                    endurance = maxEndurance / 2;
                }

                if (!player.level().isClientSide()) {
                    this.setShieldEndurance(Math.max(0, this.getShieldEndurance() - endurance));
                    AetherIIDataAttachments.sync(player, AetherIIDataAttachments.DAMAGE_SYSTEM);
                }
                if (this.getShieldEndurance() <= 0) {
                    addShieldCooldowns(player);
                    player.stopUsingItem();
                }
                if (player.level() instanceof ServerLevel && player instanceof ServerPlayer serverPlayer) {
                    AccessoryUtil.getFirst(player, AccessoryContainer.SlotType.HANDWEAR).ifPresent((stack) -> {
                        ItemStack copyStack = stack.copy();
                        stack.hurtAndBreak(1, player, brokenPlayer -> AccessoryUtil.breakAccessory(copyStack.getItem(), copyStack, serverPlayer));
                    });
                }
            }
        }
    }

    public float getDamageTypeModifiedValue(LivingEntity target, DamageSource source, double damage) {
        if (source.is(AetherIITags.DamageTypes.TYPED)) {
            Entity directEntity = source.getDirectEntity();
            boolean allowUntypedEquipment = AetherIIConfig.COMMON.allow_vanilla_equipment_in_aether.get()
                    && target.level().dimension() == AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL;

            double slashDefense = getAttributeValue(target, AetherIIAttributes.SLASH_RESISTANCE);
            double impactDefense = getAttributeValue(target, AetherIIAttributes.IMPACT_RESISTANCE);
            double pierceDefense = getAttributeValue(target, AetherIIAttributes.PIERCE_RESISTANCE);

            double baseDamage = Attributes.ATTACK_DAMAGE.getDefaultValue();
            AtomicDouble slashDamage = new AtomicDouble(0);
            AtomicDouble impactDamage = new AtomicDouble(0);
            AtomicDouble pierceDamage = new AtomicDouble(0);

            if (slashDefense != 0 || impactDefense != 0 || pierceDefense != 0) {
                if (source.getDirectEntity() instanceof LivingEntity livingEntity && !livingEntity.getMainHandItem().isEmpty()) {
                    baseDamage = livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    AttributeInstance damageAttribute = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
                    if (damageAttribute != null) {
                        AttributeModifier damageModifier = damageAttribute.getModifier(BASE_ATTACK_DAMAGE_UUID);
                        if (damageModifier != null) {
                            baseDamage -= damageModifier.getAmount();
                        }
                    }
                    slashDamage.set(getAttributeValue(livingEntity, AetherIIAttributes.SLASH_DAMAGE));
                    impactDamage.set(getAttributeValue(livingEntity, AetherIIAttributes.IMPACT_DAMAGE));
                    pierceDamage.set(getAttributeValue(livingEntity, AetherIIAttributes.PIERCE_DAMAGE));
                } else if (source.getDirectEntity() instanceof AbstractArrow abstractArrow && source.getEntity() instanceof LivingEntity) {
                    baseDamage = ((AbstractArrowAccessor) abstractArrow).aether$getBaseDamage();
                }
                if (slashDamage.get() != 0 || impactDamage.get() != 0 || pierceDamage.get() != 0) {
                    this.createFeedback(directEntity, target, slashDamage.get(), slashDefense, AetherIIParticleTypes.SLASH_DAMAGE.get(), AetherIISoundEvents.PLAYER_SLASH_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_SLASH_DAMAGE_INCORRECT.get());
                    this.createFeedback(directEntity, target, impactDamage.get(), impactDefense, AetherIIParticleTypes.IMPACT_DAMAGE.get(), AetherIISoundEvents.PLAYER_IMPACT_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_IMPACT_DAMAGE_INCORRECT.get());
                    this.createFeedback(directEntity, target, pierceDamage.get(), pierceDefense, AetherIIParticleTypes.PIERCE_DAMAGE.get(), AetherIISoundEvents.PLAYER_PIERCE_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_PIERCE_DAMAGE_INCORRECT.get());

                    double slashCalculation = slashDamage.get() > 0.0 ? Math.max(slashDamage.get() - slashDefense, 0.0) : 0.0;
                    double impactCalculation = impactDamage.get() > 0.0 ? Math.max(impactDamage.get() - impactDefense, 0.0) : 0.0;
                    double pierceCalculation = pierceDamage.get() > 0.0 ? Math.max(pierceDamage.get() - pierceDefense, 0.0) : 0.0;

                    damage = Math.max(baseDamage + slashCalculation + impactCalculation + pierceCalculation, baseDamage);

                    if (directEntity instanceof Player player) {
                        damage *= AetherIIDataAttachments.get(player, AetherIIDataAttachments.DAMAGE_SYSTEM).getCriticalDamageModifier();
                        damage *= player.getAttackStrengthScale(0.5F);

                        AetherIIDataAttachments.get(player, AetherIIDataAttachments.DAMAGE_SYSTEM).setCriticalDamageModifier(1.0F);
                    }
                } else if (!allowUntypedEquipment) {
                    double defense = Math.max(slashDefense, Math.max(impactDefense, pierceDefense));
                    damage = Math.max(damage - defense, baseDamage);
                }
            }
        }
        return (float) damage;
    }

    private static boolean isShield(ItemStack stack) {
        return stack.getItem() instanceof ShieldItem || stack.is(AetherIITags.Items.TOOLS_SHIELD);
    }

    private static void addShieldCooldowns(Player player) {
        player.getCooldowns().addCooldown(Items.SHIELD, 300);
        player.getCooldowns().addCooldown(AetherIIItems.SKYROOT_SHIELD.get(), 300);
        player.getCooldowns().addCooldown(AetherIIItems.BURRUKAI_PLATE_SHIELD.get(), 300);
        player.getCooldowns().addCooldown(AetherIIItems.ZANITE_SHIELD.get(), 300);
        player.getCooldowns().addCooldown(AetherIIItems.ARKENIUM_SHIELD.get(), 300);
        player.getCooldowns().addCooldown(AetherIIItems.GRAVITITE_SHIELD.get(), 300);
    }

    private static double getAttributeValue(LivingEntity entity, RegistryObject<Attribute> attribute) {
        Attribute value = attribute.get();
        return entity.getAttributes().hasAttribute(value) ? entity.getAttributeValue(value) : 0.0;
    }

    private void createFeedback(Entity source, Entity target, double damage, double defense, SimpleParticleType particleType, SoundEvent correct, SoundEvent incorrect) {
        if (damage > 0) {
            if (defense > 0) {
                this.resistantEntity = target.getId();
                if (source instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayer(serverPlayer, new ResistanceKnockbackPacket(serverPlayer.getId(), target.getId()));
                }
                source.level().playSound(null, source.getX(), source.getY(), source.getZ(), incorrect, source.getSoundSource(), 1.0F, 1.0F);
            } else if (defense < 0) {
                this.resistantEntity = -1;
                if (source.level() instanceof ServerLevel serverLevel) {
                    PacketDistributor.sendToPlayersNear(serverLevel, null, source.getX(), source.getY(), source.getZ(), 15,  new DamageTypeParticlePacket(target.getId(), particleType));
                }
                source.level().playSound(null, source.getX(), source.getY(), source.getZ(), correct, source.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }

    public boolean cancelKnockback(LivingEntity entity) {
        if (entity.getId() == this.resistantEntity) {
            this.resistantEntity = -1;
            return true;
        }
        return false;

    }

    public void setCriticalDamageModifier(float criticalDamageModifier) {
        this.criticalDamageModifier = criticalDamageModifier;
    }

    public float getCriticalDamageModifier() {
        return this.criticalDamageModifier;
    }

    public void setShieldEndurance(double shieldEndurance) {
        this.shieldEndurance = shieldEndurance;
    }

    public double getShieldEndurance() {
        return this.shieldEndurance;
    }

    @Override
    public void serialize(ValueOutput valueOutput) {
        valueOutput.putDouble("shield_endurance", this.shieldEndurance);
    }

    @Override
    public void deserialize(ValueInput valueInput) {
        this.setShieldEndurance(valueInput.getDoubleOr("shield_endurance", 0));
    }
}
