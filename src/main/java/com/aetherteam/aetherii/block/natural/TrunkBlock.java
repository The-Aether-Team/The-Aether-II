package com.aetherteam.aetherii.block.natural;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
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
    public static final EnumProperty<WallSide> NORTH_CONNECTION = EnumProperty.create("north_connection", WallSide.class);
    public static final EnumProperty<WallSide> EAST_CONNECTION = EnumProperty.create("east_connection", WallSide.class);
    public static final EnumProperty<WallSide> SOUTH_CONNECTION = EnumProperty.create("south_connection", WallSide.class);
    public static final EnumProperty<WallSide> WEST_CONNECTION = EnumProperty.create("west_connection", WallSide.class);
    public static final EnumProperty<WallSide> NORTHEAST_CONNECTION = EnumProperty.create("northeast_connection", WallSide.class);
    public static final EnumProperty<WallSide> NORTHWEST_CONNECTION = EnumProperty.create("northwest_connection", WallSide.class);
    public static final EnumProperty<WallSide> SOUTHEAST_CONNECTION = EnumProperty.create("southeast_connection", WallSide.class);
    public static final EnumProperty<WallSide> SOUTHWEST_CONNECTION = EnumProperty.create("southwest_connection", WallSide.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final List<EnumProperty<WallSide>> SIDE_CONNECTIONS = List.of(NORTH_CONNECTION, EAST_CONNECTION, SOUTH_CONNECTION, WEST_CONNECTION);
    private static final List<EnumProperty<WallSide>> CORNER_CONNECTIONS = List.of(NORTHEAST_CONNECTION, NORTHWEST_CONNECTION, SOUTHEAST_CONNECTION, SOUTHWEST_CONNECTION);
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
                .setValue(NORTHEAST_CONNECTION, WallSide.NONE)
                .setValue(NORTHWEST_CONNECTION, WallSide.NONE)
                .setValue(SOUTHEAST_CONNECTION, WallSide.NONE)
                .setValue(SOUTHWEST_CONNECTION, WallSide.NONE)
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
                            for (WallSide northwest : NORTHWEST_CONNECTION.getPossibleValues()) {
                                for (WallSide northeast : NORTHEAST_CONNECTION.getPossibleValues()) {
                                    for (WallSide southeast : SOUTHEAST_CONNECTION.getPossibleValues()) {
                                        for (WallSide southwest : SOUTHWEST_CONNECTION.getPossibleValues()) {
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

    @Nullable
    protected static EnumProperty<WallSide> getPropertyForDirection(Direction direction) {
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
    protected static EnumProperty<WallSide> getPropertyForCorner(Direction direction1, Direction direction2) {
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
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos relativePos = pos.relative(direction);
                state = this.updateSides(state, level, level, pos, direction, relativePos, level.getBlockState(relativePos), context.getLevel().getRandom());
                state = this.updateCorners(state, level, level, pos, direction, relativePos, level.getBlockState(relativePos), context.getLevel().getRandom());
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
                state = this.updateCorners(state, levelReader, scheduledTickAccess, currentPos, facing, facingPos, facingState, randomSource);
            }
        }

        return state;
    }

    protected BlockState updateTop(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        state = state.setValue(TALL, !facingState.getShape(levelReader, facingPos).getFaceShape(facing.getOpposite()).isEmpty());

        for (EnumProperty<WallSide> connectionProperty : SIDE_CONNECTIONS) {
            state = state.setValue(connectionProperty, tryRaiseConnection(state, levelReader, currentPos, connectionProperty, state.getValue(connectionProperty)));
        }
        for (EnumProperty<WallSide> cornerProperty : CORNER_CONNECTIONS) {
            state = state.setValue(cornerProperty, tryRaiseCorner(state, levelReader, currentPos, cornerProperty, state.getValue(cornerProperty)));
        }

        return state;
    }

    protected BlockState updateSides(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        EnumProperty<WallSide> connection = getPropertyForDirection(facing);
        if (connection != null) {
            boolean connects = connectsTo(facingState, facingState.isFaceSturdy(levelReader, facingPos, Direction.SOUTH), facing);
            WallSide type = WallSide.NONE;
            if (connects) {
                type = tryRaiseConnection(state, levelReader, currentPos, connection, WallSide.LOW);
            }
            state = state.setValue(connection, type);
        }
        return state;
    }

    protected BlockState updateCorners(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        // Side Case
        for (Direction adjacent : getAdjacentDirections(facing)) {
            EnumProperty<WallSide> cornerProperty = getPropertyForCorner(facing, adjacent);
            if (cornerProperty != null) {
                EnumProperty<WallSide> connectionProperty = getPropertyForDirection(adjacent);
                if (connectionProperty != null) {
                    if (facingState.getBlock() instanceof TrunkBlock) {
                        if (isShapeSideFull(levelReader, adjacent, currentPos.relative(adjacent), levelReader.getBlockState(currentPos.relative(adjacent)))
                                && isShapeSideFull(levelReader, adjacent, facingPos.relative(adjacent), levelReader.getBlockState(facingPos.relative(adjacent)))) {
                            state = state.setValue(cornerProperty, tryRaiseCorner(state, levelReader, currentPos, cornerProperty, WallSide.LOW));
                            continue;
                        }
                    }
                }
                state = state.setValue(cornerProperty, WallSide.NONE);
            }
        }
        // Interior Corner Case
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            Direction clockwise = direction.getClockWise();
            boolean flag = isShapeSideFull(levelReader, direction, currentPos.relative(direction), levelReader.getBlockState(currentPos.relative(direction)))
                    && isShapeSideFull(levelReader, clockwise, currentPos.relative(clockwise), levelReader.getBlockState(currentPos.relative(clockwise)));
            EnumProperty<WallSide> cornerProperty = getPropertyForCorner(direction, clockwise);
            if (cornerProperty != null) {
                state = state.setValue(cornerProperty, tryRaiseCorner(state, levelReader, currentPos, cornerProperty, flag ? WallSide.LOW : WallSide.NONE));
            }
        }
        // Exterior Corner Case
        for (Direction adjacent : getAdjacentDirections(facing)) {
            BlockPos adjacentPos = currentPos.relative(adjacent);
            BlockState adjacentState = levelReader.getBlockState(adjacentPos);
            EnumProperty<WallSide> facingConnectionProperty = getPropertyForDirection(facing);
            EnumProperty<WallSide> adjacentConnectionProperty = getPropertyForDirection(adjacent);
            EnumProperty<WallSide> cornerProperty = getPropertyForCorner(facing, adjacent);
            EnumProperty<WallSide> adjacentCornerProperty = getPropertyForCorner(facing, adjacent.getOpposite());
            EnumProperty<WallSide> facingCornerProperty = getPropertyForCorner(facing.getOpposite(), adjacent);
            if (facingConnectionProperty != null && adjacentConnectionProperty != null && cornerProperty != null && adjacentCornerProperty != null && facingCornerProperty != null) {
                boolean flag = facingState.getBlock() instanceof TrunkBlock && isShapeSideFull(levelReader, adjacent, facingPos.relative(adjacent), levelReader.getBlockState(facingPos.relative(adjacent)))
                        && adjacentState.getBlock() instanceof TrunkBlock && isShapeSideFull(levelReader, facing, adjacentPos.relative(facing), levelReader.getBlockState(adjacentPos.relative(facing)));
                state = state.setValue(cornerProperty, tryRaiseCorner(state, levelReader, currentPos, cornerProperty, flag ? WallSide.LOW : WallSide.NONE));
                if (levelReader instanceof LevelAccessor levelAccessor && flag) {
                    if (facingState.getBlock() instanceof TrunkBlock) {
                        levelAccessor.setBlock(facingPos, facingState.setValue(facingCornerProperty, tryRaiseCorner(facingState, levelReader, facingPos, facingCornerProperty, WallSide.LOW)), 3);
                    }
                    if (adjacentState.getBlock() instanceof TrunkBlock) {
                        levelAccessor.setBlock(adjacentPos, adjacentState.setValue(adjacentCornerProperty, tryRaiseCorner(adjacentState, levelReader, adjacentPos, adjacentCornerProperty, WallSide.LOW)), 3);
                    }
                }
            }
        }
        return state;
    }

    private static boolean connectsTo(BlockState state, boolean sideSolid, Direction direction) {
        Block block = state.getBlock();
        return block instanceof TrunkBlock || !isExceptionForConnection(state) && sideSolid;
    }

    private static boolean isShapeSideFull(LevelReader levelReader, Direction facing, BlockPos facingPos, BlockState facingState) {
        VoxelShape facingShape = facingState.getShape(levelReader, facingPos);
        return Block.isFaceFull(facingShape, facing) && !isExceptionForConnection(facingState);
    }

    private static WallSide tryRaiseConnection(BlockState state, LevelReader levelReader, BlockPos pos, EnumProperty<WallSide> connectionProperty, WallSide connection) {
        BlockPos facingPos = pos.above();
        BlockState facingState = levelReader.getBlockState(facingPos);
        if (connection != WallSide.NONE) {
            if ((facingState.getBlock() instanceof TrunkBlock && facingState.getValue(connectionProperty) != WallSide.NONE)
                    || (!(facingState.getBlock() instanceof TrunkBlock) && !facingState.getShape(levelReader, facingPos).getFaceShape(Direction.DOWN).isEmpty())) {
                return WallSide.TALL;
            } else {
                return WallSide.LOW;
            }
        } else {
            return connection;
        }
    }

    private static WallSide tryRaiseCorner(BlockState state, LevelReader levelReader, BlockPos pos, EnumProperty<WallSide> cornerProperty, WallSide corner) {
        BlockPos facingPos = pos.above();
        BlockState facingState = levelReader.getBlockState(facingPos);
        if (state.getValue(cornerProperty) != WallSide.NONE) {
            if ((facingState.getBlock() instanceof TrunkBlock && facingState.getValue(cornerProperty) != WallSide.NONE)
                    || (!(facingState.getBlock() instanceof TrunkBlock) && !facingState.getShape(levelReader, facingPos).getFaceShape(Direction.DOWN).isEmpty())) {
                return WallSide.TALL;
            } else {
                return WallSide.LOW;
            }
        }
        return corner;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_INDEX.get(TrunkProperties.fromState(state));
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

    public record TrunkProperties(boolean tall, WallSide north, WallSide east, WallSide south, WallSide west, WallSide northwest, WallSide northeast, WallSide southeast, WallSide southwest) {
        public static TrunkProperties fromState(BlockState state) {
            return new TrunkProperties(
                    state.getValue(TALL),
                    state.getValue(NORTH_CONNECTION),
                    state.getValue(EAST_CONNECTION),
                    state.getValue(SOUTH_CONNECTION),
                    state.getValue(WEST_CONNECTION),
                    state.getValue(NORTHWEST_CONNECTION),
                    state.getValue(NORTHEAST_CONNECTION),
                    state.getValue(SOUTHEAST_CONNECTION),
                    state.getValue(SOUTHWEST_CONNECTION)
            );
        }
    }
}
