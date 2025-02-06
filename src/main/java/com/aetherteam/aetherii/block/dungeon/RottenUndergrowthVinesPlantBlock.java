package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;

public class RottenUndergrowthVinesPlantBlock extends UndergrowthVinesPlantBlock { //TODO merge with regular Undergrowth Vines Plant Class

    public RottenUndergrowthVinesPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) AetherIIBlocks.ROTTEN_UNDERGROWTH_VINES.get();
    }
}