package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {
    @WrapOperation(method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z", at = @At(value = "INVOKE", target = "isVehicle()Z"))
    private boolean isVehicle(LivingEntity instance, Operation<Boolean> original) {
        boolean isVehicle = original.call(instance);
        if (isVehicle && instance.getFirstPassenger() != null && instance.getFirstPassenger() instanceof Aerbunny) {
            return false;
        }
        return isVehicle;
    }
}
