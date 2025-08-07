package com.aetherteam.aetherii.client.renderer.block.model.builder;

//import com.aetherteam.aetherii.client.renderer.block.model.unbaked.OldUnbakedTrunkModelLoader;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.mojang.serialization.JsonOps;
//import net.minecraft.client.renderer.block.model.Variant;
//import net.neoforged.neoforge.client.model.generators.template.CustomLoaderBuilder;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class OldTrunkModelBuilder extends CustomLoaderBuilder {
//    private Map<OldUnbakedTrunkModelLoader.Holder, Variant> connections = new LinkedHashMap<>();
//
//    public OldTrunkModelBuilder() {
//        super(OldUnbakedTrunkModelLoader.ID, false);
//    }
//
//    public void add(OldUnbakedTrunkModelLoader.Holder condition, Variant variant) {
//        this.connections.putIfAbsent(condition, variant);
//    }
//
//    @Override
//    public JsonObject toJson(JsonObject json) {
//        json = super.toJson(json);
//        JsonArray elements = new JsonArray();
//        for (var entry : this.connections.entrySet()) {
//            JsonObject jsonObject = new JsonObject();
//            JsonObject condition = new JsonObject();
//            condition.addProperty(entry.getKey().name(), entry.getKey().value().getSerializedName());
//            jsonObject.add("when", condition);
//            jsonObject.add("apply", Variant.CODEC.encode(entry.getValue(), JsonOps.INSTANCE, new JsonObject()).getOrThrow()); //todo
//            elements.add(jsonObject);
//        }
//        json.add("connections", elements);
//        return json;
//    }
//
//    @Override
//    protected CustomLoaderBuilder copyInternal() {
//        OldTrunkModelBuilder builder = new OldTrunkModelBuilder();
//        builder.connections = this.connections;
//        return builder;
//    }
//}
