package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.construction.SentryBlockUpdating;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.GroundTrapBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;

public class SentryGroundTrapBlock extends BaseEntityBlock implements SentryBlockUpdating {
    public static final MapCodec<SentryGroundTrapBlock> CODEC = simpleCodec(SentryGroundTrapBlock::new);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public MapCodec<SentryGroundTrapBlock> codec() {
        return CODEC;
    }

    public SentryGroundTrapBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, false).setValue(POWERED, false).setValue(TRIGGERED, false));
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GroundTrapBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, AetherIIBlockEntityTypes.GROUND_TRAP.get(), level.isClientSide() ? GroundTrapBlockEntity::clientTick : GroundTrapBlockEntity::serverTick);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        SentryBlockUpdating.super.updateStates(state, level, pos);
        BlockState newState = level.getBlockState(pos);
        if (newState.getValue(POWERED) && newState.getValue(LIT) && !state.getValue(TRIGGERED)) {
            level.setBlock(pos, newState.setValue(TRIGGERED, true), 1 | 2);
        }
        super.tick(state, level, pos, random);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        SentryBlockUpdating.super.scheduleChange(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState);
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LIT) ? super.getLightEmission(state, level, pos) : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT, POWERED, TRIGGERED);
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
