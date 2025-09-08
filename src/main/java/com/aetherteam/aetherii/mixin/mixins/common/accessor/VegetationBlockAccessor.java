package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(VegetationBlock.class)
public interface VegetationBlockAccessor {
    @Invoker
    boolean callMayPlaceOn(BlockState state, BlockGetter level, BlockPos pos);
}