package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIExplorationEntries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
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

    public static GuidebookEntryData bestiary(ResourceKey<BestiaryEntry> name) {
        return new GuidebookEntryData(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY.location(), name.location().toString(), List.of());
    }

    public static GuidebookEntryData bestiary(ResourceKey<BestiaryEntry> name, String... values) {
        return new GuidebookEntryData(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY.location(), name.location().toString(), List.of(values));
    }

    public static GuidebookEntryData effects(ResourceKey<EffectsEntry> name) {
        return new GuidebookEntryData(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY.location(), name.location().toString(), List.of());
    }

    public static GuidebookEntryData effects(ResourceKey<EffectsEntry> name, String... values) {
        return new GuidebookEntryData(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY.location(), name.location().toString(), List.of(values));
    }

    public static GuidebookEntryData exploration(ResourceKey<ExplorationEntry> name) {
        return new GuidebookEntryData(AetherIIExplorationEntries.EXPLORATION_ENTRY_REGISTRY_KEY.location(), name.location().toString(), List.of());
    }

    public static GuidebookEntryData exploration(ResourceKey<ExplorationEntry> name, String... values) {
        return new GuidebookEntryData(AetherIIExplorationEntries.EXPLORATION_ENTRY_REGISTRY_KEY.location(), name.location().toString(), List.of(values));
    }
}
