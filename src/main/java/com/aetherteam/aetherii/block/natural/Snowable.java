package com.aetherteam.aetherii.block.natural;

import net.minecraft.world.level.block.state.BlockState;

public interface Snowable {
    boolean isSnowy(BlockState blockState); //todo aether grass should check this for determining if it should be a snowy state.
}
