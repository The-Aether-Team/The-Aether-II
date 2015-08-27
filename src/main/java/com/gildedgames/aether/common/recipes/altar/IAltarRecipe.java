package com.gildedgames.aether.common.recipes.altar;

import net.minecraft.item.ItemStack;

public interface IAltarRecipe
{
	boolean matchesItem(ItemStack stack);

	int getAmbrosiumCost(ItemStack stack);

	ItemStack getOutput(ItemStack stack);
}
