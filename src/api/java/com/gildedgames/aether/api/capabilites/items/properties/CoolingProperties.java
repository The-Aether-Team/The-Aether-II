package com.gildedgames.aether.api.capabilites.items.properties;

import net.minecraft.item.ItemStack;

import java.util.Random;

public interface CoolingProperties
{

    int getCoolingStrength(ItemStack stack);

    int getCoolingThreshold(ItemStack stack);

    ItemStack getResultWhenCooled(ItemStack stack, Random rand);

    String getResultName(ItemStack stack);

}
