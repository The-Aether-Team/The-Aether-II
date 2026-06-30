package com.aetherteam.aetherii.api.guidebook;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record RewardWrapper(ResourceLocation advancement, ResourceLocation entryId, List<String> entryValues) {
    public static final Codec<RewardWrapper> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("advancement").forGetter(RewardWrapper::advancement),
            ResourceLocation.CODEC.fieldOf("entry").forGetter(RewardWrapper::entryId),
            Codec.list(Codec.STRING).fieldOf("entry_values").forGetter(RewardWrapper::entryValues)
    ).apply(instance, RewardWrapper::new));
    public static final StreamCodec<FriendlyByteBuf, RewardWrapper> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.RESOURCE_LOCATION, RewardWrapper::advancement,
            ByteBufCodecs.RESOURCE_LOCATION, RewardWrapper::entryId,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), RewardWrapper::entryValues,
            RewardWrapper::new);
    public static final Codec<Holder<RewardWrapper>> CODEC = RegistryFileCodec.create(AetherIIRegistries.REWARD_WRAPPER, DIRECT_CODEC);
    public static final StreamCodec<FriendlyByteBuf, Holder<RewardWrapper>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.REWARD_WRAPPER, DIRECT_STREAM_CODEC);
}
