package com.gildedgames.aether.registry.altar;

import net.minecraft.item.ItemStack;

public interface IAltarRecipeRegistry
{
	void registerAltarEnchantment(ItemStack input, ItemStack output, int cost);

	void registerAltarRecipe(IAltarRecipe recipe);
}
