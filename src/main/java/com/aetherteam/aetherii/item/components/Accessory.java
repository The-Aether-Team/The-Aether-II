package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

//todo: when the EquipmentSlot extensibility rewrite eventually comes, this class can likely be substituted by vanilla's Equippable component (and a lot of other code can be substituted as well)
public record Accessory(ResourceKey<EquipmentAsset> assetId) {
    public static final Codec<Accessory> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceKey.codec(EquipmentAssets.ROOT_ID).fieldOf("asset_id").forGetter(Accessory::assetId)
    ).apply(instance, Accessory::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Accessory> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(EquipmentAssets.ROOT_ID), Accessory::assetId,
            Accessory::new);
}
