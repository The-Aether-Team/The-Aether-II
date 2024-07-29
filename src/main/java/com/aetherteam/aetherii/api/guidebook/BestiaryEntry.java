package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.mojang.datafixers.util.Either;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public record BestiaryEntry(Holder<EntityType<?>> entityType, ResourceLocation icon, Optional<String> name, Optional<String> slotName, Optional<String> slotSubtitle, String descriptionKey, List<LootDisplay> loot, Optional<TagKey<Item>> food, ResourceLocation observationAdvancement, ResourceLocation understandingAdvancement) {
    public static final Codec<BestiaryEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    BuiltInRegistries.ENTITY_TYPE.holderByNameCodec().fieldOf("entity_type").forGetter(BestiaryEntry::entityType),
                    ResourceLocation.CODEC.fieldOf("icon_discovered").forGetter(BestiaryEntry::icon),
                    Codec.STRING.optionalFieldOf("name").forGetter(BestiaryEntry::name),
                    Codec.STRING.optionalFieldOf("slot_name").forGetter(BestiaryEntry::slotName),
                    Codec.STRING.optionalFieldOf("slot_subtitle").forGetter(BestiaryEntry::slotSubtitle),
                    Codec.STRING.fieldOf("description_key").forGetter(BestiaryEntry::descriptionKey),
                    LootDisplay.DIRECT_CODEC.sizeLimitedListOf(3).fieldOf("loot").forGetter(BestiaryEntry::loot),
                    TagKey.codec(Registries.ITEM).optionalFieldOf("food").forGetter(BestiaryEntry::food),
                    ResourceLocation.CODEC.fieldOf("observation_advancement").forGetter(BestiaryEntry::observationAdvancement),
                    ResourceLocation.CODEC.fieldOf("understanding_advancement").forGetter(BestiaryEntry::understandingAdvancement)
            ).apply(in, BestiaryEntry::new));
    public static final Codec<Holder<BestiaryEntry>> REFERENCE_CODEC = RegistryFileCodec.create(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BestiaryEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);

    public static final class LootDisplay {
            public static final Codec<LootDisplay> DIRECT_CODEC =
                    RecordCodecBuilder.create(in -> in.group(
                            Codec.either(BuiltInRegistries.ITEM.holderByNameCodec(), BuiltInRegistries.BLOCK.holderByNameCodec()).fieldOf("item").forGetter(LootDisplay::item),
                            Codec.DOUBLE.fieldOf("chance").forGetter(LootDisplay::chance),
                            Codec.INT.fieldOf("min_count").forGetter(LootDisplay::minCount),
                            Codec.INT.fieldOf("max_count").forGetter(LootDisplay::maxCount)
                    ).apply(in, LootDisplay::new));

        private final Either<Holder<Item>, Holder<Block>> item;
        private final double chance;
        private final int minCount;
        private final int maxCount;

        public LootDisplay(Either<Holder<Item>, Holder<Block>> item, double chance, int minCount, int maxCount) {
            this.item = item;
            this.chance = chance;
            this.minCount = minCount;
            this.maxCount = maxCount;
        }

        public static LootDisplay item(Holder<Item> item, double chance, int minCount, int maxCount) {
            return new LootDisplay(Either.left(item), chance, minCount, maxCount);
        }

        public static LootDisplay block(Holder<Block> item, double chance, int minCount, int maxCount) {
            return new LootDisplay(Either.right(item), chance, minCount, maxCount);
        }

        public Either<Holder<Item>, Holder<Block>> item() {
            return this.item;
        }

        public double chance() {
            return this.chance;
        }

        public int minCount() {
            return this.minCount;
        }

        public int maxCount() {
            return this.maxCount;
        }

        public ItemLike getItemLike() {
            if (this.item.right().isPresent()) {
                return this.item.right().get().value();
            } else if (this.item.left().isPresent()) {
                return this.item.left().get().value();
            } else {
                return Items.AIR;
            }
        }
    }
}
