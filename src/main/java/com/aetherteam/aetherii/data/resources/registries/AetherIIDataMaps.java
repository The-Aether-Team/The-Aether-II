package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.maps.AmberHourglassFuel;
import com.aetherteam.aetherii.data.resources.maps.BlockInfection;
import com.aetherteam.aetherii.data.resources.maps.BucketReplacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class AetherIIDataMaps {
    public static final DataMapType<Item, AmberHourglassFuel> AMBER_HOURGLASS_FUELS = DataMapType
            .builder(Identifier.fromNamespaceAndPath(AetherII.MODID, "amber_hourglass_fuels"), Registries.ITEM, AmberHourglassFuel.CODEC)
            .synced(AmberHourglassFuel.POWER_TIME_CODEC, false)
            .build();

    public static final DataMapType<Item, BucketReplacement> BUCKET_REPLACEMENT = DataMapType
            .builder(Identifier.fromNamespaceAndPath(AetherII.MODID, "bucket_replacement"), Registries.ITEM, BucketReplacement.CODEC)
            .synced(BucketReplacement.ITEM_CODEC, false)
            .build();

    public static final DataMapType<Block, BlockInfection> INFECTED_BLOCKS = DataMapType
            .builder(Identifier.fromNamespaceAndPath(AetherII.MODID, "infected_blocks"), Registries.BLOCK, BlockInfection.CODEC)
            .synced(BlockInfection.BLOCK_CODEC, false)
            .build();

    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(AMBER_HOURGLASS_FUELS);
        event.register(BUCKET_REPLACEMENT);
        event.register(INFECTED_BLOCKS);
    }
}