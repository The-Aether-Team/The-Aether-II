package com.aetherteam.aetherii.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class AetherIIBlockStateProperties {
    public static final BooleanProperty MOSSY = BooleanProperty.create("mossy");
    public static final IntegerProperty TWIG_AMOUNT = IntegerProperty.create("twig_amount", 1, 2);
    public static final IntegerProperty ROCK_AMOUNT = IntegerProperty.create("rock_amount", 1, 3);
}