package com.aetherteam.aetherii.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SentryBlock extends Block implements SentryBlockUpdating {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public SentryBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, true).setValue(POWERED, false));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockState newState = null;
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);

            boolean hasPowered = neighborState.is(state.getBlock()) && neighborState.getValue(POWERED);
            boolean hasSignal = level.getSignal(neighborPos, direction) > 0;
            if (hasSignal != state.getValue(BlockStateProperties.POWERED) || hasPowered != state.getValue(BlockStateProperties.POWERED)) {
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
        super.tick(state, level, pos, random);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        boolean hasPowered = neighborState.is(state.getBlock()) && neighborState.getValue(POWERED);
        boolean hasSignal = level.getSignal(neighborPos, direction) > 0;
        if (hasSignal != state.getValue(BlockStateProperties.POWERED) || hasPowered != state.getValue(BlockStateProperties.POWERED)) {
            scheduledTickAccess.scheduleTick(pos, state.getBlock(), 3);
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, POWERED);
    }
}
