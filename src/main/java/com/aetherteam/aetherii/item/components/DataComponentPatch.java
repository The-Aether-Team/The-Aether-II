package com.aetherteam.aetherii.item.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataComponentPatch {
    private final Map<DataComponentType<?>, Object> values;

    private DataComponentPatch(Map<DataComponentType<?>, Object> values) {
        this.values = Map.copyOf(values);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void apply(ItemStack stack) {
        this.values.forEach((type, value) -> setUnchecked(stack, type, value));
    }

    public Map<DataComponentType<?>, Object> values() {
        return this.values;
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        this.values.forEach((type, value) -> addToJson(object, type, value));
        return object;
    }

    public static DataComponentPatch fromJson(JsonObject object) {
        Builder builder = builder();
        for (String key : object.keySet()) {
            DataComponentType<Object> type = DataComponentType.byId(new net.minecraft.resources.ResourceLocation(key));
            if (type != null) {
                type.codecOrThrow().parse(JsonOps.INSTANCE, object.get(key)).result().ifPresent(value -> builder.set(type, value));
            }
        }
        return builder.build();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void setUnchecked(ItemStack stack, DataComponentType type, Object value) {
        AetherIIDataComponents.set(stack, type, value);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void addToJson(JsonObject object, DataComponentType type, Object value) {
        type.codecOrThrow().encodeStart(JsonOps.INSTANCE, value).result().ifPresent(element -> object.add(type.id().toString(), (JsonElement) element));
    }

    public static class Builder {
        private final Map<DataComponentType<?>, Object> values = new LinkedHashMap<>();

        public <T> Builder set(DataComponentType<T> type, T value) {
            this.values.put(type, value);
            return this;
        }

        public DataComponentPatch build() {
            return new DataComponentPatch(this.values);
        }
    }
}
