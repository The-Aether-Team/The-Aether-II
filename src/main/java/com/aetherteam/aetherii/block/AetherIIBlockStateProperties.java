package com.aetherteam.aetherii.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Locale;

public class AetherIIBlockStateProperties {
    public static final EnumProperty<Mossy> MOSSY = EnumProperty.create("mossy_overlay", Mossy.class);
    public static final IntegerProperty TWIG_AMOUNT = IntegerProperty.create("twig_amount", 1, 2);
    public static final IntegerProperty ROCK_AMOUNT = IntegerProperty.create("rock_amount", 1, 3);
    public static final IntegerProperty PILES = IntegerProperty.create("piles", 1, 16);
    public static final IntegerProperty PURIFIER_LEVEL = IntegerProperty.create("purifier_level", 0, 4);
    public static final BooleanProperty BRETTL_GROWN = BooleanProperty.create("grown");
    public static final BooleanProperty REINFORCED =  BooleanProperty.create("reinforced");
  
    public enum Mossy implements StringRepresentable {
        BRYALINN,
        SHAYELINN,
        AMBRELINN,
        NONE;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}