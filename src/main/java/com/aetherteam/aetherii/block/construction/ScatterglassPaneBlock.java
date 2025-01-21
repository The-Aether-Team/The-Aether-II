package com.aetherteam.aetherii.block.construction;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ScatterglassPaneBlock extends IronBarsBlock {
    public ScatterglassPaneBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.is(AetherIITags.Blocks.SCATTERGLASS_PANE) || super.skipRendering(state, adjacentBlockState, side);
    }
}
