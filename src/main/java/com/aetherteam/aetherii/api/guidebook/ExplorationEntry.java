package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIExplorationEntries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ExplorationEntry(String placeholder) {
    public static final Codec<ExplorationEntry> DIRECT_CODEC =
            RecordCodecBuilder.create(in -> in.group(
                    Codec.STRING.fieldOf("placeholder").forGetter(ExplorationEntry::placeholder)
            ).apply(in, ExplorationEntry::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ExplorationEntry>> STREAM_CODEC = ByteBufCodecs.holderRegistry(AetherIIExplorationEntries.EXPLORATION_ENTRY_REGISTRY_KEY);
}
