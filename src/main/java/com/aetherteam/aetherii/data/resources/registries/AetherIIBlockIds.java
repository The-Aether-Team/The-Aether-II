package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class AetherIIBlockIds {
    public static final ResourceKey<Block> AETHER_DIRT = createKey("aether_dirt");

    private static ResourceKey<Block> createKey(String name) {
        return ResourceKey.create(Registries.BLOCK, new ResourceLocation(AetherII.MODID, name));
    }
}
