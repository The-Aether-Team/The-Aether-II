package com.gildedgames.aether.registry.altar;

import net.minecraft.item.ItemStack;

public interface IAltarRecipe
{
	boolean matchesItem(ItemStack stack);

	int getAmbrosiumCost(ItemStack stack);

	ItemStack getOutput(ItemStack stack);
}
