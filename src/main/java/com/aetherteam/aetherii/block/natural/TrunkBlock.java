package com.aetherteam.aetherii.block.natural;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TrunkBlock extends Block implements SimpleWaterloggedBlock {
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
    private static final List<EnumProperty<TrunkConnection>> SIDE_CONNECTIONS = List.of(NORTH_CONNECTION, EAST_CONNECTION, SOUTH_CONNECTION, WEST_CONNECTION);
    private static final List<EnumProperty<TrunkCorner>> CORNER_CONNECTIONS = List.of(NORTHEAST_CONNECTION, NORTHWEST_CONNECTION, SOUTHEAST_CONNECTION, SOUTHWEST_CONNECTION);
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
        this.shapeByIndex = this.makeShapes(3.0F, 5.0F, 13.0F, 16.0F);
    }

    private static VoxelShape applyCenterShape(VoxelShape baseShape, boolean property, VoxelShape lowShape, VoxelShape tallShape) {
        if (property) {
            return Shapes.or(baseShape, tallShape);
        } else {
            return Shapes.or(baseShape, lowShape);
        }
    }

    private static VoxelShape applySideShape(VoxelShape baseShape, TrunkConnection property, VoxelShape lowShape, VoxelShape tallShape) {
        if (property.isTall()) {
            return Shapes.or(baseShape, tallShape);
        } else {
            return property == TrunkConnection.NONE ? baseShape : Shapes.or(baseShape, lowShape);
        }
    }

    private static VoxelShape applyCornerShape(VoxelShape baseShape, TrunkCorner property, VoxelShape lowShape, VoxelShape tallShape) {
        if (property == TrunkCorner.TALL) {
            return Shapes.or(baseShape, tallShape);
        } else {
            return property == TrunkCorner.NONE ? baseShape : Shapes.or(baseShape, lowShape);
        }
    }

    private Map<BlockState, VoxelShape> makeShapes(float width, float depth, float lowHeight, float tallHeight) {
        float f = 8.0F - width;
        float f1 = 8.0F + width;
        float f2 = 8.0F - depth;
        float f3 = 8.0F + depth;

        VoxelShape centerShape = Block.box(f2, 0.0F, f2, f3, lowHeight, f3);

        VoxelShape northShape = Block.box(f2, 0.0F, 0.0F, f3, lowHeight, f3);
        VoxelShape southShape = Block.box(f2, 0.0F, f2, f3, lowHeight, 16.0F);
        VoxelShape westShape = Block.box(0.0F, 0.0F, f2, f3, lowHeight, f3);
        VoxelShape eastShape = Block.box(f2, 0.0F, f2, 16.0F, lowHeight, f3);

        VoxelShape northwestShape = Block.box(0.0F, 0.0F, 0.0F, f, lowHeight, f);
        VoxelShape northeastShape = Block.box(f1, 0.0F, 0.0F, 16.0F, lowHeight, f);
        VoxelShape southwestShape = Block.box(0.0F, 0.0F, f1, f, lowHeight, 16.0F);
        VoxelShape southeastShape = Block.box(f1, 0.0F, f1, 16.0F, lowHeight, 16.0F);

        VoxelShape centerTallShape = Block.box(f2, 0.0F, f2, f3, tallHeight, f3);

        VoxelShape northTallShape = Block.box(f2, 0.0F, 0.0F, f3, tallHeight, f3);
        VoxelShape southTallShape = Block.box(f2, 0.0F, f2, f3, tallHeight, 16.0F);
        VoxelShape westTallShape = Block.box(0.0F, 0.0F, f2, f3, tallHeight, f3);
        VoxelShape eastTallShape = Block.box(f2, 0.0F, f2, 16.0F, tallHeight, f3);

        VoxelShape northwestTallShape = Block.box(0.0F, 0.0F, 0.0F, f, tallHeight, f);
        VoxelShape northeastTallShape = Block.box(f1, 0.0F, 0.0F, 16.0F, tallHeight, f);
        VoxelShape southwestTallShape = Block.box(0.0F, 0.0F, f1, f, tallHeight, 16.0F);
        VoxelShape southeastTallShape = Block.box(f1, 0.0F, f1, 16.0F, tallHeight, 16.0F);

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
                                            VoxelShape shape = Shapes.empty();

                                            shape = applyCenterShape(shape, tall, centerShape, centerTallShape);

                                            shape = applySideShape(shape, north, northShape, northTallShape);
                                            shape = applySideShape(shape, east, eastShape, eastTallShape);
                                            shape = applySideShape(shape, south, southShape, southTallShape);
                                            shape = applySideShape(shape, west, westShape, westTallShape);

                                            shape = applyCornerShape(shape, northwest, northwestShape, northwestTallShape);
                                            shape = applyCornerShape(shape, northeast, northeastShape, northeastTallShape);
                                            shape = applyCornerShape(shape, southeast, southeastShape, southeastTallShape);
                                            shape = applyCornerShape(shape, southwest, southwestShape, southwestTallShape);

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

    @Nullable
    protected static EnumProperty<TrunkConnection> getPropertyForDirection(Direction direction) {
        switch (direction) {
            case NORTH -> {
                return NORTH_CONNECTION;
            }
            case SOUTH -> {
                return SOUTH_CONNECTION;
            }
            case EAST -> {
                return EAST_CONNECTION;
            }
            case WEST -> {
                return WEST_CONNECTION;
            }
            default -> {
                return null;
            }
        }
    }

    @Nullable
    protected static EnumProperty<TrunkCorner> getPropertyForCorner(Direction direction1, Direction direction2) {
        List<Direction> directions = List.of(direction1, direction2);
        if (directions.contains(Direction.NORTH) && directions.contains(Direction.EAST)) {
            return NORTHEAST_CONNECTION;
        } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.WEST)) {
            return NORTHWEST_CONNECTION;
        } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.EAST)) {
            return SOUTHEAST_CONNECTION;
        } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.WEST)) {
            return SOUTHWEST_CONNECTION;
        }
        return null;
    }

    protected static List<Direction> getAdjacentDirections(Direction direction) {
        switch (direction.getAxis()) {
            case X -> {
                return Arrays.asList(Direction.Axis.Z.getDirections());
            }
            case Z -> {
                return Arrays.asList(Direction.Axis.X.getDirections());
            }
            default -> {
                return List.of();
            }
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (state != null) {
            state = this.updateTop(state, level, level, pos, Direction.UP, pos.above(), level.getBlockState(pos.above()), context.getLevel().getRandom());
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos relativePos = pos.relative(direction);
                state = this.updateSides(state, level, level, pos, direction, relativePos, level.getBlockState(relativePos), context.getLevel().getRandom());
                state = this.updateCorners(state, level, level, pos, direction, relativePos, level.getBlockState(relativePos), context.getLevel().getRandom());
            }
        }
        return state;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        if (facing == Direction.DOWN) {
            return super.updateShape(state, levelReader, scheduledTickAccess, currentPos, facing, facingPos, facingState, randomSource);
        } else {
            if (facing == Direction.UP) {
                state = this.updateTop(state, levelReader, scheduledTickAccess, currentPos, facing, facingPos, facingState, randomSource);
            } else {
                state = this.updateSides(state, levelReader, scheduledTickAccess, currentPos, facing, facingPos, facingState, randomSource);
                state = this.updateCorners(state, levelReader, scheduledTickAccess, currentPos, facing, facingPos, facingState, randomSource);
            }
        }

        return state;
    }

    protected BlockState updateTop(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        state = state.setValue(TALL, !facingState.getShape(levelReader, facingPos).getFaceShape(facing.getOpposite()).isEmpty());

        for (EnumProperty<TrunkConnection> connectionProperty : SIDE_CONNECTIONS) {
            state = state.setValue(connectionProperty, tryRaiseConnection(state, levelReader, currentPos, connectionProperty, state.getValue(connectionProperty)));
        }
        for (EnumProperty<TrunkCorner> cornerProperty : CORNER_CONNECTIONS) {
            state = state.setValue(cornerProperty, tryRaiseCorner(state, levelReader, currentPos, cornerProperty, state.getValue(cornerProperty)));
        }

        return state;
    }

    protected BlockState updateSides(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        EnumProperty<TrunkConnection> connection = getPropertyForDirection(facing);
        if (connection != null) {
            boolean connects = connectsTo(facingState, facingState.isFaceSturdy(levelReader, facingPos, Direction.SOUTH), facing);
            TrunkConnection type = TrunkConnection.NONE;
            if (connects) {
                type =  tryRaiseConnection(state, levelReader, currentPos, connection, isShapeSideFull(levelReader, facing, facingPos, facingState) ? TrunkConnection.FULL : TrunkConnection.MATCHING);
            }
            state = state.setValue(connection, type);
        }
        return state;
    }

    protected BlockState updateCorners(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        // Side Case
        for (Direction adjacent : getAdjacentDirections(facing)) {
            EnumProperty<TrunkCorner> cornerProperty = getPropertyForCorner(facing, adjacent);
            if (cornerProperty != null) {
                EnumProperty<TrunkConnection> connectionProperty = getPropertyForDirection(adjacent);
                if (connectionProperty != null) {
                    if (facingState.getBlock() instanceof TrunkBlock) {
                        if (state.getValue(connectionProperty).isFull() && facingState.getValue(connectionProperty).isFull()) {
                            state = state.setValue(cornerProperty, tryRaiseCorner(state, levelReader, currentPos, cornerProperty, TrunkCorner.NORMAL));
                            continue;
                        }
                    }
                }
                state = state.setValue(cornerProperty, TrunkCorner.NONE);
            }
        }
        // Interior Corner Case
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            Direction clockwise = direction.getClockWise();
            boolean flag = isShapeSideFull(levelReader, direction, currentPos.relative(direction), levelReader.getBlockState(currentPos.relative(direction)))
                    && isShapeSideFull(levelReader, clockwise, currentPos.relative(clockwise), levelReader.getBlockState(currentPos.relative(clockwise)));
            EnumProperty<TrunkCorner> cornerProperty = getPropertyForCorner(direction, clockwise);
            if (cornerProperty != null) {
                state = state.setValue(cornerProperty, tryRaiseCorner(state, levelReader, currentPos, cornerProperty, flag ? TrunkCorner.NORMAL : TrunkCorner.NONE));
            }
        }
        // Exterior Corner Case
        for (Direction adjacent : getAdjacentDirections(facing)) {
            BlockPos adjacentPos = currentPos.relative(adjacent);
            BlockState adjacentState = levelReader.getBlockState(adjacentPos);
            EnumProperty<TrunkConnection> facingConnectionProperty = getPropertyForDirection(facing);
            EnumProperty<TrunkConnection> adjacentConnectionProperty = getPropertyForDirection(adjacent);
            EnumProperty<TrunkCorner> cornerProperty = getPropertyForCorner(facing, adjacent);
            EnumProperty<TrunkCorner> adjacentCornerProperty = getPropertyForCorner(facing, adjacent.getOpposite());
            EnumProperty<TrunkCorner> facingCornerProperty = getPropertyForCorner(facing.getOpposite(), adjacent);
            if (facingConnectionProperty != null && adjacentConnectionProperty != null && cornerProperty != null && adjacentCornerProperty != null && facingCornerProperty != null) {
                boolean flag = facingState.getBlock() instanceof TrunkBlock && facingState.getValue(adjacentConnectionProperty).isFull()
                        && adjacentState.getBlock() instanceof TrunkBlock && adjacentState.getValue(facingConnectionProperty).isFull();
                state = state.setValue(cornerProperty, tryRaiseCorner(state, levelReader, currentPos, cornerProperty, flag ? TrunkCorner.NORMAL : TrunkCorner.NONE));
                if (levelReader instanceof LevelAccessor levelAccessor && flag) {
                    if (facingState.getBlock() instanceof TrunkBlock) {
                        levelAccessor.setBlock(facingPos, facingState.setValue(facingCornerProperty, tryRaiseCorner(facingState, levelReader, facingPos, facingCornerProperty, TrunkCorner.NORMAL)), 3);
                    }
                    if (adjacentState.getBlock() instanceof TrunkBlock) {
                        levelAccessor.setBlock(adjacentPos, adjacentState.setValue(adjacentCornerProperty, tryRaiseCorner(adjacentState, levelReader, adjacentPos, adjacentCornerProperty, TrunkCorner.NORMAL)), 3);
                    }
                }
            }
        }
        return state;
    }

    private static boolean connectsTo(BlockState state, boolean sideSolid, Direction direction) {
        Block block = state.getBlock();
        boolean flag = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        return block instanceof TrunkBlock || state.is(BlockTags.WALLS) || !isExceptionForConnection(state) && sideSolid || block instanceof IronBarsBlock || flag;
    }

    private static boolean isShapeSideFull(LevelReader levelReader, Direction facing, BlockPos facingPos, BlockState facingState) {
        VoxelShape facingShape = facingState.getShape(levelReader, facingPos);
        return Block.isFaceFull(facingShape, facing);
    }

    private static TrunkConnection tryRaiseConnection(BlockState state, LevelReader levelReader, BlockPos pos, EnumProperty<TrunkConnection> connectionProperty, TrunkConnection connection) {
        BlockPos facingPos = pos.above();
        BlockState facingState = levelReader.getBlockState(facingPos);
        if (state.getValue(connectionProperty) != TrunkConnection.NONE) {
            if ((facingState.getBlock() instanceof TrunkBlock && facingState.getValue(connectionProperty) != TrunkConnection.NONE)
                    || (!(facingState.getBlock() instanceof TrunkBlock) && !facingState.getShape(levelReader, facingPos).getFaceShape(Direction.DOWN).isEmpty())) {
                return state.getValue(connectionProperty).tall();
            } else {
                return state.getValue(connectionProperty).normal();
            }
        }
        return connection;
    }

    private static TrunkCorner tryRaiseCorner(BlockState state, LevelReader levelReader, BlockPos pos, EnumProperty<TrunkCorner> cornerProperty, TrunkCorner corner) {
        BlockPos facingPos = pos.above();
        BlockState facingState = levelReader.getBlockState(facingPos);
        if (state.getValue(cornerProperty) != TrunkCorner.NONE) {
            if ((facingState.getBlock() instanceof TrunkBlock && facingState.getValue(cornerProperty) != TrunkCorner.NONE)
                    || (!(facingState.getBlock() instanceof TrunkBlock) && !facingState.getShape(levelReader, facingPos).getFaceShape(Direction.DOWN).isEmpty())) {
                return TrunkCorner.TALL;
            } else {
                return TrunkCorner.NORMAL;
            }
        }
        return corner;
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
                    return this;
                }
            }
        }

        public TrunkConnection normal() {
            switch(this) {
                case MATCHING_TALL -> {
                    return MATCHING;
                }
                case FULL_TALL -> {
                    return FULL;
                }
                default -> {
                    return this;
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
