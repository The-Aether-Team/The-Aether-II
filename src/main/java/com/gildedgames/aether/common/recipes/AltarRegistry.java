package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.common.recipes.altar.IAltarRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class AltarRegistry
{
	private ArrayList<IAltarRecipe> altarRecipes = new ArrayList<IAltarRecipe>();

	public void addRecipe(IAltarRecipe recipe)
	{
		this.altarRecipes.add(recipe);
	}

	public ArrayList<IAltarRecipe> getAltarRecipes()
	{
		return altarRecipes;
	}

	public IAltarRecipe getMatchingRecipe(ItemStack stack, int ambrosiumCount)
	{
		for (IAltarRecipe recipe : this.getAltarRecipes())
		{
			if (recipe.matchesRecipe(ambrosiumCount, stack))
			{
				return recipe;
			}
		}

		return null;
	}

	public boolean isEnchantableInAltar(ItemStack stack)
	{
		for (IAltarRecipe recipe : this.getAltarRecipes())
		{
			if (recipe.matchesItem(stack))
			{
				return true;
			}
		}

		return false;
	}
}
