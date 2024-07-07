package com.aetherteam.aetherii.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class AetherIIBlockStateProperties {
    public static final IntegerProperty TWIG_AMOUNT = IntegerProperty.create("twig_amount", 1, 2);
    public static final IntegerProperty ROCK_AMOUNT = IntegerProperty.create("rock_amount", 1, 3);
    public static final IntegerProperty PILES = IntegerProperty.create("piles", 1, 16);
    public static final BooleanProperty ALTAR_CHARGING = BooleanProperty.create("charging");
    public static final BooleanProperty ALTAR_BLASTING = BooleanProperty.create("blasting");
    public static final BooleanProperty BRETTL_GROWN = BooleanProperty.create("grown");
}