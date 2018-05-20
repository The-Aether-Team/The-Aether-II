package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.recipes.IIndexableRecipe;
import com.gildedgames.aether.api.registry.recipes.IRecipeIndexRegistry;
import com.gildedgames.aether.api.util.ItemMetaPair;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.Validate;

import java.util.*;

public class RecipeIndexRegistry implements IRecipeIndexRegistry
{
	private static final Collection<IIndexableRecipe> EMPTY = Collections.emptyList();

	private final Map<ItemMetaPair, Collection<IIndexableRecipe>> cache = new HashMap<>();

	private final Map<Integer, Collection<IIndexableRecipe>> resultCache = new HashMap<>();

	@Override
	public void clearRegistrations()
	{
		this.cache.clear();
		this.resultCache.clear();
	}

	@Override
	public void registerRecipe(IIndexableRecipe recipe)
	{
		Validate.notNull(recipe);

		for (ItemMetaPair item : recipe.getRecipeItems())
		{
			Collection<IIndexableRecipe> group = this.cache.computeIfAbsent(item, k -> new ArrayList<>());
			group.add(recipe);
		}

		Collection<IIndexableRecipe> group = this.resultCache
				.computeIfAbsent(ItemHelper.getHashForItemStack(recipe.getCraftingResult()), k -> new ArrayList<>());
		group.add(recipe);
	}

	@Override
	public int getIndexSize()
	{
		return this.cache.size();
	}

	@Override
	public Collection<IIndexableRecipe> getRecipesContainingIngredient(ItemStack stack)
	{
		ItemMetaPair pair = new ItemMetaPair(stack);

		for (Map.Entry<ItemMetaPair, Collection<IIndexableRecipe>> entry : this.cache.entrySet())
		{
			if (entry.getKey().equals(pair))
			{
				return entry.getValue();
			}
		}

		return EMPTY;
	}

	@Override
	public Collection<IIndexableRecipe> getRecipesContainingResult(ItemStack item)
	{
		int hash = ItemHelper.getHashForItemStack(item);

		for (Map.Entry<Integer, Collection<IIndexableRecipe>> entry : this.resultCache.entrySet())
		{
			if (entry.getKey().equals(hash))
			{
				return entry.getValue();
			}
		}

		return EMPTY;
	}
}
