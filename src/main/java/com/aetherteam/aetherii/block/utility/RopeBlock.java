package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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
    public static final VoxelShape SHAPE_KNOT = Block.box(6, 6, 6, 10, 10, 10);
    public static final int MAX_LENGTH = 16;
    public static final int DELAY = 4;

    public RopeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(END, AetherIIBlockStateProperties.RopeEndState.NONE).setValue(KNOT, false).setValue(UP, false).setValue(DOWN, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        boolean canUnknot = false;
        List<Direction> directionStates = this.getDirectionStates(state);
        for (Direction entry : directionStates) {
            if (directionStates.contains(entry.getOpposite()) && directionStates.size() == 2) {
                canUnknot = true;
            }
        }
        if (player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty()) {
            if (state.getValue(KNOT) && canUnknot) {
                level.setBlock(pos, state.setValue(KNOT, false), 1 | 2);
                return InteractionResult.SUCCESS;
            } else if (!state.getValue(KNOT)) {
                level.setBlock(pos, state.setValue(KNOT, true), 1 | 2);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
//        if (level.getBlockState(pos.below()).isAir()) {
//            if (this.checkForStake(level, pos)) {
//                level.setBlock(pos, state.setValue(DOWN, true).setValue(END, false), 1 | 2);
//                level.setBlock(pos.below(), AetherIIBlocks.BRETTL_ROPE.get().defaultBlockState().setValue(UP, true), 1 | 2);
//                level.scheduleTick(pos.below(), this, DELAY);
//            } else {
//                level.setBlock(pos, state.setValue(END, true), 1 | 2);
//            }
//        } else {
//            level.setBlock(pos, state.setValue(DOWN, true).setValue(END, false).setValue(SPOOL, true), 1 | 2);
//        }




    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        if (!state.getValue(KNOT)) {
            if (state.getValue(PROPERTY_BY_DIRECTION.get(direction)) && neighborState.isEmpty()) {
                if (direction == Direction.UP || (direction.getAxis().getPlane() == Direction.Plane.HORIZONTAL && levelReader.getBlockState(pos.relative(direction.getOpposite())).isEmpty())) {
                    state = Blocks.AIR.defaultBlockState();
                }
            }
        }
        if (!state.isAir()) {
            if (state.getValue(KNOT) || this.getExistingConnectionAxis(state) == direction.getAxis()) {
                state = state.setValue(PROPERTY_BY_DIRECTION.get(direction), neighborState.isSolid() || neighborState.is(this)); //todo use issturdy check
            }
        }


//        if (neighborState.isAir()) {
//            if (direction == Direction.DOWN) {
//                if (state.getValue(SPOOL)) {
//                    scheduledTickAccess.scheduleTick(pos, this, DELAY);
//                    state = state.setValue(SPOOL, false);
//                } else {
//                    state = state.setValue(DOWN, false);
//                    if (!state.getValue(KNOT)) {
//                        state = state.setValue(END, true);
//                    }
//                }
//            } else if (direction == Direction.UP) { //todo this should be based on connection direction to allow for placing rope without having it break immediately when breaking smth above it. if it only has one connection?
//                return Blocks.AIR.defaultBlockState();
//            }
//        }
        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = level.getFluidState(pos);
        BlockState blockState = this.defaultBlockState().setValue(KNOT, true);
        for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
            BlockState neighborState = level.getBlockState(pos.relative(entry.getKey()));
            blockState = blockState.setValue(entry.getValue(), neighborState.isSolid());
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

    public Direction.Axis getExistingConnectionAxis(BlockState state) {
        Direction.Axis direction = null;
        for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (state.getValue(entry.getValue())) {
                direction = entry.getKey().getAxis();
                break;
            }
        }
        return direction;
    }

    public List<Direction> getDirectionStates(BlockState state) {
        List<Direction> directions = new ArrayList<>();
        for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (state.getValue(entry.getValue())) {
                directions.add(entry.getKey());
            }
        }
        return directions;
    }

    public boolean checkForStake(LevelReader levelReader, BlockPos pos) {
//        for (int i = 1; i < MAX_LENGTH; ++i) {
//            BlockPos abovePos = pos.above(i);
//            BlockState aboveState = levelReader.getBlockState(abovePos);
//            if (aboveState.is(AetherIIBlocks.BRETTL_ROPE_STAKE)) {
//                return true;
//            }
//        }
        return false;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { //todo
//        VoxelShape spool = Block.box(4, 0, 4, 12, 2, 12);
        VoxelShape shape = Shapes.empty();
        for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (state.getValue(entry.getValue())) {
                shape = Shapes.or(shape, SHAPE_CONNECTIONS.get(entry.getKey()));
            }
        }
        if (state.getValue(KNOT)) {
            shape = Shapes.or(shape, SHAPE_KNOT);
        }
//        if (state.getValue(SPOOL)) {
//            shape = Shapes.or(shape, spool);
//        }
        return shape;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape collisionShape = Shapes.empty();
        for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (entry.getKey().getAxis().getPlane() == Direction.Plane.HORIZONTAL && state.getValue(entry.getValue())) {
                collisionShape = Shapes.or(collisionShape, SHAPE_CONNECTIONS.get(entry.getKey()));
            }
        }
        if (state.getValue(KNOT)) {
            collisionShape = Shapes.or(collisionShape, SHAPE_KNOT);
        }
        if (context.isAbove(collisionShape, pos, true) && !context.isDescending()) {
            return collisionShape;
        } else {
            return Shapes.empty();
        }
    }

    @Override
    protected boolean skipRendering(BlockState state, BlockState neighborState, Direction direction) {
        return neighborState.is(this) || neighborState.is(AetherIIBlocks.BRETTL_ROPE_STAKE) || super.skipRendering(state, neighborState, direction); //todo also not working for some reason
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
}
