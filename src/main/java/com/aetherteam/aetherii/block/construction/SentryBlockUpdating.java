package com.aetherteam.aetherii.block.construction;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface SentryBlockUpdating {
    default void updateStates(BlockState state, ServerLevel level, BlockPos pos) {
        BlockState newState = null;
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);

            boolean hasPowered = neighborState.is(AetherIITags.Blocks.CARRIES_SENTRY_CURRENT) && (neighborState.hasProperty(BlockStateProperties.POWERED) && neighborState.getValue(BlockStateProperties.POWERED));
            boolean hasSignal = level.getSignal(neighborPos, direction) > 0;
            if ((neighborState.is(AetherIIBlocks.UNDERSHALE_BRICK_PRESSURE_PLATE.get()) || neighborState.is(AetherIIBlocks.SENTRY_CRATE.get())) && (state.hasProperty(BlockStateProperties.LIT) && state.getValue(BlockStateProperties.LIT))) {
                hasSignal = false;
            }
            if ((!neighborState.is(AetherIITags.Blocks.CARRIES_SENTRY_CURRENT) && hasSignal != state.getValue(BlockStateProperties.POWERED)) || hasPowered != state.getValue(BlockStateProperties.POWERED)) {
                BlockState blockstate = state;
                if (!state.getValue(BlockStateProperties.POWERED)) {
                    blockstate = state.cycle(BlockStateProperties.LIT);
                }
                newState = blockstate.setValue(BlockStateProperties.POWERED, hasSignal || hasPowered);
            }
        }
        if (newState != null) {
            level.setBlock(pos, newState, 1 | 2);
        }
    }

    default void scheduleChange(BlockState state, LevelReader level, LevelAccessor scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState) {
        boolean hasPowered = neighborState.is(AetherIITags.Blocks.CARRIES_SENTRY_CURRENT) && (neighborState.hasProperty(BlockStateProperties.POWERED) && neighborState.getValue(BlockStateProperties.POWERED));
        boolean hasSignal = level.getSignal(neighborPos, direction) > 0;
        if ((!neighborState.is(AetherIITags.Blocks.CARRIES_SENTRY_CURRENT) && hasSignal != state.getValue(BlockStateProperties.POWERED)) || hasPowered != state.getValue(BlockStateProperties.POWERED)) {
            scheduledTickAccess.scheduleTick(pos, state.getBlock(), 3);
        }
    }
}
