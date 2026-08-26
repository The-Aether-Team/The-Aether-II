package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @WrapOperation(method = "baseTick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean baseTick(LivingEntity livingEntity, TagKey<Fluid> type, Operation<Boolean> original) {
        boolean isEyeInFluid = original.call(livingEntity, type);
        if (!isEyeInFluid) {
            return livingEntity.level().getBlockState(BlockPos.containing(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ())).is(AetherIIBlocks.HESTVEIL);
        } else {
            return true;
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

    @ModifyArgs(method = "travelInAir(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V"))
    private void travelInAir(Args args) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        boolean flag = livingEntity.getDeltaMovement().y <= 0.0;
        if (flag && livingEntity.isUsingItem() && livingEntity.getUseItem().is(AetherIITags.Items.TOOLS_GLIDERS)) {
            args.set(1, -0.08);
        }
    }

    @WrapMethod(method = "handleOnClimbable(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;")
    private Vec3 handleOnClimbable(Vec3 delta, Operation<Vec3> original) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity.getInBlockState().is(AetherIIBlocks.BRETTL_ROPE_STAKE) || livingEntity.getInBlockState().is(AetherIIBlocks.BRETTL_ROPE) && livingEntity.verticalCollisionBelow) {
            return delta;
        }
        return original.call(delta);
    }
}
