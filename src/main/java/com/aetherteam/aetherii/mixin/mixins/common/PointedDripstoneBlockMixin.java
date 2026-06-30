package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.block.natural.AbstractPointedStoneBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {
    @Inject(method = "createDripstone(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/properties/DripstoneThickness;)V", at = @At("HEAD"), cancellable = true)
    private static void createDripstone(LevelAccessor level, BlockPos pos, Direction direction, DripstoneThickness thickness, CallbackInfo ci) {
        BlockState source = level.getBlockState(pos.relative(direction.getOpposite()));
        if (source.getBlock() instanceof AbstractPointedStoneBlock abstractPointedStoneBlock) {
            abstractPointedStoneBlock.createDripstone(level, pos, direction, thickness);
            ci.cancel();
        }
    }

    @WrapOperation(method = "isTip(Lnet/minecraft/world/level/block/state/BlockState;Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private static boolean isTip(BlockState instance, Block block, Operation<Boolean> original) {
        if (block == Blocks.POINTED_DRIPSTONE) {
            return original.call(instance, block) || instance.getBlock() instanceof AbstractPointedStoneBlock;
        } else {
            return original.call(instance, block);
        }
    }

    @WrapOperation(method = "isStalactiteStartPos(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private static boolean isStalactiteStartPos(BlockState instance, Block block, Operation<Boolean> original) {
        if (block == Blocks.POINTED_DRIPSTONE) {
            return original.call(instance, block) || instance.getBlock() instanceof AbstractPointedStoneBlock;
        } else {
            return original.call(instance, block);
        }
    }

    @WrapOperation(method = "isPointedDripstoneWithDirection(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private static boolean isPointedDripstoneWithDirection(BlockState instance, Block block, Operation<Boolean> original) {
        if (block == Blocks.POINTED_DRIPSTONE) {
            return original.call(instance, block) || instance.getBlock() instanceof AbstractPointedStoneBlock;
        } else {
            return original.call(instance, block);
        }
    }

    @Inject(method = "spawnDripParticle(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/Fluid;)V", at = @At("HEAD"), cancellable = true)
    private static void spawnDripParticle(Level level, BlockPos pos, BlockState state, Fluid fluid, CallbackInfo ci) {
        if (state.getBlock() instanceof AbstractPointedStoneBlock && fluid.isSame(Fluids.EMPTY)) {
            ci.cancel();
        }
    }
}
