package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TrunkBlock extends Block implements SimpleWaterloggedBlock { //todo replace isSolid checks with something better and maybe shape test based.
    public static final MapCodec<TrunkBlock> CODEC = simpleCodec(TrunkBlock::new);
    public static final BooleanProperty TALL = BooleanProperty.create("tall");
    public static final EnumProperty<TrunkConnection> NORTH_CONNECTION = EnumProperty.create("north_connection", TrunkConnection.class);
    public static final EnumProperty<TrunkConnection> EAST_CONNECTION = EnumProperty.create("east_connection", TrunkConnection.class);
    public static final EnumProperty<TrunkConnection> SOUTH_CONNECTION = EnumProperty.create("south_connection", TrunkConnection.class);
    public static final EnumProperty<TrunkConnection> WEST_CONNECTION = EnumProperty.create("west_connection", TrunkConnection.class);
    public static final EnumProperty<TrunkCorner> NORTHEAST_CONNECTION = EnumProperty.create("northeast_connection", TrunkCorner.class);
    public static final EnumProperty<TrunkCorner> NORTHWEST_CONNECTION = EnumProperty.create("northwest_connection", TrunkCorner.class);
    public static final EnumProperty<TrunkCorner> SOUTHEAST_CONNECTION = EnumProperty.create("southeast_connection", TrunkCorner.class);
    public static final EnumProperty<TrunkCorner> SOUTHWEST_CONNECTION = EnumProperty.create("southwest_connection", TrunkCorner.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape CENTER_SHAPE = Block.box(3.0F, 0.0F, 3.0F, 13.0F, 13.0F, 13.0F); //todo improve all of this
    public static final VoxelShape NORTH_SHAPE = Block.box(3.0F, 0.0F, 0.0F, 13.0F, 13.0F, 3.0F);
    public static final VoxelShape EAST_SHAPE = Block.box(13.0F, 0.0F, 3.0F, 16.0F, 13.0F, 13.0F);
    public static final VoxelShape SOUTH_SHAPE = Block.box(3.0F, 0.0F, 13.0F, 13.0F, 13.0F, 16.0F);
    public static final VoxelShape WEST_SHAPE = Block.box(0.0F, 0.0F, 3.0F, 3.0F, 13.0F, 13.0F);
    public static final VoxelShape NORTHEAST_SHAPE = Block.box(13.0F, 0.0F, 0.0F, 16.0F, 13.0F, 3.0F);
    public static final VoxelShape NORTHWEST_SHAPE = Block.box(0.0F, 0.0F, 0.0F, 3.0F, 13.0F, 3.0F);
    public static final VoxelShape SOUTHEAST_SHAPE = Block.box(13.0F, 0.0F, 13.0F, 16.0F, 13.0F, 16.0F);
    public static final VoxelShape SOUTHWEST_SHAPE = Block.box(0.0F, 0.0F, 13.0F, 3.0F, 13.0F, 16.0F);
    public static final VoxelShape CENTER_TALL_SHAPE = Block.box(3.0F, 0.0F, 3.0F, 13.0F, 16.0F, 13.0F);
    public static final VoxelShape NORTH_TALL_SHAPE = Block.box(3.0F, 0.0F, 0.0F, 13.0F, 16.0F, 3.0F);
    public static final VoxelShape EAST_TALL_SHAPE = Block.box(13.0F, 0.0F, 3.0F, 16.0F, 16.0F, 13.0F);
    public static final VoxelShape SOUTH_TALL_SHAPE = Block.box(3.0F, 0.0F, 13.0F, 13.0F, 16.0F, 16.0F);
    public static final VoxelShape WEST_TALL_SHAPE = Block.box(0.0F, 0.0F, 3.0F, 3.0F, 16.0F, 13.0F);
    public static final VoxelShape NORTHEAST_TALL_SHAPE = Block.box(13.0F, 0.0F, 0.0F, 16.0F, 16.0F, 3.0F);
    public static final VoxelShape NORTHWEST_TALL_SHAPE = Block.box(0.0F, 0.0F, 0.0F, 3.0F, 16.0F, 3.0F);
    public static final VoxelShape SOUTHEAST_TALL_SHAPE = Block.box(13.0F, 0.0F, 13.0F, 16.0F, 16.0F, 16.0F);
    public static final VoxelShape SOUTHWEST_TALL_SHAPE = Block.box(0.0F, 0.0F, 13.0F, 3.0F, 16.0F, 16.0F);
    private final Map<BlockState, VoxelShape> shapeByIndex;

    public MapCodec<TrunkBlock> codec() {
        return CODEC;
    }

    public TrunkBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TALL, false)
                .setValue(NORTH_CONNECTION, TrunkConnection.NONE)
                .setValue(EAST_CONNECTION, TrunkConnection.NONE)
                .setValue(SOUTH_CONNECTION, TrunkConnection.NONE)
                .setValue(WEST_CONNECTION, TrunkConnection.NONE)
                .setValue(NORTHEAST_CONNECTION, TrunkCorner.NONE)
                .setValue(NORTHWEST_CONNECTION, TrunkCorner.NONE)
                .setValue(SOUTHEAST_CONNECTION, TrunkCorner.NONE)
                .setValue(SOUTHWEST_CONNECTION, TrunkCorner.NONE)
                .setValue(WATERLOGGED, false));
        this.shapeByIndex = this.makeShapes();
    }

    private Map<BlockState, VoxelShape> makeShapes() {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();
        for (boolean tall : TALL.getPossibleValues()) {
            for (TrunkConnection north : NORTH_CONNECTION.getPossibleValues()) {
                for (TrunkConnection east : EAST_CONNECTION.getPossibleValues()) {
                    for (TrunkConnection south : SOUTH_CONNECTION.getPossibleValues()) {
                        for (TrunkConnection west : WEST_CONNECTION.getPossibleValues()) {
                            for (TrunkCorner northwest : NORTHWEST_CONNECTION.getPossibleValues()) {
                                for (TrunkCorner northeast : NORTHEAST_CONNECTION.getPossibleValues()) {
                                    for (TrunkCorner southeast : SOUTHEAST_CONNECTION.getPossibleValues()) {
                                        for (TrunkCorner southwest : SOUTHWEST_CONNECTION.getPossibleValues()) {
                                            VoxelShape shape = tall ? CENTER_TALL_SHAPE : CENTER_SHAPE;
                                            if (north != TrunkConnection.NONE) shape = Shapes.or(shape, north.isTall() ? NORTH_TALL_SHAPE : NORTH_SHAPE);
                                            if (east != TrunkConnection.NONE) shape = Shapes.or(shape, east.isTall() ? EAST_TALL_SHAPE : EAST_SHAPE);
                                            if (south != TrunkConnection.NONE) shape = Shapes.or(shape, south.isTall() ? SOUTH_TALL_SHAPE : SOUTH_SHAPE);
                                            if (west != TrunkConnection.NONE) shape = Shapes.or(shape, west.isTall() ? WEST_TALL_SHAPE : WEST_SHAPE);
                                            if (northwest != TrunkCorner.NONE) shape = Shapes.or(shape, northwest == TrunkCorner.TALL ? NORTHWEST_TALL_SHAPE : NORTHWEST_SHAPE);
                                            if (northeast != TrunkCorner.NONE) shape = Shapes.or(shape, northeast == TrunkCorner.TALL ? NORTHEAST_TALL_SHAPE : NORTHEAST_SHAPE);
                                            if (southeast != TrunkCorner.NONE) shape = Shapes.or(shape, southeast == TrunkCorner.TALL ? SOUTHEAST_TALL_SHAPE : SOUTHEAST_SHAPE);
                                            if (southwest != TrunkCorner.NONE) shape = Shapes.or(shape, southwest == TrunkCorner.TALL ? SOUTHWEST_TALL_SHAPE : SOUTHWEST_SHAPE);
                                            BlockState state = this.defaultBlockState()
                                                    .setValue(TALL, tall)
                                                    .setValue(NORTH_CONNECTION, north)
                                                    .setValue(EAST_CONNECTION, east)
                                                    .setValue(SOUTH_CONNECTION, south)
                                                    .setValue(WEST_CONNECTION, west)
                                                    .setValue(NORTHWEST_CONNECTION, northwest)
                                                    .setValue(NORTHEAST_CONNECTION, northeast)
                                                    .setValue(SOUTHEAST_CONNECTION, southeast)
                                                    .setValue(SOUTHWEST_CONNECTION, southwest);
                                            builder.put(state.setValue(WATERLOGGED, false), shape);
                                            builder.put(state.setValue(WATERLOGGED, true), shape);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        BlockPos blockPos = context.getClickedPos();
        if (state != null) {
            return this.determineState(context.getLevel(), state, blockPos);
        }
        return null;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return this.sideUpdate(levelReader, currentPos, state, facingPos, facingState, facing);
    }

    private BlockState sideUpdate(LevelReader levelReader, BlockPos currentPos, BlockState state, BlockPos facingPos, BlockState facingState, Direction facing) {
        return this.determineState(levelReader, state, currentPos);
    }

    public BlockState determineState(LevelReader level, BlockState state, BlockPos blockPos) { //todo find some way to simplify this with methods correlating direction to property states and stuff.
        if (state != null) {
            BlockState aboveState = level.getBlockState(blockPos.above());
            if (aboveState.getBlock() instanceof TrunkBlock) {
                state = state.setValue(TALL, true);
            }
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos relative = blockPos.relative(direction);
                TrunkConnection connection = level.getBlockState(relative).getBlock() instanceof TrunkBlock ? TrunkConnection.MATCHING : level.getBlockState(relative).isSolid() ? TrunkConnection.FULL : TrunkConnection.NONE;
                switch (direction) {
                    case NORTH -> state = state.setValue(NORTH_CONNECTION, aboveState.getValueOrElse(NORTH_CONNECTION, TrunkConnection.NONE) != TrunkConnection.NONE ? connection.tall() : connection);
                    case EAST -> state = state.setValue(EAST_CONNECTION, aboveState.getValueOrElse(EAST_CONNECTION, TrunkConnection.NONE) != TrunkConnection.NONE ? connection.tall() : connection);
                    case SOUTH -> state = state.setValue(SOUTH_CONNECTION, aboveState.getValueOrElse(SOUTH_CONNECTION, TrunkConnection.NONE) != TrunkConnection.NONE ? connection.tall() : connection);
                    case WEST -> state = state.setValue(WEST_CONNECTION, aboveState.getValueOrElse(WEST_CONNECTION, TrunkConnection.NONE) != TrunkConnection.NONE ? connection.tall() : connection);
                }
            }
            if (state.getValue(NORTH_CONNECTION).isFull() && state.getValue(EAST_CONNECTION).isFull()) state = state.setValue(NORTHEAST_CONNECTION, aboveState.getValueOrElse(NORTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
            if (state.getValue(NORTH_CONNECTION).isFull() && state.getValue(WEST_CONNECTION).isFull()) state = state.setValue(NORTHWEST_CONNECTION, aboveState.getValueOrElse(NORTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
            if (state.getValue(SOUTH_CONNECTION).isFull() && state.getValue(EAST_CONNECTION).isFull()) state = state.setValue(SOUTHEAST_CONNECTION, aboveState.getValueOrElse(SOUTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
            if (state.getValue(SOUTH_CONNECTION).isFull() && state.getValue(WEST_CONNECTION).isFull()) state = state.setValue(SOUTHWEST_CONNECTION, aboveState.getValueOrElse(SOUTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState relativeState = level.getBlockState(blockPos.relative(direction));
                if (relativeState.getBlock() instanceof TrunkBlock) {
                    switch (direction) {
                        case NORTH -> {
                            if (state.getValue(WEST_CONNECTION).isFull() && relativeState.getValue(WEST_CONNECTION).isFull()) state = state.setValue(NORTHWEST_CONNECTION, aboveState.getValueOrElse(NORTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                            if (state.getValue(EAST_CONNECTION).isFull() && relativeState.getValue(EAST_CONNECTION).isFull()) state = state.setValue(NORTHEAST_CONNECTION, aboveState.getValueOrElse(NORTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);

                            BlockState clockwiseState = level.getBlockState(blockPos.relative(direction.getClockWise()));
                            if (clockwiseState.getBlock() instanceof TrunkBlock) {
                                if (state.getValue(NORTH_CONNECTION) != TrunkConnection.NONE && state.getValue(EAST_CONNECTION) != TrunkConnection.NONE
                                        && relativeState.getValue(EAST_CONNECTION).isFull() && clockwiseState.getValue(NORTH_CONNECTION).isFull()) {
                                    state = state.setValue(NORTHEAST_CONNECTION, aboveState.getValueOrElse(NORTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                                }
                            }
                            BlockState counterClockwiseState = level.getBlockState(blockPos.relative(direction.getCounterClockWise()));
                            if (counterClockwiseState.getBlock() instanceof TrunkBlock) {
                                if (state.getValue(NORTH_CONNECTION) != TrunkConnection.NONE && state.getValue(WEST_CONNECTION) != TrunkConnection.NONE
                                        && relativeState.getValue(WEST_CONNECTION).isFull() && counterClockwiseState.getValue(NORTH_CONNECTION).isFull()) {
                                    state = state.setValue(NORTHWEST_CONNECTION, aboveState.getValueOrElse(NORTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                                }
                            }
                        }
                        case EAST -> {
                            if (state.getValue(NORTH_CONNECTION).isFull() && relativeState.getValue(NORTH_CONNECTION).isFull()) state = state.setValue(NORTHEAST_CONNECTION, aboveState.getValueOrElse(NORTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                            if (state.getValue(SOUTH_CONNECTION).isFull() && relativeState.getValue(SOUTH_CONNECTION).isFull()) state = state.setValue(SOUTHEAST_CONNECTION, aboveState.getValueOrElse(SOUTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);

                            BlockState clockwiseState = level.getBlockState(blockPos.relative(direction.getClockWise()));
                            if (clockwiseState.getBlock() instanceof TrunkBlock) {
                                if (state.getValue(EAST_CONNECTION) != TrunkConnection.NONE && state.getValue(NORTH_CONNECTION) != TrunkConnection.NONE
                                        && relativeState.getValue(NORTH_CONNECTION).isFull() && clockwiseState.getValue(EAST_CONNECTION).isFull()) {
                                    state = state.setValue(NORTHEAST_CONNECTION, aboveState.getValueOrElse(NORTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                                }
                            }
                            BlockState counterClockwiseState = level.getBlockState(blockPos.relative(direction.getCounterClockWise()));
                            if (counterClockwiseState.getBlock() instanceof TrunkBlock) {
                                if (state.getValue(EAST_CONNECTION) != TrunkConnection.NONE && state.getValue(SOUTH_CONNECTION) != TrunkConnection.NONE
                                        && relativeState.getValue(SOUTH_CONNECTION).isFull() && counterClockwiseState.getValue(EAST_CONNECTION).isFull()) {
                                    state = state.setValue(SOUTHEAST_CONNECTION, aboveState.getValueOrElse(SOUTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                                }
                            }
                        }
                        case SOUTH -> {
                            if (state.getValue(WEST_CONNECTION).isFull() && relativeState.getValue(WEST_CONNECTION).isFull()) state = state.setValue(SOUTHWEST_CONNECTION, aboveState.getValueOrElse(SOUTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                            if (state.getValue(EAST_CONNECTION).isFull() && relativeState.getValue(EAST_CONNECTION).isFull()) state = state.setValue(SOUTHEAST_CONNECTION, aboveState.getValueOrElse(SOUTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);

                            BlockState clockwiseState = level.getBlockState(blockPos.relative(direction.getClockWise()));
                            if (clockwiseState.getBlock() instanceof TrunkBlock) {
                                if (state.getValue(SOUTH_CONNECTION) != TrunkConnection.NONE && state.getValue(WEST_CONNECTION) != TrunkConnection.NONE
                                        && relativeState.getValue(WEST_CONNECTION).isFull() && clockwiseState.getValue(SOUTH_CONNECTION).isFull()) {
                                    state = state.setValue(SOUTHWEST_CONNECTION, aboveState.getValueOrElse(SOUTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                                }
                            }
                            BlockState counterClockwiseState = level.getBlockState(blockPos.relative(direction.getCounterClockWise()));
                            if (counterClockwiseState.getBlock() instanceof TrunkBlock) {
                                if (state.getValue(SOUTH_CONNECTION) != TrunkConnection.NONE && state.getValue(EAST_CONNECTION) != TrunkConnection.NONE
                                        && relativeState.getValue(EAST_CONNECTION).isFull() && counterClockwiseState.getValue(SOUTH_CONNECTION).isFull()) {
                                    state = state.setValue(SOUTHEAST_CONNECTION, aboveState.getValueOrElse(SOUTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                                }
                            }
                        }
                        case WEST -> {
                            if (state.getValue(NORTH_CONNECTION).isFull() && relativeState.getValue(NORTH_CONNECTION).isFull()) state = state.setValue(NORTHWEST_CONNECTION, aboveState.getValueOrElse(NORTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                            if (state.getValue(SOUTH_CONNECTION).isFull() && relativeState.getValue(SOUTH_CONNECTION).isFull()) state = state.setValue(SOUTHWEST_CONNECTION, aboveState.getValueOrElse(SOUTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);

                            BlockState clockwiseState = level.getBlockState(blockPos.relative(direction.getClockWise()));
                            if (clockwiseState.getBlock() instanceof TrunkBlock) {
                                if (state.getValue(WEST_CONNECTION) != TrunkConnection.NONE && state.getValue(SOUTH_CONNECTION) != TrunkConnection.NONE
                                        && relativeState.getValue(SOUTH_CONNECTION).isFull() && clockwiseState.getValue(WEST_CONNECTION).isFull()) {
                                    state = state.setValue(SOUTHWEST_CONNECTION, aboveState.getValueOrElse(SOUTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                                }
                            }
                            BlockState counterClockwiseState = level.getBlockState(blockPos.relative(direction.getCounterClockWise()));
                            if (counterClockwiseState.getBlock() instanceof TrunkBlock) {
                                if (state.getValue(WEST_CONNECTION) != TrunkConnection.NONE && state.getValue(NORTH_CONNECTION) != TrunkConnection.NONE
                                        && relativeState.getValue(NORTH_CONNECTION).isFull() && counterClockwiseState.getValue(WEST_CONNECTION).isFull()) {
                                    state = state.setValue(NORTHWEST_CONNECTION, aboveState.getValueOrElse(NORTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                                }
                            }
                        }
                    }
                }
            }
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState relativeState = level.getBlockState(blockPos.relative(direction));
                if (relativeState.getBlock() instanceof TrunkBlock) {
                    switch (direction) {
                        case NORTH -> {
                            if (relativeState.getValue(SOUTHWEST_CONNECTION) != TrunkCorner.NONE) state = state.setValue(NORTHWEST_CONNECTION, aboveState.getValueOrElse(NORTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                            if (relativeState.getValue(SOUTHEAST_CONNECTION) != TrunkCorner.NONE) state = state.setValue(NORTHEAST_CONNECTION, aboveState.getValueOrElse(NORTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                        }
                        case EAST -> {
                            if (relativeState.getValue(NORTHWEST_CONNECTION) != TrunkCorner.NONE) state = state.setValue(NORTHEAST_CONNECTION, aboveState.getValueOrElse(NORTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                            if (relativeState.getValue(SOUTHWEST_CONNECTION) != TrunkCorner.NONE) state = state.setValue(SOUTHEAST_CONNECTION, aboveState.getValueOrElse(SOUTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                        }
                        case SOUTH -> {
                            if (relativeState.getValue(NORTHEAST_CONNECTION) != TrunkCorner.NONE) state = state.setValue(SOUTHEAST_CONNECTION, aboveState.getValueOrElse(SOUTHEAST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                            if (relativeState.getValue(NORTHWEST_CONNECTION) != TrunkCorner.NONE) state = state.setValue(SOUTHWEST_CONNECTION, aboveState.getValueOrElse(SOUTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                        }
                        case WEST -> {
                            if (relativeState.getValue(NORTHEAST_CONNECTION) != TrunkCorner.NONE) state = state.setValue(NORTHWEST_CONNECTION, aboveState.getValueOrElse(NORTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                            if (relativeState.getValue(SOUTHEAST_CONNECTION) != TrunkCorner.NONE) state = state.setValue(SOUTHWEST_CONNECTION, aboveState.getValueOrElse(SOUTHWEST_CONNECTION, TrunkCorner.NONE) != TrunkCorner.NONE ? TrunkCorner.TALL : TrunkCorner.NORMAL);
                        }
                    }
                }
            }
        }
        return state;
    }


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapeByIndex.get(state);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TALL, NORTH_CONNECTION, EAST_CONNECTION, SOUTH_CONNECTION, WEST_CONNECTION, NORTHEAST_CONNECTION, NORTHWEST_CONNECTION, SOUTHEAST_CONNECTION, SOUTHWEST_CONNECTION, WATERLOGGED);
    }

    public enum TrunkConnection implements StringRepresentable {
        NONE("none"),
        MATCHING("matching"),
        MATCHING_TALL("matching_tall"),
        FULL("full"),
        FULL_TALL("full_tall");

        private final String name;

        TrunkConnection(String name) {
            this.name = name;
        }

        public boolean isMatching() {
            return this == MATCHING || this == MATCHING_TALL;
        }

        public boolean isFull() {
            return this == FULL || this == FULL_TALL;
        }

        public boolean isTall() {
            return this == MATCHING_TALL || this == FULL_TALL;
        }

        public TrunkConnection tall() {
            switch(this) {
                case MATCHING -> {
                    return MATCHING_TALL;
                }
                case FULL -> {
                    return FULL_TALL;
                }
                default -> {
                    return NONE;
                }
            }
        }

        public String toString() {
            return this.getSerializedName();
        }

        public String getSerializedName() {
            return this.name;
        }
    }

    public enum TrunkCorner implements StringRepresentable {
        NONE("none"),
        NORMAL("normal"),
        TALL("tall");

        private final String name;

        TrunkCorner(String name) {
            this.name = name;
        }

        public String toString() {
            return this.getSerializedName();
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
