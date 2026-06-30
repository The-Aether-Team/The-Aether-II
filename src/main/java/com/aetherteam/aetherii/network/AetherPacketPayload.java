package com.aetherteam.aetherii.network;

import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface AetherPacketPayload {
    Type<? extends AetherPacketPayload> type();

    static <B, T extends AetherPacketPayload> StreamCodec<B, T> codec(BiConsumer<T, B> encoder, Function<B, T> decoder) {
        return StreamCodec.of((buffer, value) -> encoder.accept(value, buffer), decoder::apply);
    }

    record Type<T extends AetherPacketPayload>(ResourceLocation id) {
    }
}
