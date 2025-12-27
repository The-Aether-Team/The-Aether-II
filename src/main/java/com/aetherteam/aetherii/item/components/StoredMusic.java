package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public record StoredMusic(Holder<Item> item, Holder<SoundEvent> sound) {
    public static final Codec<StoredMusic> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(StoredMusic::item),
            BuiltInRegistries.SOUND_EVENT.holderByNameCodec().fieldOf("sound").forGetter(StoredMusic::sound)
    ).apply(builder, StoredMusic::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, StoredMusic> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.ITEM),
            StoredMusic::item,
            ByteBufCodecs.holderRegistry(Registries.SOUND_EVENT),
            StoredMusic::sound,
            StoredMusic::new);
}
