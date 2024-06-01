package com.aetherteam.aetherii.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class AetherIIBlockStateProperties {
    public static final BooleanProperty MOSSY = BooleanProperty.create("mossy");
    public static final IntegerProperty PILES = IntegerProperty.create("piles", 1, 16);
}