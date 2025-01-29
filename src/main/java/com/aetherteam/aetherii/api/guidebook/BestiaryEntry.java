package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BestiaryEntry extends GuidebookEntry {
    public static final Codec<BestiaryEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    BestiaryEntry.ICON.mapCodec().forGetter((entry) -> entry.getIcon().getValue()),
                    BestiaryEntry.NAME.mapCodec().forGetter((entry) -> entry.getName().getValue()),
                    BestiaryEntry.SLOT_NAME.mapCodec().forGetter((entry) -> entry.getSlotName().getValue()),
                    BestiaryEntry.SLOT_SUBTITLE.mapCodec().forGetter((entry) -> entry.getSlotSubtitle().getValue()),
                    BestiaryEntry.DESCRIPTION_KEY.mapCodec().forGetter((entry) -> entry.getDescriptionKey().getValue()),
                    BestiaryEntry.ENTITY_TYPE.mapCodec().forGetter((entry) -> entry.getEntityType().getValue()),
                    BestiaryEntry.SCALE_MULTIPLIER.mapCodec().forGetter((entry) -> entry.getScaleMultiplier().getValue()),
                    BestiaryEntry.LOOT_1.mapCodec().forGetter((entry) -> entry.getLoot1().getValue()),
                    BestiaryEntry.LOOT_2.mapCodec().forGetter((entry) -> entry.getLoot2().getValue()),
                    BestiaryEntry.LOOT_3.mapCodec().forGetter((entry) -> entry.getLoot3().getValue()),
                    BestiaryEntry.FOOD.mapCodec().forGetter((entry) -> entry.getFood().getValue())
            ).apply(in, BestiaryEntry::new));
    public static final Codec<Holder<BestiaryEntry>> REFERENCE_CODEC = RegistryFileCodec.create(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BestiaryEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);

    public static final DataTemplate<Holder<EntityType<?>>> ENTITY_TYPE = new DataTemplate<>("entity_type", BuiltInRegistries.ENTITY_TYPE.holderByNameCodec()::fieldOf);
    public static final DataTemplate<Optional<Double>> SCALE_MULTIPLIER = new DataTemplate<>("scale_multiplier", Codec.DOUBLE::optionalFieldOf);
    public static final DataTemplate<LootDisplay> LOOT_1 = new DataTemplate<>("loot_1", LootDisplay.DIRECT_CODEC::fieldOf);
    public static final DataTemplate<LootDisplay> LOOT_2 = new DataTemplate<>("loot_2", LootDisplay.DIRECT_CODEC::fieldOf);
    public static final DataTemplate<LootDisplay> LOOT_3 = new DataTemplate<>("loot_3", LootDisplay.DIRECT_CODEC::fieldOf);
    public static final DataTemplate<Optional<TagKey<Item>>> FOOD = new DataTemplate<>("food", TagKey.codec(Registries.ITEM)::optionalFieldOf);

    private final Info<Holder<EntityType<?>>> entityType;
    private final Info<Optional<Double>> scaleMultiplier;
    private final Info<LootDisplay> loot1;
    private final Info<LootDisplay> loot2;
    private final Info<LootDisplay> loot3;
    private final Info<Optional<TagKey<Item>>> food;

    public BestiaryEntry(ResourceLocation icon, Optional<String> name, Optional<String> slotName, Optional<String> slotSubtitle, String descriptionKey, Holder<EntityType<?>> entityType, Optional<Double> scaleMultiplier, LootDisplay loot1, LootDisplay loot2, LootDisplay loot3, Optional<TagKey<Item>> food) {
        super(icon, name, slotName, slotSubtitle, descriptionKey);
        this.entityType = this.info(ENTITY_TYPE, entityType);
        this.scaleMultiplier = this.info(SCALE_MULTIPLIER, scaleMultiplier);
        this.loot1 = this.info(LOOT_1, loot1);
        this.loot2 = this.info(LOOT_2, loot2);
        this.loot3 = this.info(LOOT_3, loot3);
        this.food = this.info(FOOD, food);
    }

    public BestiaryEntry(Info<ResourceLocation> icon, Info<Optional<String>> name, Info<Optional<String>> slotName, Info<Optional<String>> slotSubtitle, Info<String> descriptionKey, Info<Holder<EntityType<?>>> entityType, Info<Optional<Double>> scaleMultiplier, Info<LootDisplay> loot1, Info<LootDisplay> loot2, Info<LootDisplay> loot3, Info<Optional<TagKey<Item>>> food) {
        super(icon, name, slotName, slotSubtitle, descriptionKey);
        this.entityType = entityType;
        this.scaleMultiplier = scaleMultiplier;
        this.loot1 = loot1;
        this.loot2 = loot2;
        this.loot3 = loot3;
        this.food = food;
    }

    public Info<Holder<EntityType<?>>> getEntityType() {
        return this.entityType;
    }

    public Info<Optional<Double>> getScaleMultiplier() {
        return this.scaleMultiplier;
    }

    public Info<LootDisplay> getLoot1() {
        return this.loot1;
    }

    public Info<LootDisplay> getLoot2() {
        return this.loot2;
    }

    public Info<LootDisplay> getLoot3() {
        return this.loot3;
    }

    public List<Info<LootDisplay>> getLoot() {
        return ImmutableList.of(this.getLoot1(), this.getLoot2(), this.getLoot3());
    }

    public Info<Optional<TagKey<Item>>> getFood() {
        return this.food;
    }

    public record LootDisplay(Either<Holder<Item>, Holder<Block>> item, double chance, int minCount, int maxCount) {
        public static final Codec<LootDisplay> DIRECT_CODEC =
                RecordCodecBuilder.create(in -> in.group(
                        Codec.either(BuiltInRegistries.ITEM.holderByNameCodec(), BuiltInRegistries.BLOCK.holderByNameCodec()).fieldOf("item").forGetter(LootDisplay::item),
                        Codec.DOUBLE.fieldOf("chance").forGetter(LootDisplay::chance),
                        Codec.INT.fieldOf("min_count").forGetter(LootDisplay::minCount),
                        Codec.INT.fieldOf("max_count").forGetter(LootDisplay::maxCount)
                ).apply(in, LootDisplay::new));

        public static LootDisplay item(Holder<Item> item, double chance, int minCount, int maxCount) {
            return new LootDisplay(Either.left(item), chance, minCount, maxCount);
        }

        public static LootDisplay block(Holder<Block> item, double chance, int minCount, int maxCount) {
            return new LootDisplay(Either.right(item), chance, minCount, maxCount);
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

    public static class Mutable extends BestiaryEntry { //todo this needs a codec
        private final Map<String, Info<?>> clientValues = new HashMap<>();

        public Mutable(BestiaryEntry entry) {
            super(entry.getIcon(), entry.getName(), entry.getSlotName(), entry.getSlotSubtitle(), entry.getDescriptionKey(), entry.getEntityType(), entry.getScaleMultiplier(), entry.getLoot1(), entry.getLoot2(), entry.getLoot3(), entry.getFood());
            this.clientValues.putAll(this.getValues());
        }

        @Override
        public Map<String, Info<?>> getValues() {
            return ImmutableMap.copyOf(this.clientValues);
        }
    }
}
