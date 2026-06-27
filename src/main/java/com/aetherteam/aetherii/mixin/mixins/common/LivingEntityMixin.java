package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
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
