package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.data.generators.loot.*;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.aetherteam.aetherii.loot.AetherIILootContexts;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class AetherIILootTableData {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, AetherIILoot.immutableLootTableLocations(), List.of(
                new LootTableProvider.SubProviderEntry(AetherIIBlockLoot::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(AetherIIEntityLoot::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(AetherIIChestLoot::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(AetherIIShearingLoot::new, AetherIILootContexts.SHEARING),
                new LootTableProvider.SubProviderEntry(AetherIIGiftLoot::new, LootContextParamSets.GIFT),
                new LootTableProvider.SubProviderEntry(AetherIIStrippingLoot::new, AetherIILootContexts.STRIPPING)
        ));
    }
}
