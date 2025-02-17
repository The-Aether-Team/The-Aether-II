package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Optional;

public class EffectsEntry extends GuidebookEntry {
    public static final Codec<EffectsEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    GuidebookEntry.MAP_CODEC.forGetter(GuidebookEntry::root)
            ).apply(in, EffectsEntry::new));
    public static final Codec<Holder<EffectsEntry>> REFERENCE_CODEC = RegistryFileCodec.create(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<EffectsEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY);

    public EffectsEntry(GuidebookEntry root) {
        super(root.getIcon(), root.getName(), root.getSlotName(), root.getSlotSubtitle(), root.getDescriptionKey());
    }

    public EffectsEntry(ResourceLocation icon, Optional<String> name, Optional<String> slotName, Optional<String> slotSubtitle, String descriptionKey) {
        super(icon, name, slotName, slotSubtitle, descriptionKey);
    }

    public static class Mutable extends EffectsEntry {
        public static final Codec<EffectsEntry.Mutable> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EffectsEntry.REFERENCE_CODEC.fieldOf("entry").forGetter(EffectsEntry.Mutable::getEntry),
                Codec.unboundedMap(Codec.STRING, Info.CODEC).fieldOf("values").forGetter(EffectsEntry.Mutable::getClientValues)
        ).apply(instance, EffectsEntry.Mutable::new));

        private final Holder<EffectsEntry> entry;
        private final Map<String, Info> clientValues;

        public Mutable(Holder<EffectsEntry> entry) {
            this(entry, entry.value().getValues());
        }

        public Mutable(Holder<EffectsEntry> entry, Map<String, Info> clientValues) {
            super(entry.value().getIcon(), entry.value().getName(), entry.value().getSlotName(), entry.value().getSlotSubtitle(), entry.value().getDescriptionKey());
            this.entry = entry;
            this.clientValues = clientValues;
        }

        public Holder<EffectsEntry> getEntry() {
            return this.entry;
        }

        public Map<String, Info> getClientValues() {
            return this.clientValues;
        }
    }
}
