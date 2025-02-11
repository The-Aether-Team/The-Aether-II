package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.google.common.collect.ImmutableList;
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
import java.util.Map;
import java.util.Optional;

public class BestiaryEntry extends GuidebookEntry {
    public static final DataTemplate<Holder<EntityType<?>>> ENTITY_TYPE = new DataTemplate<>("entity_type", BuiltInRegistries.ENTITY_TYPE.holderByNameCodec()::fieldOf);
    public static final DataTemplate<Integer> HEALTH = new DataTemplate<>("health", Codec.INT::fieldOf);
    public static final DataTemplate<Integer> SLASH_DEFENSE = new DataTemplate<>("slash_defense", Codec.INT::fieldOf);
    public static final DataTemplate<Integer> IMPACT_DEFENSE = new DataTemplate<>("impact_defense", Codec.INT::fieldOf);
    public static final DataTemplate<Integer> PIERCE_DEFENSE = new DataTemplate<>("pierce_defense", Codec.INT::fieldOf);
    public static final DataTemplate<Optional<Double>> SCALE_MULTIPLIER = new DataTemplate<>("scale_multiplier", Codec.DOUBLE::optionalFieldOf);
    public static final DataTemplate<Optional<LootDisplay>> LOOT_1 = new DataTemplate<>("loot_1", LootDisplay.DIRECT_CODEC::optionalFieldOf);
    public static final DataTemplate<Optional<LootDisplay>> LOOT_2 = new DataTemplate<>("loot_2", LootDisplay.DIRECT_CODEC::optionalFieldOf);
    public static final DataTemplate<Optional<LootDisplay>> LOOT_3 = new DataTemplate<>("loot_3", LootDisplay.DIRECT_CODEC::optionalFieldOf);
    public static final DataTemplate<Optional<TagKey<Item>>> FOOD = new DataTemplate<>("food", TagKey.codec(Registries.ITEM)::optionalFieldOf);

