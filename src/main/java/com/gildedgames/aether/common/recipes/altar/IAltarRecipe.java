package com.gildedgames.aether.common.recipes.altar;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IAltarRecipe
{
	boolean matchesItem(ItemStack stack);

	int getAmbrosiumCost(ItemStack stack);

	ItemStack getOutput(ItemStack stack);
}
