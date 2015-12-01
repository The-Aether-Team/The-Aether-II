package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.common.recipes.altar.IAltarRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class AltarRegistry
{
	private ArrayList<IAltarRecipe> altarRecipes = new ArrayList<>();

	public void addRecipe(IAltarRecipe recipe)
	{
		this.altarRecipes.add(recipe);
	}

	public IAltarRecipe getMatchingRecipe(ItemStack stack)
	{
		for (IAltarRecipe recipe : this.getAltarRecipes())
		{
			if (recipe.matchesItem(stack))
			{
				return recipe;
			}
		}

		return null;
	}

	public boolean isEnchantableItem(ItemStack stack)
	{
		return this.getMatchingRecipe(stack) != null;
	}

	public ArrayList<IAltarRecipe> getAltarRecipes()
	{
		return altarRecipes;
	}
}
