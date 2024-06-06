package com.aetherteam.aetherii.item.food;

import net.minecraft.world.food.FoodProperties;

public class AetherIIFoods {
    public static final FoodProperties BLUEBERRY = new FoodProperties.Builder().fast().nutrition(2).saturationMod(0.3F).build();
    public static final FoodProperties ENCHANTED_BERRY = new FoodProperties.Builder().fast().nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties ORANGE = new FoodProperties.Builder().nutrition(3).saturationMod(0.5F).build();
    public static final FoodProperties WYNDBERRY = new FoodProperties.Builder().fast().nutrition(5).saturationMod(0.4F).build();
    public static final FoodProperties ENCHANTED_WYNDBERRY = new FoodProperties.Builder().fast().nutrition(10).saturationMod(0.6F).build();
    public static final FoodProperties SWET_JELLY = new FoodProperties.Builder().nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties BURRUKAI_LIB = new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build();
    public static final FoodProperties BURRUKAI_LIB_CUT = new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).meat().build();
    public static final FoodProperties KIRRID_CUTLET = new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).meat().build();
    public static final FoodProperties KIRRID_LOIN = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).meat().build();
    public static final FoodProperties RAW_TAEGORE_MEAT = new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).meat().build();
    public static final FoodProperties TAEGORE_STEAK = new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build();
}