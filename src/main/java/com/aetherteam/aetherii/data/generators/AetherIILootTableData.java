package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.data.generators.loot.AetherIIBlockLoot;
import com.aetherteam.aetherii.data.generators.loot.AetherIIEntityLoot;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class AetherIILootTableData {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, AetherIILoot.IMMUTABLE_LOOT_TABLES, List.of(
                new LootTableProvider.SubProviderEntry(AetherIIEntityLoot::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(AetherIIBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }
}