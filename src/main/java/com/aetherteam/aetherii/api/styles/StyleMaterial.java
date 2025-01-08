package com.aetherteam.aetherii.api.styles;

import com.aetherteam.aetherii.data.resources.registries.AetherIIStyleMaterials;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record StyleMaterial(ResourceLocation assetId, Holder<Item> ingredient, Component description) {
    public static final Codec<StyleMaterial> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(StyleMaterial::assetId),
            Item.CODEC.fieldOf("ingredient").forGetter(StyleMaterial::ingredient), 
            ComponentSerialization.CODEC.fieldOf("description").forGetter(StyleMaterial::description)
    ).apply(instance, StyleMaterial::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, StyleMaterial> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, StyleMaterial::assetId,
            ByteBufCodecs.holderRegistry(Registries.ITEM), StyleMaterial::ingredient,
            ComponentSerialization.STREAM_CODEC, StyleMaterial::description,
            StyleMaterial::new);
    public static final Codec<Holder<StyleMaterial>> CODEC = RegistryFileCodec.create(AetherIIStyleMaterials.STYLE_MATERIAL_REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<StyleMaterial>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIStyleMaterials.STYLE_MATERIAL_REGISTRY_KEY, DIRECT_STREAM_CODEC);
}