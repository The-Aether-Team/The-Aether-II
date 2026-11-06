package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.neoforged.neoforge.client.model.ao.FullFaceCalculator")
public class FullFaceCalculatorMixin {
    @Unique
    private static final CardinalLighting AERCLOUD = new CardinalLighting(0.9F, 1.0F, 0.933F, 0.933F, 0.966F, 0.966F);

    @WrapOperation(
            method = "calculateFaceUncached(Lnet/neoforged/neoforge/client/model/ao/AoCalculatedFace;Lnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;ZZ)V",
            at = {
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", ordinal = 0),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", ordinal = 1),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", ordinal = 2),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", ordinal = 3),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", ordinal = 4),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", ordinal = 5),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", ordinal = 6),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", ordinal = 7),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isViewBlocking(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", ordinal = 8)
            }
    )
    private boolean calculateFaceUncached(BlockState instance, BlockGetter blockGetter, BlockPos pos, Operation<Boolean> original) {
        boolean isViewBlocking = original.call(instance, blockGetter, pos);
        if (instance.is(AetherIITags.Blocks.BYPASS_VIEW_BLOCKING) && !isViewBlocking) {
            return true;
        }
        return isViewBlocking;

    }

    @WrapOperation(method = "calculateFaceUncached(Lnet/neoforged/neoforge/client/model/ao/AoCalculatedFace;Lnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;ZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockAndTintGetter;cardinalLighting()Lnet/minecraft/world/level/CardinalLighting;"))
    private static CardinalLighting cardinalLighting(BlockAndTintGetter instance, Operation<CardinalLighting> original, @Local(argsOnly = true, name = "renderedState") BlockState renderedState) {
        if (renderedState.is(AetherIITags.Blocks.AERCLOUDS)) {
            return AERCLOUD;
        }
        return original.call(instance);
    }
}
