package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.recipes.altar.IAltarRecipe;
import com.gildedgames.aether.api.recipes.altar.IAltarRecipeRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class AltarRecipeRegistry implements IAltarRecipeRegistry
{
	private ArrayList<IAltarRecipe> altarRecipes = new ArrayList<>();

	@Override
	public void registerAltarRecipe(IAltarRecipe recipe)
	{
		this.altarRecipes.add(recipe);
	}

	@Override
	public IAltarRecipe getMatchingRecipeFromInput(ItemStack stack)
	{
		for (IAltarRecipe recipe : this.altarRecipes)
		{
			if (recipe.matchesInput(stack))
			{
				return recipe;
			}
		}

		return null;
	}

	@Override
	public IAltarRecipe getMatchingRecipeFromOutput(ItemStack stack)
	{
		for (IAltarRecipe recipe : this.altarRecipes)
		{
			if (recipe.matchesOutput(stack))
			{
				return recipe;
			}
		}

		return null;
	}
}
