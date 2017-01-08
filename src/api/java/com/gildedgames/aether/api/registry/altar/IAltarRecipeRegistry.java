package com.gildedgames.aether.api.registry.altar;

public interface IAltarRecipeRegistry
{
	/**
	 * Registers an {@link IAltarRecipe} with the registry.
	 * @param recipe The recipe to register
	 */
	void registerAltarRecipe(IAltarRecipe recipe);
}
