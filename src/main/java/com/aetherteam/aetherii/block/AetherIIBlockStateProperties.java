package com.aetherteam.aetherii.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class AetherIIBlockStateProperties {
    public static final BooleanProperty MOSSY = BooleanProperty.create("mossy");
    public static final IntegerProperty PEBBLE_AMOUNT = IntegerProperty.create("pebble_amount", 1, 3);
}