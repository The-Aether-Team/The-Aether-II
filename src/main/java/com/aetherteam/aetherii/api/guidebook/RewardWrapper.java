package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;

import java.util.List;

public record RewardWrapper(Identifier advancement, Identifier entryId, List<String> entryValues) {
    public static final Codec<RewardWrapper> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Identifier.CODEC.fieldOf("advancement").forGetter(RewardWrapper::advancement),
            Identifier.CODEC.fieldOf("entry").forGetter(RewardWrapper::entryId),
            Codec.list(Codec.STRING).fieldOf("entry_values").forGetter(RewardWrapper::entryValues)
    ).apply(instance, RewardWrapper::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, RewardWrapper> DIRECT_STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, RewardWrapper::advancement,
            Identifier.STREAM_CODEC, RewardWrapper::entryId,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), RewardWrapper::entryValues,
            RewardWrapper::new);
    public static final Codec<Holder<RewardWrapper>> CODEC = RegistryFileCodec.create(AetherIIRegistries.REWARD_WRAPPER, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<RewardWrapper>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.REWARD_WRAPPER, DIRECT_STREAM_CODEC);
}
