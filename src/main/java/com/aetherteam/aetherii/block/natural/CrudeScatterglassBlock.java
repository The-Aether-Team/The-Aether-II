package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CrudeScatterglassBlock extends HalfTransparentBlock {
    public CrudeScatterglassBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.is(AetherIITags.Blocks.CRUDE_SCATTERGLASS) || super.skipRendering(state, adjacentBlockState, side);
    }
}
