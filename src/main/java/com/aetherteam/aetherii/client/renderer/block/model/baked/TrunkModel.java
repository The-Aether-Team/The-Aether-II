package com.aetherteam.aetherii.client.renderer.block.model.baked;

import com.aetherteam.aetherii.client.renderer.block.model.NamedModelProperty;
import com.aetherteam.aetherii.client.renderer.block.model.unbaked.UnbakedTrunkModelLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.DelegateBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.ConcatenatedListView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
