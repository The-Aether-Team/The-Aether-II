package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIRewardWrappers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record RewardWrapper(ResourceLocation advancement, ResourceLocation entryId, List<String> entryValues) {
    public static final Codec<RewardWrapper> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("advancement").forGetter(RewardWrapper::advancement),
            ResourceLocation.CODEC.fieldOf("entry").forGetter(RewardWrapper::entryId),
            Codec.list(Codec.STRING).fieldOf("entry_values").forGetter(RewardWrapper::entryValues)
    ).apply(instance, RewardWrapper::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, RewardWrapper> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, RewardWrapper::advancement,
            ResourceLocation.STREAM_CODEC, RewardWrapper::entryId,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), RewardWrapper::entryValues,
            RewardWrapper::new);
    public static final Codec<Holder<RewardWrapper>> CODEC = RegistryFileCodec.create(AetherIIRewardWrappers.REWARD_WRAPPER_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<RewardWrapper>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRewardWrappers.REWARD_WRAPPER_REGISTRY_KEY, DIRECT_STREAM_CODEC);
}
