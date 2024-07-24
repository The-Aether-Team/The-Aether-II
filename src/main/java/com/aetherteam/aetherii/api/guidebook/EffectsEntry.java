package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record EffectsEntry(String placeholder) {
    public static final Codec<EffectsEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    Codec.STRING.fieldOf("placeholder").forGetter(EffectsEntry::placeholder)
            ).apply(in, EffectsEntry::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<EffectsEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY);
}
