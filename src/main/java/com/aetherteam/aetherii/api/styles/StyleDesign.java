package com.aetherteam.aetherii.api.styles;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.util.ComponentSerialization;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record StyleDesign(ResourceLocation assetId, Component description) {
    public static final Codec<StyleDesign> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(StyleDesign::assetId),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(StyleDesign::description)
    ).apply(instance, StyleDesign::new));
    public static final StreamCodec<FriendlyByteBuf, StyleDesign> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.RESOURCE_LOCATION, StyleDesign::assetId,
            ComponentSerialization.STREAM_CODEC, StyleDesign::description,
            StyleDesign::new);
    public static final Codec<Holder<StyleDesign>> CODEC = RegistryFileCodec.create(AetherIIRegistries.STYLE_DESIGN, DIRECT_CODEC);
    public static final StreamCodec<FriendlyByteBuf, Holder<StyleDesign>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.STYLE_DESIGN, DIRECT_STREAM_CODEC);
}
