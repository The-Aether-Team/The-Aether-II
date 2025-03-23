package com.aetherteam.aetherii.client.renderer.block.model.baked;

import com.aetherteam.aetherii.block.natural.TrunkBlock;
import com.aetherteam.aetherii.client.renderer.block.model.NamedModelProperty;
import com.aetherteam.aetherii.client.renderer.block.model.unbaked.UnbakedTrunkModelLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.DelegateBakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.ConcatenatedListView;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TrunkModel extends DelegateBakedModel {
    public static final NamedModelProperty<WallSide> NORTHEAST_CONNECTION = new NamedModelProperty<>("northeast_connection");
    public static final NamedModelProperty<WallSide> NORTHWEST_CONNECTION = new NamedModelProperty<>("northwest_connection");
    public static final NamedModelProperty<WallSide> SOUTHEAST_CONNECTION = new NamedModelProperty<>("southeast_connection");
    public static final NamedModelProperty<WallSide> SOUTHWEST_CONNECTION = new NamedModelProperty<>("southwest_connection");
    public static final List<NamedModelProperty<WallSide>> CONNECTIONS = List.of(NORTHEAST_CONNECTION, NORTHWEST_CONNECTION, SOUTHEAST_CONNECTION, SOUTHWEST_CONNECTION);
    
    private final Map<UnbakedTrunkModelLoader.Holder, BakedModel> connections;

    public TrunkModel(BakedModel model, Map<UnbakedTrunkModelLoader.Holder, BakedModel> connections) {
        super(model);
        this.connections = connections;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
        List<List<BakedQuad>> quads = new ArrayList<>();
        quads.add(this.parent.getQuads(state, side, rand, extraData, renderType));
        for (var connection : this.connections.entrySet()) {
            UnbakedTrunkModelLoader.Holder holder = connection.getKey();
            this.getConnectionFromString(holder.name(), extraData).ifPresent((wallSide) -> {
                List<BakedQuad> connectionQuads = connection.getValue().getQuads(state, side, rand, extraData, renderType);
                if (!connectionQuads.isEmpty() && wallSide != WallSide.NONE && wallSide == holder.value()) {
                    quads.add(connectionQuads);
                }
            });
        }
        return ConcatenatedListView.of(quads);
    }

    private Optional<WallSide> getConnectionFromString(String name, ModelData data) {
        for (var properties : CONNECTIONS) {
            if (name.equals(properties.getName())) {
                WallSide connection = data.get(properties);
                if (connection != null) {
                    return Optional.of(connection);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public ModelData getModelData(BlockAndTintGetter level, BlockPos pos, BlockState state, ModelData modelData) {
        Map<NamedModelProperty<WallSide>, WallSide> properties = new LinkedHashMap<>();
        if (modelData == ModelData.EMPTY) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                for (Direction adjacent : getAdjacentDirections(facing)) {
                    NamedModelProperty<WallSide> cornerProperty = getPropertyForCorner(facing, adjacent);
                    if (cornerProperty != null) {
                        BlockPos facingPos = pos.relative(facing);
                        BlockPos adjacentPos = pos.relative(adjacent);
                        BlockPos cornerPos = pos.relative(facing).relative(adjacent);

                        BlockState facingState = level.getBlockState(facingPos);
                        BlockState adjacentState = level.getBlockState(adjacentPos);
                        BlockState cornerState = level.getBlockState(cornerPos);

                        if (connectsTo(facingState) && connectsTo(adjacentState) && checkCases(level, facing, adjacent, facingPos, adjacentPos, cornerPos, facingState, adjacentState, cornerState)) {
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

                            if ((!isTrunk(aboveState) && basicCase) || (isTrunk(aboveState) && checkCases(level, facing, adjacent, facingPos, adjacentPos, cornerPos, facingState, adjacentState, cornerState))) {
                                properties.put(cornerProperty, WallSide.TALL);
                            }
                        }
                    }
                }
            }
        }
        ModelData.Builder modelDataBuilder = ModelData.builder();
        for (var entry : properties.entrySet()) {
            modelDataBuilder.with(entry.getKey(), entry.getValue());
        }
        modelData = modelDataBuilder.build();
        return modelData;
    }

    private static boolean checkCases(BlockAndTintGetter level, Direction facing, Direction adjacent, BlockPos facingPos, BlockPos adjacentPos, BlockPos cornerPos, BlockState facingState, BlockState adjacentState, BlockState cornerState) {
        boolean lowCaseInner = isShapeSideFull(level, facing.getOpposite(), facingPos, facingState) && isShapeSideFull(level, adjacent.getOpposite(), adjacentPos, adjacentState);
        boolean lowCaseSide = (isShapeSideFull(level, facing.getOpposite(), facingPos, facingState) && isShapeSideFull(level, facing.getOpposite(), cornerPos, cornerState) && isTrunk(adjacentState))
                || (isShapeSideFull(level, adjacent.getOpposite(), adjacentPos, adjacentState) && isShapeSideFull(level, adjacent.getOpposite(), cornerPos, cornerState) && isTrunk(facingState));
        boolean lowCaseOuter1 = (isTrunk(adjacentState) && isTrunk(facingState) && isShapeSideFull(level, adjacent.getOpposite(), cornerPos, cornerState) && isShapeSideFull(level, facing.getOpposite(), cornerPos, cornerState));
        boolean lowCaseOuter2 = (isShapeSideFull(level, facing.getOpposite(), facingPos, facingState) && isShapeSideFull(level, adjacent, facingPos, facingState) && isTrunk(adjacentState) && isTrunk(cornerState));
        return lowCaseInner || lowCaseSide || lowCaseOuter1 || lowCaseOuter2;
    }

    private static boolean connectsTo(BlockState state) {
        return isTrunk(state) || !Block.isExceptionForConnection(state);
    }

    private static boolean isShapeSidePresent(BlockGetter level, Direction facing, BlockPos facingPos, BlockState facingState) {
        return !facingState.getShape(level, facingPos).getFaceShape(facing).isEmpty() && !Block.isExceptionForConnection(facingState);
    }

    private static boolean isShapeSideFull(BlockGetter level, Direction facing, BlockPos facingPos, BlockState facingState) {
        VoxelShape facingShape = facingState.getShape(level, facingPos);
        return Block.isFaceFull(facingShape, facing) && !Block.isExceptionForConnection(facingState);
    }

    private static boolean isTrunk(BlockState state) {
        return state.getBlock() instanceof TrunkBlock;
    }

    @Nullable
    protected static NamedModelProperty<WallSide> getPropertyForCorner(Direction direction1, Direction direction2) {
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
}
