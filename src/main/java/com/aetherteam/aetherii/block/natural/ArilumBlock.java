package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.KelpBlock;

public class ArilumBlock extends KelpBlock {
    public ArilumBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected Block getBodyBlock() {
        return AetherIIBlocks.ARILUM_PLANT.get();
    }
}
