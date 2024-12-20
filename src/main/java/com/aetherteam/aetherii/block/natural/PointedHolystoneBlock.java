package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluids;

public class PointedHolystoneBlock extends AbstractPointedStoneBlock {
    public PointedHolystoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void createDripstone(LevelAccessor level, BlockPos pos, Direction direction, DripstoneThickness thickness) {
        BlockState blockstate = AetherIIBlocks.POINTED_HOLYSTONE.get().defaultBlockState().setValue(TIP_DIRECTION, direction).setValue(THICKNESS, thickness).setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
        level.setBlock(pos, blockstate, 3);
    }
}
