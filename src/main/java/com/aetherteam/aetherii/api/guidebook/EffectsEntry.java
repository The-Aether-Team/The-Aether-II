package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EffectsEntry extends GuidebookEntry {
    public static final DataTemplate<Holder<MobEffect>> EFFECT = new DataTemplate<>("effect", BuiltInRegistries.MOB_EFFECT.holderByNameCodec()::fieldOf);
    public static final DataTemplate<Holder<Item>> ITEM = new DataTemplate<>("item", BuiltInRegistries.ITEM.holderByNameCodec()::fieldOf);

    public static final Codec<EffectsEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    GuidebookEntry.MAP_CODEC.forGetter(GuidebookEntry::root),
                    EffectsEntry.EFFECT.mapCodec().forGetter(EffectsEntry::getEffect),
                    Codec.list(EffectsEntry.ITEM.mapCodec().codec(), 0, 6).fieldOf("items").forGetter(EffectsEntry::getItems)
            ).apply(in, EffectsEntry::new));
    public static final Codec<Holder<EffectsEntry>> REFERENCE_CODEC = RegistryFileCodec.create(AetherIIRegistries.EFFECTS_ENTRY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<EffectsEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIRegistries.EFFECTS_ENTRY);

    private final Holder<MobEffect> effect;
    private final List<Holder<Item>> items;

    public EffectsEntry(GuidebookEntry root, Holder<MobEffect> effect, List<Holder<Item>> items) {
        this(root.getId(), root.getIcon(), root.getName(), root.getSlotName(), root.getSlotSubtitle(), root.getDescriptionKey(), effect, items);
    }

    public EffectsEntry(Identifier id, Identifier icon, String name, String slotName, Optional<String> slotSubtitle, String descriptionKey, Holder<MobEffect> effect, List<Holder<Item>> items) {
        super(id, icon, name, slotName, slotSubtitle, descriptionKey);
        this.effect = this.info(EFFECT, effect);
        this.items = items;
        for (int i = 0; i < this.items.size(); i++) {
            this.info(ITEM.id() + "_" + i, items.get(i));
        }
    }

    public Holder<MobEffect> getEffect() {
        return this.effect;
    }

    public List<Holder<Item>> getItems() {
        return this.items;
    }

    @Override
    public String toString() {
        return "EffectsEntry{" +
                "id=" + this.getId() +
                ", icon=" + this.getIcon() +
                ", name=" + this.getName() +
                ", slotName=" + this.getSlotName() +
                ", slotSubtitle=" + this.getSlotSubtitle() +
                ", descriptionKey=" + this.getDescriptionKey() +
                ", effect=" + this.effect +
                ", items=" + this.items +
                '}';
    }

    public static class Mutable extends EffectsEntry implements MutableEntry {
        public static final Codec<EffectsEntry.Mutable> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EffectsEntry.REFERENCE_CODEC.fieldOf("entry").forGetter(EffectsEntry.Mutable::getEntry),
                Codec.unboundedMap(Codec.STRING, Info.CODEC).fieldOf("values").forGetter(EffectsEntry.Mutable::getClientValues)
        ).apply(instance, EffectsEntry.Mutable::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, EffectsEntry.Mutable> STREAM_CODEC = StreamCodec.composite(
                EffectsEntry.STREAM_CODEC, EffectsEntry.Mutable::getEntry,
                ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, Info.STREAM_CODEC), EffectsEntry.Mutable::getClientValues,
                EffectsEntry.Mutable::new);

        private final Holder<EffectsEntry> entry;
        private final Map<String, Info> clientValues;

        public Mutable(Holder<EffectsEntry> entry) {
            this(entry, entry.value().getValues());
        }

        public Mutable(Holder<EffectsEntry> entry, Map<String, Info> clientValues) {
            super(entry.value().getId(), entry.value().getIcon(), entry.value().getName(), entry.value().getSlotName(), entry.value().getSlotSubtitle(), entry.value().getDescriptionKey(), entry.value().getEffect(), entry.value().getItems());
            this.entry = entry;
            this.clientValues = clientValues;
        }

        public Holder<EffectsEntry> getEntry() {
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
