package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponItemMixin {
    @WrapOperation(method = "draw(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;processProjectileCount(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/Entity;I)I"))
    private static int draw(ServerLevel level, ItemStack tool, Entity entity, int projectileCount, Operation<Integer> original) {
        if (tool.getItem() instanceof TieredCrossbowItem tieredCrossbowItem) {
            return tieredCrossbowItem.getProjectileCount(level, tool, entity, projectileCount);
        }
        return original.call(level, tool, entity, projectileCount);
    }

    @WrapOperation(method = "shoot(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;Ljava/util/List;FFZLnet/minecraft/world/entity/LivingEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;processProjectileSpread(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/Entity;F)F"))
    private float shoot(ServerLevel level, ItemStack tool, Entity entity, float projectileSpread, Operation<Float> original) {
        if (tool.getItem() instanceof TieredCrossbowItem tieredCrossbowItem) {
            return tieredCrossbowItem.getProjectileSpread(level, tool, entity, 0.0F);
        }
        return original.call(level, tool, entity, projectileSpread);
    }
}
