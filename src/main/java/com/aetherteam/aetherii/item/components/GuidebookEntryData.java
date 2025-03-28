package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record GuidebookEntryData(ResourceLocation registry, String name, List<String> values) {
    public static final Codec<GuidebookEntryData> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            ResourceLocation.CODEC.fieldOf("registry").forGetter(GuidebookEntryData::registry),
            Codec.STRING.fieldOf("name").forGetter(GuidebookEntryData::name),
            Codec.STRING.listOf().fieldOf("values").forGetter(GuidebookEntryData::values)
    ).apply(builder, GuidebookEntryData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, GuidebookEntryData> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            GuidebookEntryData::registry,
            ByteBufCodecs.STRING_UTF8,
            GuidebookEntryData::name,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
            GuidebookEntryData::values,
            GuidebookEntryData::new);
}
