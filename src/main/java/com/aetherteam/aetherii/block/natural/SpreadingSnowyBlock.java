package com.aetherteam.aetherii.block.natural;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowyDirtBlock;

public class SpreadingSnowyBlock extends SnowyDirtBlock {
    @SuppressWarnings("unused")
    private final ResourceKey<Block> dirtBlock;

    public SpreadingSnowyBlock(Properties properties, ResourceKey<Block> dirtBlock) {
        super(properties);
        this.dirtBlock = dirtBlock;
    }
}
