package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.entity.vehicle.RiderSitContext;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class HumanoidMobRendererMixin {
    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z", ordinal = 0))
    private boolean useRiderSitContextForRidingPose(LivingEntity instance, Operation<Boolean> original) {
        boolean passenger = original.call(instance);
        Entity vehicle = instance.getVehicle();
        if (passenger && vehicle instanceof RiderSitContext riderSitContext) {
            return riderSitContext.shouldRiderSit(vehicle, instance);
        }
        return passenger;
    }
}
