package com.gildedgames.aether.api.registry.altar;

import net.minecraft.item.ItemStack;

public interface IAltarRecipeRegistry
{
	/**
	 * Registers a simple input/output recipe.
	 * @param input The {@link ItemStack} that this recipe requires
	 * @param output The {@link ItemStack} this recipe will return upon being completed
	 * @param cost How much Ambrosium will be required to complete the recipe
	 */
	void registerAltarEnchantment(ItemStack input, ItemStack output, int cost);

	/**
	 * Registers an {@link IAltarRecipe} with the registry.
	 * @param recipe The recipe to register
	 */
	void registerAltarRecipe(IAltarRecipe recipe);
}
