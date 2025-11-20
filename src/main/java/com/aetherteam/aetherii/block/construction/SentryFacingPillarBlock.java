package com.aetherteam.aetherii.block.construction;

import com.aetherteam.aetherii.block.miscellaneous.FacingPillarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SentryFacingPillarBlock extends FacingPillarBlock implements SentryBlockUpdating {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public SentryFacingPillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.UP).setValue(LIT, true).setValue(POWERED, false));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        SentryBlockUpdating.super.updateStates(state, level, pos);
        super.tick(state, level, pos, random);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        SentryBlockUpdating.super.scheduleChange(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState);
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT, POWERED);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return level.getBlockState(pos).getValue(LIT) ? 15 : 0;
    }
}
