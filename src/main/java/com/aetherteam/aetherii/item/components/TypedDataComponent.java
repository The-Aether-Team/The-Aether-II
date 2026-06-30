package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record TypedDataComponent<T>(DataComponentType<T> type, T value) {
    public static final StreamCodec<FriendlyByteBuf, TypedDataComponent<?>> STREAM_CODEC = StreamCodec.of((buffer, component) -> {
        buffer.writeResourceLocation(component.type().id());
        encodeUnchecked(buffer, component);
    }, buffer -> {
        ResourceLocation id = buffer.readResourceLocation();
        DataComponentType<?> type = DataComponentType.byId(id);
        if (type == null) {
            throw new IllegalArgumentException("Unknown data component type: " + id);
        }
        Object value = ByteBufCodecs.fromCodec(type.codecOrThrow()).decode(buffer);
        return createUnchecked(type, value);
    });

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void encodeUnchecked(FriendlyByteBuf buffer, TypedDataComponent<?> component) {
        ((StreamCodec) ByteBufCodecs.fromCodec(component.type().codecOrThrow())).encode(buffer, component.value());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> TypedDataComponent<T> createUnchecked(DataComponentType<T> type, Object value) {
        return new TypedDataComponent(type, value);
    }
}
