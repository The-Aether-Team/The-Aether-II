package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.maps.BucketReplacement;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class AetherIIDataMaps {
    public static final DataMapType<Item, BucketReplacement> BUCKET_REPLACEMENT = DataMapType
            .builder(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "bucket_replacement"), Registries.ITEM, BucketReplacement.CODEC)
            .synced(BucketReplacement.ITEM_CODEC, false)
            .build();

    // Don't sync, as we sync this manually with a packet
    public static final DataMapType<Biome, Integer> AETHER_GRASS_COLORS = DataMapType
            .builder(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "aether_grass_color"), Registries.BIOME, Codec.INT)
            .build();

    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(BUCKET_REPLACEMENT);
        event.register(AETHER_GRASS_COLORS);
    }
}