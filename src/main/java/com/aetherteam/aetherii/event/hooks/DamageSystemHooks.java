package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageInflictions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageResistances;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.combat.abilities.UniqueDamage;
import com.aetherteam.aetherii.network.packet.clientbound.DamageTypeParticlePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class DamageSystemHooks {
    public static void trackCriticalHitValue(Player player, float value) {
        player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM.get()).setCriticalDamageModifier(value);
    }

    public static float getDamageTypeModifiedValue(Entity target, DamageSource source, double damage) {
        if (source.typeHolder().is(AetherIITags.DamageTypes.TYPED)) {
            RegistryAccess registryAccess = target.level().registryAccess();
            Entity sourceEntity = source.getDirectEntity();
            ItemStack sourceStack = ItemStack.EMPTY;

            if (sourceEntity instanceof LivingEntity livingSource) {
                sourceStack = livingSource.getMainHandItem();
            } else if (sourceEntity instanceof Projectile && sourceEntity instanceof ItemSupplier itemSupplier) {
                sourceStack = itemSupplier.getItem();
            }

            if (!sourceStack.isEmpty()) {
                double slashDamage = AetherIIDamageInflictions.getSlashDamage(registryAccess, sourceStack);
                double impactDamage = AetherIIDamageInflictions.getImpactDamage(registryAccess, sourceStack);
                double pierceDamage = AetherIIDamageInflictions.getPierceDamage(registryAccess, sourceStack);

                double slashDefense = AetherIIDamageResistances.getSlashDefense(registryAccess, target);
                double impactDefense = AetherIIDamageResistances.getImpactDefense(registryAccess, target);
                double pierceDefense = AetherIIDamageResistances.getPierceDefense(registryAccess, target);

                if (target.getType().is(AetherIITags.Entities.UNIQUE_DAMAGE_RESISTANCES)) {
                    if (sourceStack.is(AetherIITags.Items.UNIQUE_DAMAGE_INFLICTIONS)) {
                        if (sourceStack.getItem() instanceof UniqueDamage uniqueDamage) {
                            Triple<Double, Double, Double> damages = uniqueDamage.getUniqueDamage(sourceStack, slashDamage, impactDamage, pierceDamage);
                            slashDamage += damages.getLeft();
                            impactDamage += damages.getMiddle();
                            pierceDamage += damages.getRight();
                        }

                        createSoundsAndParticles(sourceEntity, target, slashDamage, slashDefense, AetherIIParticleTypes.SLASH_ATTACK.get(), AetherIISoundEvents.PLAYER_SLASH_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_IMPACT_DAMAGE_INCORRECT.get());
                        createSoundsAndParticles(sourceEntity, target, impactDamage, impactDefense, AetherIIParticleTypes.IMPACT_ATTACK.get(), AetherIISoundEvents.PLAYER_IMPACT_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_IMPACT_DAMAGE_INCORRECT.get());
                        createSoundsAndParticles(sourceEntity, target, pierceDamage, pierceDefense, AetherIIParticleTypes.PIERCE_ATTACK.get(), AetherIISoundEvents.PLAYER_PIERCE_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_PIERCE_DAMAGE_INCORRECT.get());

                        double slashCalculation = slashDamage > 0.0 ? Math.max(slashDamage - slashDefense, 0.0) : 0.0;
                        double impactCalculation = impactDamage > 0.0 ? Math.max(impactDamage - impactDefense, 0.0) : 0.0;
                        double pierceCalculation = pierceDamage > 0.0 ? Math.max(pierceDamage - pierceDefense, 0.0) : 0.0;

                        damage = Math.max(slashCalculation + impactCalculation + pierceCalculation, 1.0);

                        AetherII.LOGGER.info(slashDamage + " - " + slashDefense);
                        AetherII.LOGGER.info(impactDamage + " - " + impactDefense);
                        AetherII.LOGGER.info(pierceDamage + " - " + pierceDefense);
                        AetherII.LOGGER.info(String.valueOf(damage));

                        if (sourceEntity instanceof Player player) {
                            damage *= player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).getCriticalDamageModifier();
                            damage *= player.getAttackStrengthScale(0.5F);

                            player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).setCriticalDamageModifier(1.0F);
                        }
                    } else {
                        double resistance = Math.max(slashDefense, Math.max(impactDefense, pierceDefense));
                        damage = Math.max(damage - resistance, 1.0F);
                    }
                }
            }
        }
        return (float) damage;
    }

    private static void createSoundsAndParticles(Entity source, Entity target, double damage, double defense, SimpleParticleType particleType, SoundEvent correct, SoundEvent incorrect) {
        if (damage > 0) {
            if (defense > 0) {
                source.level().playSound(null, source.getX(), source.getY(), source.getZ(), incorrect, source.getSoundSource(), 1.0F, 1.0F);
            } else if (defense < 0) {
                PacketRelay.sendToNear(new DamageTypeParticlePacket(target.getId(), particleType), source.getX(), source.getY(), source.getZ(), 15, source.level().dimension());
                source.level().playSound(null, source.getX(), source.getY(), source.getZ(), correct, source.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }

    public static void addAbilityTooltips(ItemStack stack, List<Component> components) {
        for (int i = 1; i <= 5; i++) {
            String string = stack.getDescriptionId() + "." + AetherII.MODID + ".ability.tooltip." + i;
            if (I18n.exists(string)) {
                components.add(i, Component.translatable(string));
            }
        }
    }

    public static void addDamageTypeTooltips(Player player, List<Component> components, ItemStack stack) {
        if (player != null) {
            RegistryAccess registryAccess = player.level().registryAccess();

            int position = components.size();
            Component damageText = Component.translatable(Attributes.ATTACK_DAMAGE.getDescriptionId());
            for (int i = 0; i < position; i++) {
                Component component = components.get(i);
                if (component.getString().contains(damageText.getString())) {
                    position = i + 1;
                    break;
                }
            }
            double slashDamage = AetherIIDamageInflictions.getSlashDamage(registryAccess, stack);
            double impactDamage = AetherIIDamageInflictions.getImpactDamage(registryAccess, stack);
            double pierceDamage = AetherIIDamageInflictions.getPierceDamage(registryAccess, stack);

            addDamageTypeTooltip(components, position, slashDamage, "slash");
            addDamageTypeTooltip(components, position, impactDamage, "impact");
            addDamageTypeTooltip(components, position, pierceDamage, "pierce");
        }
    }

    private static void addDamageTypeTooltip(List<Component> components, int position, double value, String name) {
        if (value > 0.0) {
            components.remove(position - 1);
            components.add(position, CommonComponents.space().append(Component.translatable("attribute.modifier.equals.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(value), Component.translatable("aether_ii.tooltip.item.damage." + name)).withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR)));
        }
    }

    public static void addBonusDamageTypeTooltips(Player player, List<Component> components, ItemStack stack) {
        if (player != null) {
            RegistryAccess registryAccess = player.level().registryAccess();
            if (stack.getItem() instanceof UniqueDamage uniqueDamage) {
                double slashDamage = AetherIIDamageInflictions.getSlashDamage(registryAccess, stack) - 1;
                double impactDamage = AetherIIDamageInflictions.getImpactDamage(registryAccess, stack) - 1;
                double pierceDamage = AetherIIDamageInflictions.getPierceDamage(registryAccess, stack) - 1;

                Triple<Double, Double, Double> damages = uniqueDamage.getUniqueDamage(stack, slashDamage, impactDamage, pierceDamage);
                slashDamage = damages.getLeft();
                impactDamage = damages.getMiddle();
                pierceDamage = damages.getRight();

                addBonusDamageTypeTooltip(components, slashDamage, "slash");
                addBonusDamageTypeTooltip(components, impactDamage, "impact");
                addBonusDamageTypeTooltip(components, pierceDamage, "pierce");
            }
        }
    }

    private static void addBonusDamageTypeTooltip(List<Component> components, double value, String name) {
        if (value > 0.0) {
            int position = components.size();
            Component damageText = Component.translatable(Attributes.ATTACK_DAMAGE.getDescriptionId());
            for (int i = position - 1; i >= 0; i--) {
                Component component = components.get(i);
                if (component.getString().contains(damageText.getString())) {
                    position = i + 1;
                    break;
                }
            }
            components.remove(position - 1);
            components.add(position, Component.literal("").append(Component.translatable("attribute.modifier.plus.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(value), Component.translatable("aether_ii.tooltip.item.damage." + name)).withStyle(ChatFormatting.BLUE)));
        }
    }
}
