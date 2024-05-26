package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageInflictions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageResistances;
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

import java.util.List;

public class DamageSystemHooks {
    public static void trackCriticalHitValue(Player player, float value) {
        player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM.get()).setCriticalDamageModifier(value);
    }

    public static float getDamageTypeModifiedValue(Entity target, DamageSource source, float damage) {
        RegistryAccess registryAccess = target.level().registryAccess();
        Entity sourceEntity = source.getDirectEntity();
        ItemStack sourceStack = ItemStack.EMPTY;

        if (sourceEntity instanceof LivingEntity livingSource) {
            sourceStack = livingSource.getMainHandItem();
        } else if (sourceEntity instanceof Projectile && sourceEntity instanceof ItemSupplier itemSupplier) {
            sourceStack = itemSupplier.getItem();
        }

        if (!sourceStack.isEmpty()) {
            float slashDamage = AetherIIDamageInflictions.getSlashDamage(registryAccess, sourceStack);
            float impactDamage = AetherIIDamageInflictions.getImpactDamage(registryAccess, sourceStack);
            float pierceDamage = AetherIIDamageInflictions.getPierceDamage(registryAccess, sourceStack);

            float slashDefense = AetherIIDamageResistances.getSlashDefense(registryAccess, target);
            float impactDefense = AetherIIDamageResistances.getImpactDefense(registryAccess, target);
            float pierceDefense = AetherIIDamageResistances.getPierceDefense(registryAccess, target);

            boolean isTypedItem = (slashDamage + impactDamage + pierceDamage) > 0.0F;
            boolean isTypedEntity = (slashDefense + impactDefense + pierceDefense) > 0.0F;

            if (isTypedItem && isTypedEntity) {
                float slashCalculation = slashDamage - slashDefense;
                float impactCalculation = impactDamage - impactDefense;
                float pierceCalculation = pierceDamage - pierceDefense;

                if (slashCalculation >= 1.0F) {
                    spawnDamageTypeParticles(target, AetherIIParticleTypes.SLASH_ATTACK.get());
                } else {
                    slashCalculation = 1.0F;
                }
                if (impactCalculation >= 1.0F) {
                    spawnDamageTypeParticles(target, AetherIIParticleTypes.IMPACT_ATTACK.get());
                } else {
                    impactCalculation = 1.0F;
                }
                if (pierceCalculation >= 1.0F) {
                    spawnDamageTypeParticles(target, AetherIIParticleTypes.PIERCE_ATTACK.get());
                } else {
                    pierceCalculation = 1.0F;
                }

                damage = slashCalculation + impactCalculation + pierceCalculation;
                if (sourceEntity instanceof Player player) {
                    damage *= player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).getCriticalDamageModifier();
                    damage *= player.getAttackStrengthScale(0.5F);

                    player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).setCriticalDamageModifier(1.0F);
                }
            }
        }
        return damage;
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

            addDamageTypeTooltip(components, position, AetherIIDamageInflictions.getSlashDamage(registryAccess, stack), "slash");
            addDamageTypeTooltip(components, position, AetherIIDamageInflictions.getImpactDamage(registryAccess, stack), "impact");
            addDamageTypeTooltip(components, position, AetherIIDamageInflictions.getPierceDamage(registryAccess, stack), "pierce");
        }
    }

    private static void addDamageTypeTooltip(List<Component> components, int position, float value, String name) {
        if (value > 0.0) {
            components.add(position, Component.literal(" ").append(Component.translatable("attribute.modifier.equals.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(value), Component.translatable("aether_ii.tooltip.item.damage." + name)).withStyle(ChatFormatting.DARK_GREEN)));
        }
    }
}
