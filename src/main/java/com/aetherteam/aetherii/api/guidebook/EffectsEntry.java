package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class EffectsEntry extends GuidebookEntry {
    public static final Codec<EffectsEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    Codec.STRING.fieldOf("placeholder").forGetter(EffectsEntry::placeholder)
            ).apply(in, EffectsEntry::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<EffectsEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY);
    private final String placeholder;

    public EffectsEntry(String placeholder) {
        super(null, null, null, null, null); //todo
        this.placeholder = placeholder;
    }

    public String placeholder() {
        return placeholder;
    }

    public static class Mutable extends EffectsEntry { //todo
        public Mutable(String placeholder) {
            super(placeholder);
        }
    }
}
