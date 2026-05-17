package com.aetherteam.aetherii.block.dungeon;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class PrayerCandleBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<PrayerCandleBlock> CODEC = simpleCodec(PrayerCandleBlock::new);
    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty WATERLOGGED;
    public static final Map<Direction, VoxelShape> SHAPES;

    public MapCodec<PrayerCandleBlock> codec() {
        return CODEC;
    }

    public PrayerCandleBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    private boolean canAttachTo(BlockGetter level, BlockPos pos, Direction direction) {
        BlockState blockState = level.getBlockState(pos);
        return blockState.isFaceSturdy(level, pos, direction);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return this.canAttachTo(level, pos.relative(direction.getOpposite()), direction);
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (directionToNeighbour.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (state.getValue(WATERLOGGED)) {
                ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }

            return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        }
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!context.replacingClickedOnBlock()) {
            BlockState state = context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace().getOpposite()));
            if (state.is(this) && state.getValue(FACING) == context.getClickedFace()) {
                return null;
            }
        }

        BlockState state = this.defaultBlockState();
        LevelReader level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState replacedFluidState = context.getLevel().getFluidState(context.getClickedPos());

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction.getOpposite());
                if (state.canSurvive(level, pos)) {
                    return state.setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER));
                }
            }
        }

        return null;
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        SHAPES = Shapes.rotateHorizontal(Block.box(3.0F, 2.0F, 8.0F, 13.0F, 16.0F, 16.0F));
    }
}