package com.gildedgames.aether.common.recipes.altar;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IAltarRecipe
{
	boolean matchesRecipe(int ambrosiumCount, ItemStack stack);

	int getAmbrosiumNeeded();

	ItemStack getOutput();
}
