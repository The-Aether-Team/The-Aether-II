package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageInflictions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageResistances;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DamageSystemHooks {
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
            float slashDamage = AetherIIDamageInflictions.getSlashDamage(registryAccess, sourceStack) - AetherIIDamageResistances.getSlashDefense(registryAccess, target);
            float impactDamage = AetherIIDamageInflictions.getImpactDamage(registryAccess, sourceStack) - AetherIIDamageResistances.getImpactDefense(registryAccess, target);
            float pierceDamage = AetherIIDamageInflictions.getPierceDamage(registryAccess, sourceStack) - AetherIIDamageResistances.getPierceDefense(registryAccess, target);

            damage = slashDamage + impactDamage + pierceDamage;
        }

        return damage;
    }
}
