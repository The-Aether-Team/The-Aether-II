package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyArgs(method = "travelInAir(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V"))
    private void travelInAir(Args args) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        boolean flag = livingEntity.getDeltaMovement().y <= 0.0;
        if (flag && livingEntity.isUsingItem() && livingEntity.getUseItem().is(AetherIITags.Items.TOOLS_GLIDERS)) {
            args.set(1, -0.08);
        }
    }

    @WrapOperation(method = "causeFallDamage(DFLnet/minecraft/world/damagesource/DamageSource;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;calculateFallDamage(DF)I"))
    private int causeFallDamage(LivingEntity livingEntity, double fallDistance, float damageModifier, Operation<Integer> original) {
        int damage = original.call(livingEntity, fallDistance, damageModifier);
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            AetherIIAdvancementTriggers.FALL_ON_GROUND.get().trigger(serverPlayer, fallDistance, damage);
        }
        return damage;
    }
}
