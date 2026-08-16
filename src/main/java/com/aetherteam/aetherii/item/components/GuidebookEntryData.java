package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.List;

public record GuidebookEntryData(Identifier registry, String name, List<String> values) {
    public static final Codec<GuidebookEntryData> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Identifier.CODEC.fieldOf("registry").forGetter(GuidebookEntryData::registry),
            Codec.STRING.fieldOf("name").forGetter(GuidebookEntryData::name),
            Codec.STRING.listOf().fieldOf("values").forGetter(GuidebookEntryData::values)
    ).apply(builder, GuidebookEntryData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, GuidebookEntryData> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC,
            GuidebookEntryData::registry,
            ByteBufCodecs.STRING_UTF8,
            GuidebookEntryData::name,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
            GuidebookEntryData::values,
            GuidebookEntryData::new);

    public static GuidebookEntryData bestiary(ResourceKey<BestiaryEntry> name) {
        return new GuidebookEntryData(AetherIIRegistries.BESTIARY_ENTRY.identifier(), name.identifier().toString(), List.of());
    }

    public static GuidebookEntryData bestiary(ResourceKey<BestiaryEntry> name, String... values) {
        return new GuidebookEntryData(AetherIIRegistries.BESTIARY_ENTRY.identifier(), name.identifier().toString(), List.of(values));
    }

    public static GuidebookEntryData effects(ResourceKey<EffectsEntry> name) {
        return new GuidebookEntryData(AetherIIRegistries.EFFECTS_ENTRY.identifier(), name.identifier().toString(), List.of());
    }

    public static GuidebookEntryData effects(ResourceKey<EffectsEntry> name, String... values) {
        return new GuidebookEntryData(AetherIIRegistries.EFFECTS_ENTRY.identifier(), name.identifier().toString(), List.of(values));
    }

    public static GuidebookEntryData exploration(ResourceKey<ExplorationEntry> name) {
        return new GuidebookEntryData(AetherIIRegistries.EXPLORATION_ENTRY.identifier(), name.identifier().toString(), List.of());
    }

    public static GuidebookEntryData exploration(ResourceKey<ExplorationEntry> name, String... values) {
        return new GuidebookEntryData(AetherIIRegistries.EXPLORATION_ENTRY.identifier(), name.identifier().toString(), List.of(values));
    }
}
