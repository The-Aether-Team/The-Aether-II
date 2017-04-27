package com.gildedgames.aether.api.registry.recipes;

import net.minecraft.item.ItemStack;

import java.util.Collection;

/**
 * Index manager for recipes. Adding items to this registry will create indexes for
 * it's input items, allowing you to search for recipes that use a specific item.
 */
public interface IRecipeIndexRegistry
{
	/**
	 * Registers a recipe with this index.
	 *
	 * @param recipe The recipe to register
	 */
	void registerRecipe(IIndexableRecipe recipe);

	/**
	 * Returns the number of indexes (keys) in this index. Not very useful other than for
	 * statistical reporting.
	 * @return The number of indexes this registry has currently allocated
	 */
	int getIndexSize();

	/**
	 * Gets a collection of recipes that use {@param item} as an input. This is damage
	 * value sensitive.
	 *
	 * @param item The item index to search by
	 * @return A {@link Collection} containing {@link IIndexableRecipe} which
	 * contain {@param item}, empty if none.
	 */
	Collection<IIndexableRecipe> getRecipesContainingItem(ItemStack item);
}
