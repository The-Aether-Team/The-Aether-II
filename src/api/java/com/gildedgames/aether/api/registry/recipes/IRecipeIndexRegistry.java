package com.gildedgames.aether.api.registry.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collection;

/**
 * Index manager for recipes.
 */
public interface IRecipeIndexRegistry
{
	/**
	 * Registers a recipe with this index.
	 *
	 * @param recipe The recipe to register
	 */
	void addIndex(IIndexableRecipe recipe);

	/**
	 * Cleans the index.
	 */
	void dropAllIndexes();

	/**
	 * Returns the size of this index.
	 * @return The number of indexes
	 */
	int getIndexSize();

	/**
	 * Gets a collection of recipes containing {@param item}.
	 *
	 * @param item The item to search for recipes containing it
	 * @return A non-null {@link Collection} of {@link IIndexableRecipe}
	 */
	Collection<IIndexableRecipe> getRecipesContainingItem(ItemStack item);
}
