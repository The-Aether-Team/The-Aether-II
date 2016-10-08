package com.gildedgames.aether.api.capabilites.items.properties;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface TemperatureProperties
{

    int getTemperature(ItemStack stack);

    int getTemperatureThreshold(ItemStack stack);

    ItemStack getResultWhenCooled(World world, BlockPos pos, ItemStack stack, Random rand);

    ItemStack getResultWhenHeated(World world, BlockPos pos, ItemStack stack, Random rand);

    boolean shouldDecreaseStackSize(boolean heating);

    String getCooledName(ItemStack stack);

    String getHeatedName(ItemStack stack);

}
