package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.recipes.IIndexableRecipe;
import com.gildedgames.aether.api.util.ItemMetaPair;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RecipeWrapper implements IIndexableRecipe
{
	private final IRecipe recipe;

	private final Set<ItemMetaPair> unique;

	public RecipeWrapper(final IRecipe recipe)
	{
		Validate.notNull(recipe.getRecipeOutput(), "Recipe is missing output stack");
		Validate.notEmpty(recipe.getIngredients(), "Recipe is missing inputs");

		this.recipe = recipe;
		this.unique = new HashSet<>();

		for (final Ingredient ingredient : this.recipe.getIngredients())
		{
			for (ItemStack stack : ingredient.getMatchingStacks())
			{
				this.unique.add(new ItemMetaPair(stack));
			}
		}
	}

	@Override
	public NonNullList<Ingredient> getCraftingMatrix()
	{
		return this.recipe.getIngredients();
	}

	@Override
	public Collection<ItemMetaPair> getRecipeItems()
	{
		return this.unique;
	}

	@Override
	public ItemStack getCraftingResult()
	{
		return this.recipe.getRecipeOutput();
	}
}
