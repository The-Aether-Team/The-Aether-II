package com.aetherteam.aetherii.client.renderer.block.model.builder;

import com.aetherteam.aetherii.client.renderer.block.model.unbaked.UnbakedTrunkModelLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.data.models.blockstates.Variant;
import net.neoforged.neoforge.client.model.generators.template.CustomLoaderBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrunkModelBuilder extends CustomLoaderBuilder {
    private Map<UnbakedTrunkModelLoader.Holder, Variant> connections = new LinkedHashMap<>();

    public TrunkModelBuilder() {
        super(UnbakedTrunkModelLoader.ID, false);
    }

    public void add(UnbakedTrunkModelLoader.Holder condition, Variant variant) {
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
            jsonObject.add("apply", entry.getValue().get());
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
