package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LongJumpToRandomPos.class)
public class LongJumpToRandomPosMixin {
    @WrapOperation(method = "checkExtraStartConditions(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Mob;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private static boolean canJumpFromCurrentPosition(BlockState instance, Block block, Operation<Boolean> original) {
        if (block == Blocks.HONEY_BLOCK) {
            return original.call(instance, block) || instance.is(AetherIIBlocks.GEL_BLOCK.get());
        } else {
            return original.call(instance, block);
        }
    }
}
