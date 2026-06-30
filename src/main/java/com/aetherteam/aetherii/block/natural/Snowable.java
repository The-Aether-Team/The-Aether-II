package com.aetherteam.aetherii.block.natural;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface Snowable {
    boolean isSnowy(BlockState blockState);

    default BlockState setSnowy(BlockState blockState) {
        return blockState.setValue(BlockStateProperties.SNOWY, true);
    }
}
