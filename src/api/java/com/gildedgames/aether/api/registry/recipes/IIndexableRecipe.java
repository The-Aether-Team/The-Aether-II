package com.gildedgames.aether.api.registry.recipes;

import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;

/**
 * Implemented to provide an abstract way of looking into other recipes from
 * the recipe searcher. This can be implemented as either a wrapper or directly on
 * your recipe class.
 */
public interface IIndexableRecipe
{
	/**
	 * Returns the objects of this recipe's matrix.
	 * @return The recipe's matrix.
	 */
	List<Object> getCraftingMatrix();

	/**
	 * Returns the collection of unique items this recipe requires.
	 * @return A collection of unique items
	 */
	Collection<ItemMetaPair> getRecipeItems();

	/**
	 * @return The crafting matrix width
	 */
	int getRecipeWidth();

	/**
	 * @return The crafting matrix height
	 */
	int getRecipeHeight();

	/**
	 * Returns the resulting {@link ItemStack} from this recipe.
	 * @return An {@link ItemStack} representing this recipe's result
	 */
	ItemStack getResult();
}
