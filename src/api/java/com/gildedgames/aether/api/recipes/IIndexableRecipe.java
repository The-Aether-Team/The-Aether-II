package com.gildedgames.aether.api.recipes;

import com.gildedgames.aether.api.util.ItemMetaPair;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

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
	 * Returns a {@link List} of Ingredients in crafting matrix order.
	 * @return The recipe's matrix.
	 */
	NonNullList<Ingredient> getCraftingMatrix();

	/**
	 * Returns the collection of unique items this recipe requires to be crafted.
	 * @return A non-empty collection of unique items this recipe requires as input
	 */
	Collection<ItemMetaPair> getRecipeItems();

	/**
	 * Returns the output {@link ItemStack} this recipe will return when it is crafted.
	 * @return An {@link ItemStack} representing this recipe's output
	 */
	ItemStack getCraftingResult();
}
