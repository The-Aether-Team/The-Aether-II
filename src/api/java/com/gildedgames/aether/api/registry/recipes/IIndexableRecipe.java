package com.gildedgames.aether.api.registry.recipes;

import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;

/**
 * Implemented to provide an abstract way of looking into other recipes from
 * other locations. This can be implemented as either a wrapper or directly by
 * your recipe class.
 */
public interface IIndexableRecipe
{
	/**
	 * Returns a {@link List} of objects in crafting matrix order, where elements can either be
	 * an {@link ItemStack} or a {@link Collection<ItemStack>}.
	 * @return The recipe's matrix.
	 */
	List<Object> getCraftingMatrix();

	/**
	 * Returns the collection of unique items this recipe requires to be crafted.
	 * @return A non-empty collection of unique items this recipe requires as input
	 */
	Collection<ItemMetaPair> getRecipeItems();

	/**
	 * @return The width of the crafting matrix returned by {@link IIndexableRecipe#getCraftingMatrix()}
	 */
	int getRecipeWidth();

	/**
	 * @return The height of the crafting matrix returned by {@link IIndexableRecipe#getCraftingMatrix()}
	 */
	int getRecipeHeight();

	/**
	 * Returns the output {@link ItemStack} this recipe will return when it is crafted.
	 * @return An {@link ItemStack} representing this recipe's output
	 */
	ItemStack getCraftingResult();
}
