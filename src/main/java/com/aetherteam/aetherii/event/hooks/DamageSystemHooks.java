package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageInflictions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageResistances;
import com.aetherteam.aetherii.item.combat.abilities.UniqueDamage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
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

            boolean isTypedItem = (slashDamage + impactDamage + pierceDamage) > 0.0;
            boolean isTypedEntity = (slashDefense + impactDefense + pierceDefense) > 0.0;

            if (isTypedItem && isTypedEntity) {
                if (sourceStack.getItem() instanceof UniqueDamage uniqueDamage) {
                    Triple<Double, Double, Double> damages = uniqueDamage.getUniqueDamage(sourceStack, slashDamage, impactDamage, pierceDamage);
                    slashDamage += damages.getLeft();
                    impactDamage += damages.getMiddle();
                    pierceDamage += damages.getRight();
                }

                double slashCalculation = slashDamage - slashDefense;
                double impactCalculation = impactDamage - impactDefense;
                double pierceCalculation = pierceDamage - pierceDefense;

                if (slashCalculation >= 1.0) {
                    spawnDamageTypeParticles(target, AetherIIParticleTypes.SLASH_ATTACK.get());
                } else {
                    slashCalculation = 1.0;
                }
                if (impactCalculation >= 1.0) {
                    spawnDamageTypeParticles(target, AetherIIParticleTypes.IMPACT_ATTACK.get());
                } else {
                    impactCalculation = 1.0;
                }
                if (pierceCalculation >= 1.0) {
                    spawnDamageTypeParticles(target, AetherIIParticleTypes.PIERCE_ATTACK.get());
                } else {
                    pierceCalculation = 1.0;
                }

                damage = slashCalculation + impactCalculation + pierceCalculation;
                if (sourceEntity instanceof Player player) {
                    damage *= player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).getCriticalDamageModifier();
                    damage *= player.getAttackStrengthScale(0.5F);

                    player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).setCriticalDamageModifier(1.0F);
                }
            }
        }
        return (float) damage;
    }

    private static void spawnDamageTypeParticles(Entity target, SimpleParticleType particleType) {
        Minecraft.getInstance().particleEngine.createTrackingEmitter(target, particleType);
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
            components.add(position, Component.literal(" ").append(Component.translatable("attribute.modifier.equals.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(value), Component.translatable("aether_ii.tooltip.item.damage." + name)).withStyle(ChatFormatting.DARK_GREEN)));
        }
    }

    public static void addBonusDamageTypeTooltips(Player player, List<Component> components, ItemStack stack) {
        if (player != null) {
            RegistryAccess registryAccess = player.level().registryAccess();

            double slashDamage = AetherIIDamageInflictions.getSlashDamage(registryAccess, stack) - 1;
            double impactDamage = AetherIIDamageInflictions.getImpactDamage(registryAccess, stack) - 1;
            double pierceDamage = AetherIIDamageInflictions.getPierceDamage(registryAccess, stack) - 1;

            if (stack.getItem() instanceof UniqueDamage uniqueDamage) {
                Triple<Double, Double, Double> damages = uniqueDamage.getUniqueDamage(stack, slashDamage, impactDamage, pierceDamage);
                slashDamage = damages.getLeft();
                impactDamage = damages.getMiddle();
                pierceDamage = damages.getRight();
            }

            addBonusDamageTypeTooltip(components, slashDamage, "slash");
            addBonusDamageTypeTooltip(components, impactDamage, "impact");
            addBonusDamageTypeTooltip(components, pierceDamage, "pierce");
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
            components.add(position, Component.literal("").append(Component.translatable("attribute.modifier.plus.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(value), Component.translatable("aether_ii.tooltip.item.damage." + name)).withStyle(ChatFormatting.BLUE)));
        }
    }
}