    public static final Codec<BestiaryEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    BestiaryEntry.ICON.mapCodec().forGetter(GuidebookEntry::getIcon),
                    BestiaryEntry.NAME.mapCodec().forGetter(GuidebookEntry::getName),
                    BestiaryEntry.SLOT_NAME.mapCodec().forGetter(GuidebookEntry::getSlotName),
                    BestiaryEntry.SLOT_SUBTITLE.mapCodec().forGetter(GuidebookEntry::getSlotSubtitle),
                    BestiaryEntry.DESCRIPTION_KEY.mapCodec().forGetter(GuidebookEntry::getDescriptionKey),
                    BestiaryEntry.ENTITY_TYPE.mapCodec().forGetter(BestiaryEntry::getEntityType),
                    BestiaryEntry.HEALTH.mapCodec().forGetter(BestiaryEntry::getHealth),
                    BestiaryEntry.SLASH_DEFENSE.mapCodec().forGetter(BestiaryEntry::getSlashDefense),
                    BestiaryEntry.IMPACT_DEFENSE.mapCodec().forGetter(BestiaryEntry::getImpactDefense),
                    BestiaryEntry.PIERCE_DEFENSE.mapCodec().forGetter(BestiaryEntry::getPierceDefense),
                    BestiaryEntry.SCALE_MULTIPLIER.mapCodec().forGetter(BestiaryEntry::getScaleMultiplier),
                    BestiaryEntry.LOOT_1.mapCodec().forGetter(BestiaryEntry::getLoot1),
                    BestiaryEntry.LOOT_2.mapCodec().forGetter(BestiaryEntry::getLoot2),
                    BestiaryEntry.LOOT_3.mapCodec().forGetter(BestiaryEntry::getLoot3),
                    BestiaryEntry.FOOD.mapCodec().forGetter(BestiaryEntry::getFood)
            ).apply(in, BestiaryEntry::new));
    public static final Codec<Holder<BestiaryEntry>> REFERENCE_CODEC = RegistryFileCodec.create(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BestiaryEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);

    private final Holder<EntityType<?>> entityType;
    private final int health;
    private final int slashDefense;
    private final int impactDefense;
    private final int pierceDefense;
    private final Optional<Double> scaleMultiplier;
    private final Optional<LootDisplay> loot1;
    private final Optional<LootDisplay> loot2;
    private final Optional<LootDisplay> loot3;
    private final Optional<TagKey<Item>> food;

    public BestiaryEntry(ResourceLocation icon, Optional<String> name, Optional<String> slotName, Optional<String> slotSubtitle, String descriptionKey, Holder<EntityType<?>> entityType, int health, int slashDefense, int impactDefense, int pierceDefense, Optional<Double> scaleMultiplier, Optional<LootDisplay> loot1, Optional<LootDisplay> loot2, Optional<LootDisplay> loot3, Optional<TagKey<Item>> food) {
        super(icon, name, slotName, slotSubtitle, descriptionKey);
        this.entityType = this.info(ENTITY_TYPE, entityType);
        this.health = this.info(HEALTH, health);
        this.slashDefense = this.info(SLASH_DEFENSE, slashDefense);
        this.impactDefense = this.info(IMPACT_DEFENSE, impactDefense);
        this.pierceDefense = this.info(PIERCE_DEFENSE, pierceDefense);
        this.scaleMultiplier = this.info(SCALE_MULTIPLIER, scaleMultiplier);
        this.loot1 = this.info(LOOT_1, loot1);
        this.loot2 = this.info(LOOT_2, loot2);
        this.loot3 = this.info(LOOT_3, loot3);
        this.food = this.info(FOOD, food);
    }

    public Holder<EntityType<?>> getEntityType() {
        return this.entityType;
    }

    public int getHealth() {
        return this.health;
    }

    public int getSlashDefense() {
        return this.slashDefense;
    }

    public int getImpactDefense() {
        return this.impactDefense;
    }

    public int getPierceDefense() {
        return this.pierceDefense;
    }

    public Optional<Double> getScaleMultiplier() {
        return this.scaleMultiplier;
    }

    public Optional<LootDisplay> getLoot1() {
        return this.loot1;
    }

    public Optional<LootDisplay> getLoot2() {
        return this.loot2;
    }

    public Optional<LootDisplay> getLoot3() {
        return this.loot3;
    }

    public List<Optional<LootDisplay>> getLoot() {
        return ImmutableList.of(this.getLoot1(), this.getLoot2(), this.getLoot3());
    }

    public Optional<TagKey<Item>> getFood() {
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

    public static class Mutable extends BestiaryEntry {
        public static final Codec<Mutable> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BestiaryEntry.REFERENCE_CODEC.fieldOf("entry").forGetter(Mutable::getEntry),
                Codec.unboundedMap(Codec.STRING, Info.CODEC).fieldOf("values").forGetter(Mutable::getClientValues)
        ).apply(instance, Mutable::new));

        private final Holder<BestiaryEntry> entry;
        private final Map<String, Info> clientValues;

        public Mutable(Holder<BestiaryEntry> entry) {
            this(entry, entry.value().getValues());
        }

        public Mutable(Holder<BestiaryEntry> entry, Map<String, Info> clientValues) {
            super(entry.value().getIcon(), entry.value().getName(), entry.value().getSlotName(), entry.value().getSlotSubtitle(), entry.value().getDescriptionKey(), entry.value().getEntityType(), entry.value().getHealth(), entry.value().getSlashDefense(), entry.value().getImpactDefense(), entry.value().getPierceDefense(), entry.value().getScaleMultiplier(), entry.value().getLoot1(), entry.value().getLoot2(), entry.value().getLoot3(), entry.value().getFood());
            this.entry = entry;
            this.clientValues = clientValues;
        }

        public Holder<BestiaryEntry> getEntry() {
            return this.entry;
        }

        public Map<String, Info> getClientValues() {
            return this.clientValues;
        }
    }
}
