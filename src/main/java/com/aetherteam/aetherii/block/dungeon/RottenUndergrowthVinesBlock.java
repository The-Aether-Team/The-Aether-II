package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.world.level.block.Block;

public class RottenUndergrowthVinesBlock extends UndergrowthVinesBlock { //TODO merge with regular Undergrowth Vines Class

    public RottenUndergrowthVinesBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected Block getBodyBlock() {
        return AetherIIBlocks.ROTTEN_UNDERGROWTH_VINES_PLANT.get();
    }
}