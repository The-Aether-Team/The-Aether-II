package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.util.RegistryObjectUtil;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BestiaryEntry extends GuidebookEntry {
    public static final DataTemplate<Holder<EntityType<?>>> ENTITY_TYPE = new DataTemplate<>("entity_type", BuiltInRegistries.ENTITY_TYPE.holderByNameCodec()::fieldOf);
    public static final DataTemplate<Integer> HEALTH = new DataTemplate<>("health", Codec.INT::fieldOf);
    public static final DataTemplate<Integer> SLASH_DEFENSE = new DataTemplate<>("slash_defense", Codec.INT::fieldOf);
    public static final DataTemplate<Integer> IMPACT_DEFENSE = new DataTemplate<>("impact_defense", Codec.INT::fieldOf);
    public static final DataTemplate<Integer> PIERCE_DEFENSE = new DataTemplate<>("pierce_defense", Codec.INT::fieldOf);
    public static final DataTemplate<EffectResistanceDisplay> EFFECT_RESISTANCE = new DataTemplate<>("effect_resistance", EffectResistanceDisplay.DIRECT_CODEC::fieldOf);
    public static final DataTemplate<Optional<Double>> SCALE_MULTIPLIER = new DataTemplate<>("scale_multiplier", Codec.DOUBLE::optionalFieldOf);
    public static final DataTemplate<LootDisplay> LOOT = new DataTemplate<>("loot", LootDisplay.DIRECT_CODEC::fieldOf);
    public static final DataTemplate<Optional<TagKey<Item>>> FOOD = new DataTemplate<>("food", TagKey.codec(Registries.ITEM)::optionalFieldOf);

    public static final Codec<BestiaryEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    GuidebookEntry.MAP_CODEC.forGetter(GuidebookEntry::root),
                    BestiaryEntry.ENTITY_TYPE.mapCodec().forGetter(BestiaryEntry::getEntityType),
                    BestiaryEntry.HEALTH.mapCodec().forGetter(BestiaryEntry::getHealth),
                    BestiaryEntry.SLASH_DEFENSE.mapCodec().forGetter(BestiaryEntry::getSlashDefense),
                    BestiaryEntry.IMPACT_DEFENSE.mapCodec().forGetter(BestiaryEntry::getImpactDefense),
                    BestiaryEntry.PIERCE_DEFENSE.mapCodec().forGetter(BestiaryEntry::getPierceDefense),
                    BestiaryEntry.EFFECT_RESISTANCE.mapCodec().codec().listOf().fieldOf("effect_resistances").forGetter(BestiaryEntry::getEffectResistances),
                    BestiaryEntry.SCALE_MULTIPLIER.mapCodec().forGetter(BestiaryEntry::getScaleMultiplier),
                    BestiaryEntry.LOOT.mapCodec().codec().listOf().fieldOf("loot_slots").forGetter(BestiaryEntry::getLoot),
                    BestiaryEntry.FOOD.mapCodec().forGetter(BestiaryEntry::getFood)
            ).apply(in, BestiaryEntry::new));
    public static final Codec<Holder<BestiaryEntry>> REFERENCE_CODEC = RegistryFileCodec.create(AetherIIRegistries.BESTIARY_ENTRY, DIRECT_CODEC);
    public static final StreamCodec<FriendlyByteBuf, Holder<BestiaryEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIRegistries.BESTIARY_ENTRY);

    private final Holder<EntityType<?>> entityType;
    private final int health;
    private final int slashDefense;
    private final int impactDefense;
    private final int pierceDefense;
    private final List<EffectResistanceDisplay> effectResistances;
    private final Optional<Double> scaleMultiplier;
    private final List<LootDisplay> loot;
    private final Optional<TagKey<Item>> food;

    public BestiaryEntry(GuidebookEntry root, Holder<EntityType<?>> entityType, int health, int slashDefense, int impactDefense, int pierceDefense, List<EffectResistanceDisplay> effectResistances, Optional<Double> scaleMultiplier, List<LootDisplay> loot, Optional<TagKey<Item>> food) {
        this(root.getId(), root.getIcon(), root.getName(), root.getSlotName(), root.getSlotSubtitle(), root.getDescriptionKey(), entityType, health, slashDefense, impactDefense, pierceDefense, effectResistances, scaleMultiplier, loot, food);
    }

    public BestiaryEntry(ResourceLocation id, ResourceLocation icon, String name, String slotName, Optional<String> slotSubtitle, String descriptionKey, Holder<EntityType<?>> entityType, int health, int slashDefense, int impactDefense, int pierceDefense, List<EffectResistanceDisplay> effectResistances, Optional<Double> scaleMultiplier, List<LootDisplay> loot, Optional<TagKey<Item>> food) {
        super(id, icon, name, slotName, slotSubtitle, descriptionKey);
        this.entityType = this.info(ENTITY_TYPE, entityType);
        this.health = this.info(HEALTH, health);
        this.slashDefense = this.info(SLASH_DEFENSE, slashDefense);
        this.impactDefense = this.info(IMPACT_DEFENSE, impactDefense);
        this.pierceDefense = this.info(PIERCE_DEFENSE, pierceDefense);
        this.effectResistances = effectResistances;
        for (int i = 0; i < this.effectResistances.size(); i++) {
            this.info(EFFECT_RESISTANCE.id() + "_" + i, effectResistances.get(i));
        }
        this.scaleMultiplier = this.info(SCALE_MULTIPLIER, scaleMultiplier);
        this.loot = loot;
        for (int i = 0; i < this.loot.size(); i++) {
            this.info(LOOT.id() + "_" + i, loot.get(i));
        }
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

    public List<EffectResistanceDisplay> getEffectResistances() {
        return this.effectResistances;
    }

    public Optional<Double> getScaleMultiplier() {
        return this.scaleMultiplier;
    }

    public List<LootDisplay> getLoot() {
        return this.loot;
    }

    public Optional<TagKey<Item>> getFood() {
        return this.food;
    }

    @Override
    public String toString() {
        return "BestiaryEntry{" +
                "id=" + this.getId() +
                ", icon=" + this.getIcon() +
                ", name=" + this.getName() +
                ", slotName=" + this.getSlotName() +
                ", slotSubtitle=" + this.getSlotSubtitle() +
                ", descriptionKey=" + this.getDescriptionKey() +
                ", entityType=" + this.entityType +
                ", health=" + this.health +
                ", slashDefense=" + this.slashDefense +
                ", impactDefense=" + this.impactDefense +
                ", pierceDefense=" + this.pierceDefense +
                ", effectResistances=" + this.effectResistances +
                ", scaleMultiplier=" + this.scaleMultiplier +
                ", loot=" + this.loot +
                ", food=" + this.food +
                '}';
    }

    public record EffectResistanceDisplay(Holder<Attribute> attribute, int value) {
        public static final Codec<EffectResistanceDisplay> DIRECT_CODEC =
                RecordCodecBuilder.create(in -> in.group(
                        BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("attribute").forGetter(EffectResistanceDisplay::attribute),
                        Codec.INT.fieldOf("value").forGetter(EffectResistanceDisplay::value)
                ).apply(in, EffectResistanceDisplay::new));
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

        public static LootDisplay item(RegistryObject<? extends Item> item, double chance, int minCount, int maxCount) {
            return item(RegistryObjectUtil.item(item), chance, minCount, maxCount);
        }

        public static LootDisplay block(Holder<Block> item, double chance, int minCount, int maxCount) {
            return new LootDisplay(Either.right(item), chance, minCount, maxCount);
        }

        public static LootDisplay block(RegistryObject<? extends Block> item, double chance, int minCount, int maxCount) {
            return block(RegistryObjectUtil.block(item), chance, minCount, maxCount);
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

    public static class Mutable extends BestiaryEntry implements MutableEntry {
        public static final Codec<Mutable> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BestiaryEntry.REFERENCE_CODEC.fieldOf("entry").forGetter(Mutable::getEntry),
                Codec.unboundedMap(Codec.STRING, Info.CODEC).fieldOf("values").forGetter(Mutable::getClientValues)
        ).apply(instance, Mutable::new));
        public static final StreamCodec<FriendlyByteBuf, BestiaryEntry.Mutable> STREAM_CODEC = StreamCodec.composite(
                BestiaryEntry.STREAM_CODEC, BestiaryEntry.Mutable::getEntry,
                ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, Info.STREAM_CODEC), BestiaryEntry.Mutable::getClientValues,
                BestiaryEntry.Mutable::new);

        private final Holder<BestiaryEntry> entry;
        private final Map<String, Info> clientValues;

        public Mutable(Holder<BestiaryEntry> entry) {
            this(entry, entry.value().getValues());
        }

        public Mutable(Holder<BestiaryEntry> entry, Map<String, Info> clientValues) {
            super(entry.value().getId(), entry.value().getIcon(), entry.value().getName(), entry.value().getSlotName(), entry.value().getSlotSubtitle(), entry.value().getDescriptionKey(), entry.value().getEntityType(), entry.value().getHealth(), entry.value().getSlashDefense(), entry.value().getImpactDefense(), entry.value().getPierceDefense(), entry.value().getEffectResistances(), entry.value().getScaleMultiplier(), entry.value().getLoot(), entry.value().getFood());
            this.entry = entry;
            this.clientValues = clientValues;
        }

        public Holder<BestiaryEntry> getEntry() {
            return this.entry;
        }

        public Map<String, Info> getClientValues() {
            return this.clientValues;
        }

        @Override
        public String toString() {
            return "Mutable{" + "entry=" + this.entry + ", clientValues=" + this.clientValues + '}';
        }
    }
}
