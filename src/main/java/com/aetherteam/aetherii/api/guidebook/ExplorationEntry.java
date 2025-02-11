package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIExplorationEntries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class ExplorationEntry extends GuidebookEntry {
    public static final Codec<ExplorationEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    Codec.STRING.fieldOf("placeholder").forGetter(ExplorationEntry::placeholder)
            ).apply(in, ExplorationEntry::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ExplorationEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIExplorationEntries.EXPLORATION_ENTRY_REGISTRY_KEY);
    private final String placeholder;

    public ExplorationEntry(String placeholder) {
        super(null, null, null, null, null); //todo
        this.placeholder = placeholder;
    }

    public String placeholder() {
        return placeholder;
    }

    public static class Mutable extends ExplorationEntry { //todo
        public Mutable(String placeholder) {
            super(placeholder);
        }
    }
}
