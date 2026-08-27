package com.aetherteam.aetherii.api.styles;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;

public record StyleDesign(Identifier assetId, Component description) {
    public static final Codec<StyleDesign> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Identifier.CODEC.fieldOf("asset_id").forGetter(StyleDesign::assetId),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(StyleDesign::description)
    ).apply(instance, StyleDesign::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, StyleDesign> DIRECT_STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, StyleDesign::assetId,
            ComponentSerialization.STREAM_CODEC, StyleDesign::description,
            StyleDesign::new);
    public static final Codec<Holder<StyleDesign>> CODEC = RegistryFileCodec.create(AetherIIRegistries.STYLE_DESIGN, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<StyleDesign>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.STYLE_DESIGN, DIRECT_STREAM_CODEC);
}