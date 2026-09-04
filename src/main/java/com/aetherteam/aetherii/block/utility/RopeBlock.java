package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RopeBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<AetherIIBlockStateProperties.RopeEndState> END = AetherIIBlockStateProperties.ROPE_END;
    public static final BooleanProperty KNOT = AetherIIBlockStateProperties.ROPE_KNOT;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;
    public static final Map<Direction, VoxelShape> SHAPE_CONNECTIONS = Shapes.rotateAll(Block.box(6, 6, 0, 10, 10, 8));
    public static final Map<Direction, VoxelShape> SHAPE_OCCLUSION_CONNECTIONS = Shapes.rotateAll(Block.box(7, 7, 0, 9, 9, 8));
    public static final VoxelShape SHAPE_KNOT = Block.box(6, 6, 6, 10, 10, 10);
    public static final VoxelShape SHAPE_FLOOR_SPOOL = Block.box(4, 0, 4, 12, 2, 12);
    public static final int DELAY = 4;
    private final Function<BlockState, VoxelShape> shapes;
    private final Function<BlockState, VoxelShape> collisionShapes;
    private final Function<BlockState, VoxelShape> occlusionShapes;

    public RopeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(END, AetherIIBlockStateProperties.RopeEndState.NONE).setValue(KNOT, false).setValue(UP, false).setValue(DOWN, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
        this.shapes = this.makeShapes();
        this.collisionShapes = this.makeCollisionShapes();
        this.occlusionShapes = this.makeOcclusionShapes();
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState((state) -> {
            VoxelShape shape = Shapes.empty();
            for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
                if (state.getValue(entry.getValue())) {
                    shape = Shapes.or(shape, SHAPE_CONNECTIONS.get(entry.getKey()));
                }
            }
            if (state.getValue(KNOT)) {
                shape = Shapes.or(shape, SHAPE_KNOT);
            }
            if (state.getValue(END) == AetherIIBlockStateProperties.RopeEndState.SPOOLED) {
                shape = Shapes.or(shape, SHAPE_FLOOR_SPOOL);
            }
            return shape;
        }, WATERLOGGED);
    }

    private Function<BlockState, VoxelShape> makeCollisionShapes() {
        return this.getShapeForEachState((state) -> {
            VoxelShape shape = Shapes.empty();
            for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
                if (entry.getKey().getAxis().getPlane() == Direction.Plane.HORIZONTAL && state.getValue(entry.getValue())) {
                    shape = Shapes.or(shape, SHAPE_CONNECTIONS.get(entry.getKey()));
                }
            }
            if (state.getValue(KNOT)) {
                shape = Shapes.or(shape, SHAPE_KNOT);
            }
            return shape;
        }, WATERLOGGED);
    }

    private Function<BlockState, VoxelShape> makeOcclusionShapes() {
        return this.getShapeForEachState((state) -> {
            VoxelShape shape = Shapes.empty();
            for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
                if (state.getValue(entry.getValue())) {
                    shape = Shapes.or(shape, SHAPE_OCCLUSION_CONNECTIONS.get(entry.getKey()));
                }
            }
            return shape;
        }, WATERLOGGED);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        boolean canUnknot = false;
        List<Direction> directionStates = this.getDirectionStates(state);
        for (Direction entry : directionStates) {
            if (directionStates.contains(entry.getOpposite()) && directionStates.size() == 2) {
                canUnknot = true;
                break;
            }
        }
        if (state.getValue(KNOT) && canUnknot) {
            level.setBlock(pos, state.setValue(KNOT, false), 1 | 2);
            return InteractionResult.SUCCESS;
        } else if (!state.getValue(KNOT)) {
            BlockState newState = state.setValue(KNOT, true).setValue(END, AetherIIBlockStateProperties.RopeEndState.NONE);
            for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
                if (!newState.getValue(entry.getValue())) {
                    BlockPos neighborPos = pos.relative(entry.getKey());
                    BlockState neighborState = level.getBlockState(neighborPos);
                    newState = newState.setValue(entry.getValue(), neighborState.isFaceSturdy(level, neighborPos, entry.getKey().getOpposite(), SupportType.CENTER));
                }
            }
            level.setBlock(pos, newState, 1 | 2);
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
            if (RopeStakeBlock.checkForStake(level, pos)) {
                RopeBlock.placeRope(level, belowPos, AetherIIBlocks.BRETTL_ROPE.get().defaultBlockState().setValue(RopeBlock.UP, true));
            } else {
                level.setBlock(pos, state.setValue(END, AetherIIBlockStateProperties.RopeEndState.FRAYED), 1 | 2);
            }
        } else {
            level.setBlock(pos, state.setValue(RopeBlock.DOWN, true).setValue(END, AetherIIBlockStateProperties.RopeEndState.SPOOLED), 1 | 2);
        }
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        if (aboveState.is(this) && !aboveState.getValue(KNOT)) {
            level.setBlock(abovePos, aboveState.setValue(END, AetherIIBlockStateProperties.RopeEndState.FRAYED), 1 | 2);
        } else if (aboveState.is(AetherIIBlocks.BRETTL_ROPE_STAKE) && aboveState.getValue(RopeStakeBlock.SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED) {
            level.setBlock(abovePos, aboveState.setValue(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.NONE), 1 | 2);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        if (!state.getValue(KNOT)) {
            if (state.getValue(PROPERTY_BY_DIRECTION.get(direction)) && neighborState.isEmpty()) {
                if (direction == Direction.UP || (direction.getAxis().getPlane() == Direction.Plane.HORIZONTAL && !neighborState.isFaceSturdy(levelReader, pos.relative(direction), direction.getOpposite(), SupportType.CENTER))) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }
        if ((state.getValue(KNOT) && (neighborState.getValueOrElse(KNOT, true) || this.getExistingConnectionAxis(neighborState) == direction.getAxis())) || this.getExistingConnectionAxis(state) == direction.getAxis()) {
            if (state.getValue(END) == AetherIIBlockStateProperties.RopeEndState.SPOOLED) {
                scheduledTickAccess.scheduleTick(pos, this, DELAY);
            }
            state = state.setValue(PROPERTY_BY_DIRECTION.get(direction), neighborState.isFaceSturdy(levelReader, neighborPos, direction.getOpposite(), SupportType.CENTER) || neighborState.is(this)).setValue(END, AetherIIBlockStateProperties.RopeEndState.NONE);
        }
        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = level.getFluidState(pos);
        BlockState blockState = this.defaultBlockState().setValue(KNOT, true);
        for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
            BlockPos neighborPos = pos.relative(entry.getKey());
            BlockState neighborState = level.getBlockState(neighborPos);
            blockState = blockState.setValue(entry.getValue(), neighborState.isFaceSturdy(level, neighborPos, entry.getKey().getOpposite(), SupportType.CENTER));
        }
        return blockState.setValue(WATERLOGGED, fluidState.is(Fluids.WATER));
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(END, KNOT, UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    private Direction.Axis getExistingConnectionAxis(BlockState state) {
        Direction.Axis axis = null;
        for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (state.getValueOrElse(entry.getValue(), false)) {
                axis = entry.getKey().getAxis();
            }
        }
        return axis;
    }

    private List<Direction> getDirectionStates(BlockState state) {
        List<Direction> directions = new ArrayList<>();
        for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (state.getValueOrElse(entry.getValue(), false)) {
                directions.add(entry.getKey());
            }
        }
        return directions;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)  {
        return this.shapes.apply(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = this.collisionShapes.apply(state);
        if (context.isAbove(shape, pos, true) && !context.isDescending()) {
            return shape;
        } else {
            return Shapes.empty();
        }
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return this.occlusionShapes.apply(state);
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean isScaffolding(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    public static void placeRope(Level level, BlockPos pos, BlockState state) {
        BlockState existingBlock = level.getBlockState(pos);
        if (!existingBlock.is(state.getBlock())) {
            if (existingBlock.getFluidState().is(FluidTags.WATER)) {
                state = state.setValue(WATERLOGGED, true);
            }
            level.destroyBlock(pos, true);
            level.setBlock(pos, state, 1 | 2);
        }
        level.scheduleTick(pos, state.getBlock(), RopeBlock.DELAY);
    }
}
