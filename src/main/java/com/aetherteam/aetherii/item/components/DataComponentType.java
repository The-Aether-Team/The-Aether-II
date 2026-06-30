package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataComponentType<T> {
    private static final Map<ResourceLocation, DataComponentType<?>> REGISTRY = new LinkedHashMap<>();

    public static final Codec<DataComponentType<?>> PERSISTENT_CODEC = ResourceLocation.CODEC.comapFlatMap(
            id -> {
                DataComponentType<?> type = REGISTRY.get(id);
                return type != null ? DataResult.success(type) : DataResult.error(() -> "Unknown data component type: " + id);
            },
            DataComponentType::id
    );

    private final ResourceLocation id;
    private final Codec<T> codec;

    public DataComponentType(ResourceLocation id, Codec<T> codec) {
        this.id = id;
        this.codec = codec;
        REGISTRY.put(id, this);
    }

    public ResourceLocation id() {
        return this.id;
    }

    public Codec<T> codecOrThrow() {
        return this.codec;
    }

    public DataComponentType<T> get() {
        return this;
    }

    @SuppressWarnings("unchecked")
    public static <T> DataComponentType<T> byId(ResourceLocation id) {
        return (DataComponentType<T>) REGISTRY.get(id);
    }
}
