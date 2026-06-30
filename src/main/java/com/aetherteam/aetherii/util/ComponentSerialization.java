package com.aetherteam.aetherii.util;

import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

import java.util.Optional;

public final class ComponentSerialization {
    public static final Codec<Component> CODEC = Codec.STRING.comapFlatMap(
            value -> {
                Component component = Component.Serializer.fromJson(value);
                return component != null ? DataResult.success(component) : DataResult.error(() -> "Invalid component json: " + value);
            },
            Component.Serializer::toJson
    );
    public static final StreamCodec<FriendlyByteBuf, Component> STREAM_CODEC = StreamCodec.of(FriendlyByteBuf::writeComponent, FriendlyByteBuf::readComponent);
    public static final StreamCodec<FriendlyByteBuf, Optional<Component>> TRUSTED_OPTIONAL_STREAM_CODEC = ByteBufCodecs.optional(STREAM_CODEC);

    private ComponentSerialization() {
    }
}
