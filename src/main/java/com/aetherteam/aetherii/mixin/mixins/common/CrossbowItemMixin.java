package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @WrapOperation(method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;getShootingPower(Lnet/minecraft/world/item/component/ChargedProjectiles;)F"))
    private static float use(ChargedProjectiles projectile, Operation<Float> original, @Local ItemStack stack) {
        if (stack.getItem() instanceof TieredCrossbowItem tieredCrossbowItem) {
            return tieredCrossbowItem.getCrossbowShootingPower(projectile);
        }
        return original.call(projectile);
    }

    @WrapOperation(method = "getChargeDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;modifyCrossbowChargingTime(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;F)F"))
    private static float getChargeDuration(ItemStack stack, LivingEntity entity, float crossbowChargingTime, Operation<Float> original) {
        if (stack.getItem() instanceof TieredCrossbowItem tieredCrossbowItem) {
            return tieredCrossbowItem.getChargeTime(stack, entity, crossbowChargingTime);
        }
        return original.call(stack, entity, crossbowChargingTime);
    }
}
