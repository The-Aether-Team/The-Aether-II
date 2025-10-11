package com.aetherteam.aetherii.client.renderer.block.model;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.block.model.property.NamedModelProperty;
import com.google.gson.*;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.*;
import net.neoforged.neoforge.client.model.generators.template.CustomLoaderBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TrunkModelNew {
    public static final NamedModelProperty<WallSide> NORTHEAST_CONNECTION = new NamedModelProperty<>("northeast_connection");
    public static final NamedModelProperty<WallSide> NORTHWEST_CONNECTION = new NamedModelProperty<>("northwest_connection");
    public static final NamedModelProperty<WallSide> SOUTHEAST_CONNECTION = new NamedModelProperty<>("southeast_connection");
    public static final NamedModelProperty<WallSide> SOUTHWEST_CONNECTION = new NamedModelProperty<>("southwest_connection");

    public static class TrunkModelBuilder extends CustomLoaderBuilder {
        private Map<Holder, Variant> connections = new LinkedHashMap<>();

        public TrunkModelBuilder() {
            super(TrunkUnbakedModelLoader.ID, false);
        }

        public void add(Holder condition, Variant variant) {
            this.connections.putIfAbsent(condition, variant);
        }

        @Override
        public JsonObject toJson(JsonObject json) {
            json = super.toJson(json);
            JsonArray elements = new JsonArray();
            for (var entry : this.connections.entrySet()) {
                JsonObject jsonObject = new JsonObject();
                JsonObject condition = new JsonObject();
                condition.addProperty(entry.getKey().name(), entry.getKey().value().getSerializedName());
                jsonObject.add("when", condition);
                jsonObject.add("apply", Variant.CODEC.encodeStart(JsonOps.INSTANCE, entry.getValue()).getOrThrow());
                elements.add(jsonObject);
            }
            json.add("connections", elements);
            return json;
        }

        @Override
        protected CustomLoaderBuilder copyInternal() {
            TrunkModelBuilder builder = new TrunkModelBuilder();
            builder.connections = this.connections;
            return builder;
        }
    }

    public static class TrunkUnbakedModelLoader implements UnbakedModelLoader<TrunkUnbakedModel> {
        public static final TrunkUnbakedModelLoader INSTANCE = new TrunkUnbakedModelLoader();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "trunk_model_loader");

        private TrunkUnbakedModelLoader() { }

        @Override
        public TrunkUnbakedModel read(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
            Map<Holder, Variant> corners = new LinkedHashMap<>();
            JsonArray jsonArray = jsonObject.getAsJsonArray("connections");
            for (JsonElement element : jsonArray) {
                if (element instanceof JsonObject elementObject) {
                    JsonObject conditionObject = elementObject.getAsJsonObject("when");
                    String conditionKey = conditionObject.keySet().toArray(String[]::new)[0];
                    String conditionValue = conditionObject.get(conditionKey).getAsString();
                    Holder conditionHolder = new Holder(conditionKey, WallSide.valueOf(conditionValue.toUpperCase(Locale.ROOT)));

                    JsonElement variantElement = elementObject.getAsJsonObject("apply");
                    DataResult<Variant> variant = Variant.CODEC.parse(JsonOps.INSTANCE, variantElement);
                    corners.put(conditionHolder, variant.getOrThrow());
                }
            }
            jsonObject.remove("connections");
            jsonObject.remove("loader");

            StandardModelParameters params = StandardModelParameters.parse(jsonObject, context);
            return new TrunkUnbakedModel(params, new TrunkUnbakedGeometry(corners));
        }
    }

    public static class TrunkUnbakedModel extends AbstractUnbakedModel {
        private final TrunkUnbakedGeometry geometry;

        public TrunkUnbakedModel(StandardModelParameters params, TrunkUnbakedGeometry geometry) {
            super(params);
            this.geometry = geometry;
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            super.resolveDependencies(resolver);
            for (var connection : this.geometry.getConnections().entrySet()) {
                resolver.markDependency(connection.getValue().modelLocation());
            }
        }

        @Override
        public UnbakedGeometry geometry() {
            return this.geometry;
        }
    }

    public static class TrunkUnbakedGeometry implements ExtendedUnbakedGeometry {
        private final Map<Holder, Variant> connections;

        public TrunkUnbakedGeometry(Map<Holder, Variant> connections) {
            this.connections = connections;
        }

        @Override
        public QuadCollection bake(TextureSlots textureSlots, ModelBaker baker, ModelState state, ModelDebugName debugName, ContextMap additionalProperties) {
            QuadCollection.Builder builder = new QuadCollection.Builder();
            for (Map.Entry<Holder, Variant> connection : this.connections.entrySet()) {
                connection.getValue().bake(baker).getQuads(null).forEach(builder::addUnculledFace);
            }
            return builder.build();
        }

        public Map<Holder, Variant> getConnections() {
            return this.connections;
        }
    }

    public static class TrunkModel extends DelegateBlockStateModel {
        protected TrunkModel(BlockStateModel delegate) {
            super(delegate);
        }

        @Override
        public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
            super.collectParts(level, pos, state, random, parts);
        }
    }

    public record Holder(String name, WallSide value) { }
}
