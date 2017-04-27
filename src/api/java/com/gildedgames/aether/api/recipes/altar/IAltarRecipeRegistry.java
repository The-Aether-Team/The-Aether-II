package com.gildedgames.aether.api.recipes.altar;

import net.minecraft.item.ItemStack;

public interface IAltarRecipeRegistry
{
	/**
	 * Registers an {@link IAltarRecipe} with the registry.
	 * @param recipe The recipe to register
	 */
	void registerAltarRecipe(IAltarRecipe recipe);

	/**
	 * Returns the first matching recipe that accepts {@param stack} as input.
	 * @param stack The inputstack
	 * @return The first matching recipe, null if none match.
	 */
	IAltarRecipe getMatchingRecipe(ItemStack stack);
}
