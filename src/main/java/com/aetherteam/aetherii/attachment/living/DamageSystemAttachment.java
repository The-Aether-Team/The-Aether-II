package com.aetherteam.aetherii.attachment.living;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AbstractArrowAccessor;
import com.aetherteam.aetherii.network.packet.clientbound.DamageTypeParticlePacket;
import com.aetherteam.aetherii.network.packet.clientbound.ResistanceKnockbackPacket;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

public class DamageSystemAttachment implements ValueIOSerializable {
    private float criticalDamageModifier = 1.0F;
    private double shieldEndurance = 0;
    private int resistantEntity = -1;

    public static final StreamCodec<RegistryFriendlyByteBuf, DamageSystemAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, DamageSystemAttachment::getShieldEndurance,
            DamageSystemAttachment::new);

    protected DamageSystemAttachment(double shieldEndurance) {
        this.shieldEndurance = shieldEndurance;
    }

    public DamageSystemAttachment() { }

    public void onJoinLevel(Player player) {
        DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
        double maxEndurance = AetherIIAttributes.getMaxEndurance(player);
        if (attachment.shieldEndurance == 0) {
            attachment.setShieldEndurance(maxEndurance);
            player.syncData(AetherIIDataAttachments.DAMAGE_SYSTEM);
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
            DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
            double maxEndurance = AetherIIAttributes.getMaxEndurance(player);
            double recovery = player.getAttributeValue(AetherIIAttributes.ENDURANCE_RECOVERY);
            if (attachment.getShieldEndurance() < maxEndurance && attachment.getShieldEndurance() > 0 && !player.isBlocking()) {
                attachment.setShieldEndurance(Math.min(maxEndurance, attachment.getShieldEndurance() + recovery));
                player.syncData(AetherIIDataAttachments.DAMAGE_SYSTEM);
            }
        }
    }

    public void buildUpShieldStun(LivingEntity entity, Entity source, double damage) {
        if (entity instanceof Player player && player.getUseItem().is(Tags.Items.TOOLS_SHIELD)) {
            if (source != null && source.typeHolder().is(AetherIITags.EntityTypes.AETHER_MOBS)) {
                double maxEndurance = AetherIIAttributes.getMaxEndurance(player);
                double endurance = player.getAttributeValue(AetherIIAttributes.BLOCKING_STRENGTH);

                damage *= 10;
                endurance = damage - (damage * endurance);

                if (!(entity.getUseItem().getItem() instanceof TieredShieldItem)) {
                    endurance = maxEndurance / 2;
                }

                if (!player.level().isClientSide()) {
                    this.setShieldEndurance(Math.max(0, this.getShieldEndurance() - endurance));
                    player.syncData(AetherIIDataAttachments.DAMAGE_SYSTEM);
                }
                if (this.getShieldEndurance() <= 0) {
                    player.level().registryAccess().lookupOrThrow(Registries.ITEM).getTagOrEmpty(Tags.Items.TOOLS_SHIELD).forEach((item) -> player.getCooldowns().addCooldown(item.value().getDefaultInstance(), 300));
                    player.stopUsingItem();
                }
                if (player.level() instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
                    AccessoryUtil.getFirst(player, AccessoryContainer.SlotType.HANDWEAR).ifPresent((stack) -> {
                        ItemStack copyStack = stack.copy();
                        stack.hurtAndBreak(1, serverLevel, player, item -> AccessoryUtil.breakAccessory(item, copyStack, serverPlayer));
                    });
                }
            }
        }
    }

    public float getDamageTypeModifiedValue(LivingEntity target, DamageSource source, double damage) {
        if (source.typeHolder().is(AetherIITags.DamageTypes.TYPED)) {
            Entity directEntity = source.getDirectEntity();

            double slashDefense = target.getAttributes().hasAttribute(AetherIIAttributes.SLASH_RESISTANCE) ? target.getAttributeValue(AetherIIAttributes.SLASH_RESISTANCE) : 0.0;
            double impactDefense = target.getAttributes().hasAttribute(AetherIIAttributes.IMPACT_RESISTANCE) ? target.getAttributeValue(AetherIIAttributes.IMPACT_RESISTANCE) : 0.0;
            double pierceDefense = target.getAttributes().hasAttribute(AetherIIAttributes.PIERCE_RESISTANCE) ? target.getAttributeValue(AetherIIAttributes.PIERCE_RESISTANCE) : 0.0;

            double baseDamage = Attributes.ATTACK_DAMAGE.value().getDefaultValue();
            AtomicDouble slashDamage = new AtomicDouble(0);
            AtomicDouble impactDamage = new AtomicDouble(0);
            AtomicDouble pierceDamage = new AtomicDouble(0);

            if (slashDefense != 0 || impactDefense != 0 || pierceDefense != 0) {
                if (source.getDirectEntity() instanceof LivingEntity livingEntity && !livingEntity.getMainHandItem().isEmpty()) {
                    baseDamage = livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    AttributeInstance damageAttribute = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
                    if (damageAttribute != null) {
                        AttributeModifier damageModifier = damageAttribute.getModifier(Item.BASE_ATTACK_DAMAGE_ID);
                        if (damageModifier != null) {
                            baseDamage -= damageModifier.amount();
                        }
                    }
                    slashDamage.set(livingEntity.getAttributes().hasAttribute(AetherIIAttributes.SLASH_DAMAGE) ? livingEntity.getAttributeValue(AetherIIAttributes.SLASH_DAMAGE) : 0.0);
                    impactDamage.set(livingEntity.getAttributes().hasAttribute(AetherIIAttributes.IMPACT_DAMAGE) ? livingEntity.getAttributeValue(AetherIIAttributes.IMPACT_DAMAGE) : 0.0);
                    pierceDamage.set(livingEntity.getAttributes().hasAttribute(AetherIIAttributes.PIERCE_DAMAGE) ? livingEntity.getAttributeValue(AetherIIAttributes.PIERCE_DAMAGE) : 0.0);
                } else if (source.getDirectEntity() instanceof AbstractArrow abstractArrow && source.getEntity() instanceof LivingEntity && abstractArrow.getWeaponItem() != null && !abstractArrow.getWeaponItem().isEmpty()) {
                    ItemStack weapon = abstractArrow.getWeaponItem();
                    ItemAttributeModifiers modifiers = weapon.getAttributeModifiers();
                    baseDamage = ((AbstractArrowAccessor) abstractArrow).aether$getBaseDamage();
                    modifiers.forEach(EquipmentSlotGroup.HAND, (attribute, modifier) -> {
                        if (attribute.getKey() != null) {
                            if (AetherIIAttributes.SLASH_RANGED_DAMAGE.is(attribute.getKey())) {
                                slashDamage.set(modifier.amount());
                            } else if (AetherIIAttributes.IMPACT_RANGED_DAMAGE.is(attribute.getKey())) {
                                impactDamage.set(modifier.amount());
                            } else if (AetherIIAttributes.PIERCE_RANGED_DAMAGE.is(attribute.getKey())) {
                                pierceDamage.set(modifier.amount());
                            }
                        }
                    });
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
                        damage *= player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).getCriticalDamageModifier();
                        damage *= player.getAttackStrengthScale(0.5F);

                        player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).setCriticalDamageModifier(1.0F);
                    }
                } else {
                    double defense = Math.max(slashDefense, Math.max(impactDefense, pierceDefense));
                    damage = Math.max(damage - defense, baseDamage);
                }
            }
        }
        return (float) damage;
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
