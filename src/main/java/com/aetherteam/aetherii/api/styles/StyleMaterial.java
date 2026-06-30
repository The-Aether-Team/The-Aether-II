package com.aetherteam.aetherii.api.styles;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.util.ComponentSerialization;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public record StyleMaterial(ResourceLocation assetId, Holder<Item> ingredient, Component description) {
    public static final Codec<StyleMaterial> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(StyleMaterial::assetId),
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("ingredient").forGetter(StyleMaterial::ingredient),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(StyleMaterial::description)
    ).apply(instance, StyleMaterial::new));
    public static final StreamCodec<FriendlyByteBuf, StyleMaterial> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.RESOURCE_LOCATION, StyleMaterial::assetId,
            ByteBufCodecs.holderRegistry(Registries.ITEM), StyleMaterial::ingredient,
            ComponentSerialization.STREAM_CODEC, StyleMaterial::description,
            StyleMaterial::new);
    public static final Codec<Holder<StyleMaterial>> CODEC = RegistryFileCodec.create(AetherIIRegistries.STYLE_MATERIAL, DIRECT_CODEC);
    public static final StreamCodec<FriendlyByteBuf, Holder<StyleMaterial>> STREAM_CODEC = ByteBufCodecs.holder(AetherIIRegistries.STYLE_MATERIAL, DIRECT_STREAM_CODEC);
}
