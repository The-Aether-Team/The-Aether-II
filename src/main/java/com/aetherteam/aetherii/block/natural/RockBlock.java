package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RockBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty AMOUNT = AetherIIBlockStateProperties.ROCK_AMOUNT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SMALL_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 3.0, 11.0);
    protected static final VoxelShape LARGE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0);

    public RockBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(AMOUNT, 1).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int i = state.getValue(AMOUNT);
        if (i == 1) {
            return SMALL_SHAPE;
        } else {
            return LARGE_SHAPE;
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return !state.canSurvive(levelReader, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos.below());
        return Block.isFaceFull(blockState.getCollisionShape(level, pos.below()), Direction.UP);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(AMOUNT) < 3 || super.canBeReplaced(state, context);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state.is(this)) {
            return state.cycle(AMOUNT);
        } else {
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AMOUNT, WATERLOGGED);
    }
}
