package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.data.generators.loot.AetherIIBlockLoot;
import com.aetherteam.aetherii.data.generators.loot.AetherIIChestLoot;
import com.aetherteam.aetherii.data.generators.loot.AetherIIEntityLoot;
import com.aetherteam.aetherii.data.generators.loot.AetherIIStrippingLoot;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.aetherteam.aetherii.loot.AetherIILootContexts;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AetherIILootTableData {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new LootTableProvider(output, AetherIILoot.IMMUTABLE_LOOT_TABLES, List.of(
                new LootTableProvider.SubProviderEntry(AetherIIBlockLoot::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(AetherIIEntityLoot::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(AetherIIChestLoot::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(AetherIIStrippingLoot::new, AetherIILootContexts.STRIPPING)
        ), registries);
    }
}