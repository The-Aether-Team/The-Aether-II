package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.entity.vehicle.RiderSitContext;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidMobRenderer.class)
public class HumanoidMobRendererMixin {
    @WrapOperation(method = "extractHumanoidRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FLnet/minecraft/client/renderer/item/ItemModelResolver;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;shouldRiderSit()Z"))
    private static boolean shouldRiderSit(Entity instance, Operation<Boolean> original, @Local(argsOnly = true) LivingEntity entity) {
        if (instance instanceof RiderSitContext riderSitContext) {
            return riderSitContext.shouldRiderSit(instance, entity);
        } else {
            return original.call(instance);
        }
    }
}
