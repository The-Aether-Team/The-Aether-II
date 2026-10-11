package com.aetherteam.aetherii.block.natural;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrunkBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<TrunkBlock> CODEC = simpleCodec(TrunkBlock::new);
    public static final BooleanProperty TALL = BooleanProperty.create("tall");
    public static final EnumProperty<WallSide> NORTH_CONNECTION = EnumProperty.create("north_connection", WallSide.class);
    public static final EnumProperty<WallSide> EAST_CONNECTION = EnumProperty.create("east_connection", WallSide.class);
    public static final EnumProperty<WallSide> SOUTH_CONNECTION = EnumProperty.create("south_connection", WallSide.class);
    public static final EnumProperty<WallSide> WEST_CONNECTION = EnumProperty.create("west_connection", WallSide.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final List<EnumProperty<WallSide>> SIDE_CONNECTIONS = List.of(NORTH_CONNECTION, EAST_CONNECTION, SOUTH_CONNECTION, WEST_CONNECTION);
    private static final Map<TrunkProperties, VoxelShape> SHAPE_BY_INDEX = makeShapes();

    public MapCodec<TrunkBlock> codec() {
        return CODEC;
    }

    public TrunkBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TALL, false)
                .setValue(NORTH_CONNECTION, WallSide.NONE)
                .setValue(EAST_CONNECTION, WallSide.NONE)
                .setValue(SOUTH_CONNECTION, WallSide.NONE)
                .setValue(WEST_CONNECTION, WallSide.NONE)
                .setValue(WATERLOGGED, false));
    }

    public static Map<TrunkProperties, VoxelShape> makeShapes() {
        float width = 3.0F;
        float depth = 5.0F;
        float lowHeight = 13.0F;
        float tallHeight = 16.0F;

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

        ImmutableMap.Builder<TrunkProperties, VoxelShape> builder = ImmutableMap.builder();
        for (boolean tall : TALL.getPossibleValues()) {
            for (WallSide north : NORTH_CONNECTION.getPossibleValues()) {
                for (WallSide east : EAST_CONNECTION.getPossibleValues()) {
                    for (WallSide south : SOUTH_CONNECTION.getPossibleValues()) {
                        for (WallSide west : WEST_CONNECTION.getPossibleValues()) {
                            for (WallSide northwest : WallSide.values()) {
                                for (WallSide northeast : WallSide.values()) {
                                    for (WallSide southeast : WallSide.values()) {
                                        for (WallSide southwest : WallSide.values()) {
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

                                            TrunkProperties trunkProperties = new TrunkProperties(tall, north, east, south, west, northwest, northeast, southeast, southwest);
                                            builder.put(trunkProperties, shape.optimize());
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

    private static VoxelShape applyCenterShape(VoxelShape baseShape, boolean property, VoxelShape lowShape, VoxelShape tallShape) {
        if (property) {
            return Shapes.joinUnoptimized(baseShape, tallShape, BooleanOp.OR);
        } else {
            return Shapes.joinUnoptimized(baseShape, lowShape, BooleanOp.OR);
        }
    }

    private static VoxelShape applySideShape(VoxelShape baseShape, WallSide property, VoxelShape lowShape, VoxelShape tallShape) {
        if (property == WallSide.TALL) {
            return Shapes.joinUnoptimized(baseShape, tallShape, BooleanOp.OR);
        } else {
            return property == WallSide.NONE ? baseShape : Shapes.joinUnoptimized(baseShape, lowShape, BooleanOp.OR);
        }
    }

    private static VoxelShape applyCornerShape(VoxelShape baseShape, WallSide property, VoxelShape lowShape, VoxelShape tallShape) {
        if (property == WallSide.TALL) {
            return Shapes.joinUnoptimized(baseShape, tallShape, BooleanOp.OR);
        } else {
            return property == WallSide.NONE ? baseShape : Shapes.joinUnoptimized(baseShape, lowShape, BooleanOp.OR);
        }
    }

    public static Map<String, WallSide> getCornerProperties(BlockGetter level, BlockPos pos) {
        Map<String, WallSide> properties = new LinkedHashMap<>();
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (Direction adjacent : getAdjacentDirections(facing)) {
                String cornerProperty = getNameForCorner(facing, adjacent);
                if (cornerProperty != null) {
                    BlockPos facingPos = pos.relative(facing);
                    BlockPos adjacentPos = pos.relative(adjacent);
                    BlockPos cornerPos = pos.relative(facing).relative(adjacent);

                    BlockState facingState = level.getBlockState(facingPos);
                    BlockState adjacentState = level.getBlockState(adjacentPos);
                    BlockState cornerState = level.getBlockState(cornerPos);

                    if (connectsTo(facingState) && connectsTo(adjacentState) && checkCornerCases(level, facing, adjacent, facingPos, adjacentPos, cornerPos, facingState, adjacentState, cornerState)) {
                        properties.putIfAbsent(cornerProperty, WallSide.LOW);
                    }

                    if (properties.get(cornerProperty) == WallSide.LOW) {
                        BlockPos abovePos = pos.above();
                        facingPos = facingPos.above();
                        adjacentPos = adjacentPos.above();
                        cornerPos = cornerPos.above();

                        BlockState aboveState = level.getBlockState(abovePos);
                        facingState = level.getBlockState(facingPos);
                        adjacentState = level.getBlockState(adjacentPos);
                        cornerState = level.getBlockState(cornerPos);

                        boolean basicCase = isShapeSidePresent(level, Direction.UP.getOpposite(), abovePos, aboveState);

                        if (basicCase || (isTrunk(aboveState) && checkCornerCases(level, facing, adjacent, facingPos, adjacentPos, cornerPos, facingState, adjacentState, cornerState))) {
                            properties.put(cornerProperty, WallSide.TALL);
                        }
                    }
                }
            }
        }
        return properties;
    }

    @Nullable
    public static EnumProperty<WallSide> getPropertyForDirection(Direction direction) {
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
    protected static String getNameForCorner(Direction direction1, Direction direction2) {
        List<Direction> directions = List.of(direction1, direction2);
        if (directions.contains(Direction.NORTH) && directions.contains(Direction.EAST)) {
            return "northeast_connection";
        } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.WEST)) {
            return "northwest_connection";
        } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.EAST)) {
            return "southeast_connection";
        } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.WEST)) {
            return "southwest_connection";
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
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos relativePos = pos.relative(direction);
                state = this.updateSides(state, level, level, pos, direction, relativePos, level.getBlockState(relativePos), context.getLevel().getRandom());
            }
            state = this.updateTop(state, level, level, pos, Direction.UP, pos.above(), level.getBlockState(pos.above()), context.getLevel().getRandom());
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
            }
        }

        return state;
    }

    protected BlockState updateTop(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        state = state.setValue(TALL, !facingState.getShape(levelReader, facingPos).getFaceShape(facing.getOpposite()).isEmpty());

        for (EnumProperty<WallSide> connectionProperty : SIDE_CONNECTIONS) {
            state = state.setValue(connectionProperty, tryRaiseConnection(state, levelReader, currentPos, connectionProperty, state.getValue(connectionProperty)));
        }

        return state;
    }

    protected BlockState updateSides(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        EnumProperty<WallSide> connection = getPropertyForDirection(facing);
        if (connection != null) {
            boolean connects = connectsTo(facingState, facingState.isFaceSturdy(levelReader, facingPos, facing));
            WallSide type = WallSide.NONE;
            if (connects) {
                type = tryRaiseConnection(state, levelReader, currentPos, connection, WallSide.LOW);
            }
            state = state.setValue(connection, type);
        }
        return state;
    }

    private static boolean checkCornerCases(BlockGetter level, Direction facing, Direction adjacent, BlockPos facingPos, BlockPos adjacentPos, BlockPos cornerPos, BlockState facingState, BlockState adjacentState, BlockState cornerState) {
        boolean lowCaseInner = isShapeSideFull(level, facing.getOpposite(), facingPos, facingState) && isShapeSideFull(level, adjacent.getOpposite(), adjacentPos, adjacentState);
        boolean lowCaseSide = (isShapeSideFull(level, facing.getOpposite(), facingPos, facingState) && isShapeSideFull(level, facing.getOpposite(), cornerPos, cornerState) && isTrunk(adjacentState))
                || (isShapeSideFull(level, adjacent.getOpposite(), adjacentPos, adjacentState) && isShapeSideFull(level, adjacent.getOpposite(), cornerPos, cornerState) && isTrunk(facingState));
        boolean lowCaseOuter1 = (isTrunk(adjacentState) && isTrunk(facingState) && isShapeSideFull(level, adjacent.getOpposite(), cornerPos, cornerState) && isShapeSideFull(level, facing.getOpposite(), cornerPos, cornerState));
        boolean lowCaseOuter2 = (isShapeSideFull(level, facing.getOpposite(), facingPos, facingState) && isShapeSideFull(level, adjacent, facingPos, facingState) && isTrunk(adjacentState) && isTrunk(cornerState));
        return lowCaseInner || lowCaseSide || lowCaseOuter1 || lowCaseOuter2;
    }

    private static WallSide tryRaiseConnection(BlockState state, LevelReader levelReader, BlockPos pos, EnumProperty<WallSide> connectionProperty, WallSide connection) {
        BlockPos facingPos = pos.above();
        BlockState facingState = levelReader.getBlockState(facingPos);
        if (connection != WallSide.NONE) {
            if ((isTrunk(facingState) && facingState.getValue(connectionProperty) != WallSide.NONE)
                    || (!isTrunk(facingState) && !facingState.getShape(levelReader, facingPos).getFaceShape(Direction.DOWN).isEmpty())) {
                return WallSide.TALL;
            } else {
                return WallSide.LOW;
            }
        } else {
            return connection;
        }
    }

    private static boolean connectsTo(BlockState state, boolean sideSolid) {
        return isTrunk(state) || (!isExceptionForConnection(state) && sideSolid);
    }

    private static boolean connectsTo(BlockState state) {
        return isTrunk(state) || !isExceptionForConnection(state);
    }

    private static boolean isShapeSidePresent(BlockGetter level, Direction facing, BlockPos facingPos, BlockState facingState) {
        return !isTrunk(facingState) && !facingState.getCollisionShape(level, facingPos).getFaceShape(facing).isEmpty() && !Block.isExceptionForConnection(facingState);
    }

    private static boolean isShapeSideFull(BlockGetter level, Direction facing, BlockPos facingPos, BlockState facingState) {
        return !isTrunk(facingState) && Block.isFaceFull(facingState.getCollisionShape(level, facingPos), facing) && facingState.isFaceSturdy(level, facingPos, facing) && !Block.isExceptionForConnection(facingState);
    }

    private static boolean isTrunk(BlockState state) {
        return state.getBlock() instanceof TrunkBlock;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_INDEX.get(TrunkProperties.fromBlock(state, getCornerProperties(level, pos)));
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
        builder.add(TALL, NORTH_CONNECTION, EAST_CONNECTION, SOUTH_CONNECTION, WEST_CONNECTION, WATERLOGGED);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180 -> {
                return state.setValue(NORTH_CONNECTION, state.getValue(SOUTH_CONNECTION)).setValue(EAST_CONNECTION, state.getValue(WEST_CONNECTION)).setValue(SOUTH_CONNECTION, state.getValue(NORTH_CONNECTION)).setValue(WEST_CONNECTION, state.getValue(EAST_CONNECTION));
            }
            case COUNTERCLOCKWISE_90 -> {
                return state.setValue(NORTH_CONNECTION, state.getValue(EAST_CONNECTION)).setValue(EAST_CONNECTION, state.getValue(SOUTH_CONNECTION)).setValue(SOUTH_CONNECTION, state.getValue(WEST_CONNECTION)).setValue(WEST_CONNECTION, state.getValue(NORTH_CONNECTION));
            }
            case CLOCKWISE_90 -> {
                return state.setValue(NORTH_CONNECTION, state.getValue(WEST_CONNECTION)).setValue(EAST_CONNECTION, state.getValue(NORTH_CONNECTION)).setValue(SOUTH_CONNECTION, state.getValue(EAST_CONNECTION)).setValue(WEST_CONNECTION, state.getValue(SOUTH_CONNECTION));
            }
            default -> {
                return state;
            }
        }
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT -> {
                return state.setValue(NORTH_CONNECTION, state.getValue(SOUTH_CONNECTION)).setValue(SOUTH_CONNECTION, state.getValue(NORTH_CONNECTION));
            }
            case FRONT_BACK -> {
                return state.setValue(EAST_CONNECTION, state.getValue(WEST_CONNECTION)).setValue(WEST_CONNECTION, state.getValue(EAST_CONNECTION));
            }
            default -> {
                return super.mirror(state, mirror);
            }
        }
    }

    public record TrunkProperties(boolean tall, WallSide north, WallSide east, WallSide south, WallSide west, WallSide northwest, WallSide northeast, WallSide southeast, WallSide southwest) {
        public static TrunkProperties fromBlock(BlockState state, Map<String, WallSide> map) {
            return new TrunkProperties(
                    state.getValue(TALL),
                    state.getValue(NORTH_CONNECTION),
                    state.getValue(EAST_CONNECTION),
                    state.getValue(SOUTH_CONNECTION),
                    state.getValue(WEST_CONNECTION),
                    map.computeIfAbsent("northwest_connection", (s) -> WallSide.NONE),
                    map.computeIfAbsent("northeast_connection", (s) -> WallSide.NONE),
                    map.computeIfAbsent("southeast_connection", (s) -> WallSide.NONE),
                    map.computeIfAbsent("southwest_connection", (s) -> WallSide.NONE)
            );
        }
    }
}
