package com.aetherteam.aetherii.client.renderer.block.model.unbaked;

import com.aetherteam.aetherii.client.renderer.block.model.baked.TrunkModel;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.util.context.ContextMap;
import net.neoforged.neoforge.client.model.DelegateUnbakedModel;

import java.util.LinkedHashMap;
import java.util.Map;

public class UnbakedTrunkModel extends DelegateUnbakedModel {
    private final Map<UnbakedTrunkModelLoader.Holder, Variant> connections;

    protected UnbakedTrunkModel(UnbakedModel model, Map<UnbakedTrunkModelLoader.Holder, Variant> connections) {
        super(model);
        this.connections = connections;
    }

    @Override
    public BakedModel bake(TextureSlots textures, ModelBaker baker, ModelState modelState, boolean useAmbientOcclusion, boolean usesBlockLight, ItemTransforms itemTransforms, ContextMap additionalProperties) {
        Map<UnbakedTrunkModelLoader.Holder, BlockStateModel> bakedConnections = new LinkedHashMap<>();
        for (var connection : this.connections.entrySet()) {
            bakedConnections.put(connection.getKey(), baker.bake(connection.getValue().modelLocation(), connection.getValue()));
        }
        BlockStateModel base = super.bake(textures, baker, modelState, useAmbientOcclusion, usesBlockLight, itemTransforms, additionalProperties);
        return new TrunkModel(base, bakedConnections);
    }

    @Override
    public void resolveDependencies(Resolver resolver) {
        super.resolveDependencies(resolver);
        for (var connection : this.connections.entrySet()) {
            resolver.resolve(connection.getValue().modelLocation());
        }
    }
}
