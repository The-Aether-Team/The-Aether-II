package com.aetherteam.aetherii.api.styles;

import com.aetherteam.aetherii.data.resources.registries.AetherIIStyleDesigns;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record StyleDesign(ResourceLocation assetId, Component description) {
    public static final Codec<StyleDesign> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(StyleDesign::assetId),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(StyleDesign::description)
    ).apply(instance, StyleDesign::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, StyleDesign> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, StyleDesign::assetId,
            ComponentSerialization.STREAM_CODEC, StyleDesign::description,
            StyleDesign::new);
    public static final Codec<Holder<StyleDesign>> CODEC = RegistryFileCodec.create(AetherIIStyleDesigns.STYLE_DESIGN_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<StyleDesign>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIStyleDesigns.STYLE_DESIGN_REGISTRY_KEY, DIRECT_STREAM_CODEC);
}