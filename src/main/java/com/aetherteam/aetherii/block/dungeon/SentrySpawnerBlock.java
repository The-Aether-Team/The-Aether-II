package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.SentrySpawnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SentrySpawnerBlock extends BaseEntityBlock {
    public static final EnumProperty<AetherIIBlockStateProperties.SentrySpawnerState> SENTRY_SPAWNER_STATE = AetherIIBlockStateProperties.SENTRY_SPAWNER_STATE;
    private static final VoxelShape SHAPE = Block.box(0, 0.0, 0, 16, 14.0, 16);
public SentrySpawnerBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(SENTRY_SPAWNER_STATE, AetherIIBlockStateProperties.SentrySpawnerState.INACTIVE));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SentrySpawnerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, AetherIIBlockEntityTypes.SENTRY_SPAWNER.get(), level.isClientSide() ? SentrySpawnerBlockEntity::clientTick : SentrySpawnerBlockEntity::serverTick);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor scheduledTickAccess = level;
        if (state.getValue(SENTRY_SPAWNER_STATE) == AetherIIBlockStateProperties.SentrySpawnerState.INACTIVE) {
            boolean hasPowered = neighborState.is(AetherIITags.Blocks.CARRIES_SENTRY_CURRENT) && (neighborState.hasProperty(BlockStateProperties.POWERED) && neighborState.getValue(BlockStateProperties.POWERED));
            boolean hasSignal = level.getSignal(neighborPos, direction) > 0;
            if (hasPowered || hasSignal) {
                return state.setValue(SENTRY_SPAWNER_STATE, AetherIIBlockStateProperties.SentrySpawnerState.TRIGGERED);
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(SENTRY_SPAWNER_STATE) != AetherIIBlockStateProperties.SentrySpawnerState.INACTIVE ? super.getLightEmission(state, level, pos) : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SENTRY_SPAWNER_STATE);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return level.getBlockState(pos).getValue(SENTRY_SPAWNER_STATE) != AetherIIBlockStateProperties.SentrySpawnerState.INACTIVE ? 15 : 0;
    }
}
