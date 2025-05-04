package com.aetherteam.aetherii.attachment.living;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import com.aetherteam.aetherii.network.packet.DamageSystemSyncPacket;
import com.aetherteam.aetherii.network.packet.clientbound.DamageTypeParticlePacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DamageSystemAttachment implements INBTSynchable {
    public static final int MAX_SHIELD_STAMINA = 500;
    private float criticalDamageModifier = 1.0F;
    private int shieldStamina = MAX_SHIELD_STAMINA;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setShieldStamina", Triple.of(Type.INT, (object) -> this.setShieldStamina((int) object), this::getShieldStamina))
    );

    public DamageSystemAttachment() { }

    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    public void postTickUpdate(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            this.restoreShieldStamina(player);
        }
    }

    public void restoreShieldStamina(Player player) {
        if (!player.level().isClientSide()) {
            DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
            if (player.tickCount % 5 == 0) {
                if (attachment.getShieldStamina() < DamageSystemAttachment.MAX_SHIELD_STAMINA && attachment.getShieldStamina() > 0) { //todo balance
                    if (!player.isBlocking()) {
                        attachment.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setShieldStamina", Math.min(500, attachment.getShieldStamina() + 2));
                    }
                }
            }
        }
    }

    public void buildUpShieldStun(LivingEntity entity, DamageSource source) {
        if (entity instanceof Player player && player.getUseItem().is(Tags.Items.TOOLS_SHIELD)) {
            if (source.getEntity() != null && source.getEntity().getType().is(AetherIITags.Entities.AETHER_MOBS)) {
                int rate = DamageSystemAttachment.MAX_SHIELD_STAMINA / 2;
                int cooldown;
                if (entity.getUseItem().getItem() instanceof TieredShieldItem) {
                    rate = (int) player.getAttributeValue(AetherIIAttributes.SHIELD_STAMINA_REDUCTION);
                    cooldown = (int) player.getAttributeValue(AetherIIAttributes.SHIELD_COOLDOWN_REDUCTION);
                } else {
                    cooldown = 0;
                }
                this.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setShieldStamina", Math.max(0, this.getShieldStamina() - rate));
                if (this.getShieldStamina() <= 0) {
                    player.level().registryAccess().lookupOrThrow(Registries.ITEM).getTagOrEmpty(Tags.Items.TOOLS_SHIELD).forEach((item) -> player.getCooldowns().addCooldown(item.value().getDefaultInstance(), 300 - cooldown));
                    player.stopUsingItem();
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
                    baseDamage = livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
                    slashDamage.set(livingEntity.getAttributes().hasAttribute(AetherIIAttributes.SLASH_DAMAGE) ? livingEntity.getAttributeValue(AetherIIAttributes.SLASH_DAMAGE) : 0.0);
                    impactDamage.set(livingEntity.getAttributes().hasAttribute(AetherIIAttributes.IMPACT_DAMAGE) ? livingEntity.getAttributeValue(AetherIIAttributes.IMPACT_DAMAGE) : 0.0);
                    pierceDamage.set(livingEntity.getAttributes().hasAttribute(AetherIIAttributes.PIERCE_DAMAGE) ? livingEntity.getAttributeValue(AetherIIAttributes.PIERCE_DAMAGE) : 0.0);
                } else if (source.getDirectEntity() instanceof AbstractArrow abstractArrow && source.getEntity() instanceof LivingEntity && !abstractArrow.getWeaponItem().isEmpty()) {
                    ItemStack weapon = abstractArrow.getWeaponItem();
                    ItemAttributeModifiers modifiers = weapon.getAttributeModifiers();
                    baseDamage = abstractArrow.getBaseDamage();
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
                    this.createSoundsAndParticles(directEntity, target, slashDamage.get(), slashDefense, AetherIIParticleTypes.SLASH_ATTACK.get(), AetherIISoundEvents.PLAYER_SLASH_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_SLASH_DAMAGE_INCORRECT.get());
                    this.createSoundsAndParticles(directEntity, target, impactDamage.get(), impactDefense, AetherIIParticleTypes.IMPACT_ATTACK.get(), AetherIISoundEvents.PLAYER_IMPACT_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_IMPACT_DAMAGE_INCORRECT.get());
                    this.createSoundsAndParticles(directEntity, target, pierceDamage.get(), pierceDefense, AetherIIParticleTypes.PIERCE_ATTACK.get(), AetherIISoundEvents.PLAYER_PIERCE_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_PIERCE_DAMAGE_INCORRECT.get());

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

    private void createSoundsAndParticles(Entity source, Entity target, double damage, double defense, SimpleParticleType particleType, SoundEvent correct, SoundEvent incorrect) {
        if (damage > 0) {
            if (defense > 0) {
                source.level().playSound(null, source.getX(), source.getY(), source.getZ(), incorrect, source.getSoundSource(), 1.0F, 1.0F);
            } else if (defense < 0) {
                if (source.level() instanceof ServerLevel serverLevel) {
                    PacketDistributor.sendToPlayersNear(serverLevel, null, source.getX(), source.getY(), source.getZ(), 15,  new DamageTypeParticlePacket(target.getId(), particleType));
                }
                source.level().playSound(null, source.getX(), source.getY(), source.getZ(), correct, source.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }

    public void setCriticalDamageModifier(float criticalDamageModifier) {
        this.criticalDamageModifier = criticalDamageModifier;
    }

    public float getCriticalDamageModifier() {
        return this.criticalDamageModifier;
    }

    public void setShieldStamina(int shieldStamina) {
        this.shieldStamina = shieldStamina;
    }

    public int getShieldStamina() {
        return this.shieldStamina;
    }

    @Override
    public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new DamageSystemSyncPacket(entityID, key, type, value);
    }
}
