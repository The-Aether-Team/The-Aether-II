package com.aetherteam.aetherii.client.renderer.block.model.unbaked;

import com.aetherteam.aetherii.AetherII;
import com.google.gson.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.UnbakedModelLoader;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class UnbakedTrunkModelLoader implements UnbakedModelLoader<UnbakedTrunkModel> {
    public static final UnbakedTrunkModelLoader INSTANCE = new UnbakedTrunkModelLoader();
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "trunk_model_loader");

    @Override
    public UnbakedTrunkModel read(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
        Map<Holder, Variant> corners = new LinkedHashMap<>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("connections");
        for (JsonElement element : jsonArray) {
            if (element instanceof JsonObject elementObject) {
                JsonObject conditionObject = elementObject.getAsJsonObject("when");
                String conditionKey = conditionObject.keySet().toArray(String[]::new)[0];
                String conditionValue = conditionObject.get(conditionKey).getAsString();
                Holder conditionHolder = new Holder(conditionKey, WallSide.valueOf(conditionValue.toUpperCase(Locale.ROOT)));

                JsonElement variantElement = elementObject.getAsJsonObject("apply");
                Variant variant = new Variant.Deserializer().deserialize(variantElement, null, context);

                corners.put(conditionHolder, variant);
            }
        }
        jsonObject.remove("connections");
        jsonObject.remove("loader");
        UnbakedModel model = context.deserialize(jsonObject, UnbakedModel.class);
        return new UnbakedTrunkModel(model, corners);
    }

    public record Holder(String name, WallSide value) { }
}
