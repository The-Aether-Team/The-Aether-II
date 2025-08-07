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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.ConcatenatedListView;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TrunkModel extends DelegateBlockStateModel {
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
        if (modelData == ModelData.EMPTY) {
            Map<String, WallSide> properties = TrunkBlock.getCornerProperties(level, pos);
            ModelData.Builder modelDataBuilder = ModelData.builder();
            for (var entry : properties.entrySet()) {
                NamedModelProperty<WallSide> property = getPropertyForName(entry.getKey());
                if (property != null) {
                    modelDataBuilder.with(property, entry.getValue());
                }
            }
            modelData = modelDataBuilder.build();
        }
        return modelData;
    }

    @Nullable
    protected static NamedModelProperty<WallSide> getPropertyForName(String name) {
        return switch (name) {
            case "northeast_connection" -> NORTHEAST_CONNECTION;
            case "northwest_connection" -> NORTHWEST_CONNECTION;
            case "southeast_connection" -> SOUTHEAST_CONNECTION;
            case "southwest_connection" -> SOUTHWEST_CONNECTION;
            default -> null;
        };
    }
}
