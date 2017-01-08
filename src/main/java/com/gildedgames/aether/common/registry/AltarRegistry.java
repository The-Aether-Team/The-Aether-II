package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.registry.altar.IAltarRecipe;
import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class AltarRegistry implements IAltarRecipeRegistry
{
	private ArrayList<IAltarRecipe> altarRecipes = new ArrayList<>();

	@Override
	public void registerAltarRecipe(IAltarRecipe recipe)
	{
		this.addRecipe(recipe);
	}

	private void addRecipe(IAltarRecipe recipe)
	{
		this.altarRecipes.add(recipe);
	}

	public IAltarRecipe getMatchingRecipe(ItemStack stack)
	{
		for (IAltarRecipe recipe : this.altarRecipes)
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
}
