package com.gildedgames.aether.common.crafting;

import com.gildedgames.aether.api.registry.altar.IAltarRecipe;
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
			if (recipe.matchesRecipe(stack))
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
