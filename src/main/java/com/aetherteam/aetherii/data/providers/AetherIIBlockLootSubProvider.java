package com.aetherteam.aetherii.data.providers;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;

import java.util.Set;

public abstract class AetherIIBlockLootSubProvider extends BlockLootSubProvider {
    public AetherIIBlockLootSubProvider(Set<Item> items, FeatureFlagSet flags) {
        super(items, flags);
    }
}
