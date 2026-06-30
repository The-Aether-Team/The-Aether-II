package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExplorationEntry extends GuidebookEntry {
    public static final Codec<ExplorationEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    GuidebookEntry.MAP_CODEC.forGetter(GuidebookEntry::root)
            ).apply(in, ExplorationEntry::new));
    public static final Codec<Holder<ExplorationEntry>> REFERENCE_CODEC = RegistryFileCodec.create(AetherIIRegistries.EXPLORATION_ENTRY, DIRECT_CODEC);
    public static final StreamCodec<FriendlyByteBuf, Holder<ExplorationEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIRegistries.EXPLORATION_ENTRY);

    public ExplorationEntry(GuidebookEntry root) {
        super(root.getId(), root.getIcon(), root.getName(), root.getSlotName(), root.getSlotSubtitle(), root.getDescriptionKey());
    }

    public ExplorationEntry(ResourceLocation id, ResourceLocation icon, String name, String slotName, Optional<String> slotSubtitle, String descriptionKey) {
        super(id, icon, name, slotName, slotSubtitle, descriptionKey);
    }

    public static class Mutable extends ExplorationEntry implements MutableEntry {
        public static final Codec<ExplorationEntry.Mutable> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ExplorationEntry.REFERENCE_CODEC.fieldOf("entry").forGetter(ExplorationEntry.Mutable::getEntry),
                Codec.unboundedMap(Codec.STRING, Info.CODEC).fieldOf("values").forGetter(ExplorationEntry.Mutable::getClientValues)
        ).apply(instance, ExplorationEntry.Mutable::new));
        public static final StreamCodec<FriendlyByteBuf, ExplorationEntry.Mutable> STREAM_CODEC = StreamCodec.composite(
                ExplorationEntry.STREAM_CODEC, ExplorationEntry.Mutable::getEntry,
                ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, Info.STREAM_CODEC), ExplorationEntry.Mutable::getClientValues,
                ExplorationEntry.Mutable::new);

        private final Holder<ExplorationEntry> entry;
        private final Map<String, Info> clientValues;

        public Mutable(Holder<ExplorationEntry> entry) {
            this(entry, entry.value().getValues());
        }

        public Mutable(Holder<ExplorationEntry> entry, Map<String, Info> clientValues) {
            super(entry.value().getId(), entry.value().getIcon(), entry.value().getName(), entry.value().getSlotName(), entry.value().getSlotSubtitle(), entry.value().getDescriptionKey());
            this.entry = entry;
            this.clientValues = clientValues;
        }

        public Holder<ExplorationEntry> getEntry() {
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
