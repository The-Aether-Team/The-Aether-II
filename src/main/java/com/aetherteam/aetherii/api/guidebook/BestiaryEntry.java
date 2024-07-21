package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;

public record BestiaryEntry(Holder<EntityType<?>> entityType, ResourceLocation icon, String descriptionKey, List<LootDisplay> loot, Optional<TagKey<Item>> food, ResourceLocation observationAdvancement, ResourceLocation understandingAdvancement) {
    public static final Codec<BestiaryEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    BuiltInRegistries.ENTITY_TYPE.holderByNameCodec().fieldOf("entity_type").forGetter(BestiaryEntry::entityType),
                    ResourceLocation.CODEC.fieldOf("icon_discovered").forGetter(BestiaryEntry::icon),
                    Codec.STRING.fieldOf("description_key").forGetter(BestiaryEntry::descriptionKey),
                    LootDisplay.DIRECT_CODEC.listOf().fieldOf("loot").forGetter(BestiaryEntry::loot),
                    TagKey.codec(Registries.ITEM).optionalFieldOf("food").forGetter(BestiaryEntry::food),
                    ResourceLocation.CODEC.fieldOf("observation_advancement").forGetter(BestiaryEntry::observationAdvancement),
                    ResourceLocation.CODEC.fieldOf("understanding_advancement").forGetter(BestiaryEntry::understandingAdvancement)
            ).apply(in, BestiaryEntry::new));
    public static final Codec<Holder<BestiaryEntry>> REFERENCE_CODEC = RegistryFileCodec.create(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BestiaryEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);

    public record LootDisplay(Holder<Item> item, double chance, int minCount, int maxCount) {
        public static final Codec<LootDisplay> DIRECT_CODEC =
                RecordCodecBuilder.create(in -> in.group(
                        BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(LootDisplay::item),
                        Codec.DOUBLE.fieldOf("chance").forGetter(LootDisplay::chance),
                        Codec.INT.fieldOf("min_count").forGetter(LootDisplay::minCount),
                        Codec.INT.fieldOf("max_count").forGetter(LootDisplay::maxCount)
                ).apply(in, LootDisplay::new));
    }
}
