package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.KelpBlock;

public class BloomingArilumBlock extends KelpBlock {
    public BloomingArilumBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected Block getBodyBlock() {
        return AetherIIBlocks.BLOOMING_ARILUM_PLANT.get();
    }
}
