package com.aetherteam.aetherii.client.renderer.block.model.unbaked;

//import com.aetherteam.aetherii.client.renderer.block.model.baked.OldTrunkModel;
//import net.minecraft.client.renderer.block.model.BlockStateModel;
//import net.minecraft.client.renderer.block.model.ItemTransforms;
//import net.minecraft.client.renderer.block.model.TextureSlots;
//import net.minecraft.client.renderer.block.model.Variant;
//import net.minecraft.client.resources.model.ModelBaker;
//import net.minecraft.client.resources.model.ModelState;
//import net.minecraft.client.resources.model.UnbakedGeometry;
//import net.minecraft.client.resources.model.UnbakedModel;
//import net.minecraft.util.context.ContextMap;
//import net.neoforged.neoforge.client.model.DelegateUnbakedModel;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class OldUnbakedTrunkModel extends DelegateUnbakedModel {
//    private final Map<OldUnbakedTrunkModelLoader.Holder, Variant> connections;
//
//    protected OldUnbakedTrunkModel(UnbakedModel model, Map<OldUnbakedTrunkModelLoader.Holder, Variant> connections) {
//        super(model);
//        this.connections = connections;
//    }
//
//
//
//    @Override
//    public BakedModel bake(TextureSlots textures, ModelBaker baker, ModelState modelState, boolean useAmbientOcclusion, boolean usesBlockLight, ItemTransforms itemTransforms, ContextMap additionalProperties) {
//        Map<OldUnbakedTrunkModelLoader.Holder, BlockStateModel> bakedConnections = new LinkedHashMap<>();
//        for (var connection : this.connections.entrySet()) {
//            bakedConnections.put(connection.getKey(), baker.bake(connection.getValue().modelLocation(), connection.getValue()));
//        }
//        BlockStateModel base = super.bake(textures, baker, modelState, useAmbientOcclusion, usesBlockLight, itemTransforms, additionalProperties);
//        return new OldTrunkModel(base, bakedConnections);
//    }
//
//    @Override
//    public @Nullable UnbakedGeometry geometry() {
//        UnbakedGeometry geometry = super.geometry();
//        Map<OldUnbakedTrunkModelLoader.Holder, BlockStateModel> bakedConnections = new LinkedHashMap<>();
//        for (var connection : this.connections.entrySet()) {
//            bakedConnections.put(connection.getKey(), ge.bake(connection.getValue().modelLocation(), connection.getValue()));
//
//        }
//
//
//
//        return super.geometry();
//    }
//
//    @Override
//    public void resolveDependencies(Resolver resolver) {
//        super.resolveDependencies(resolver);
//        for (var connection : this.connections.entrySet()) {
//            resolver.markDependency(connection.getValue().modelLocation());
//        }
//    }
//}
