package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.registry.recipes.IIndexableRecipe;
import com.gildedgames.aether.api.registry.recipes.ItemMetaPair;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ShapelessRecipeWrapper implements IIndexableRecipe
{
	private final ShapelessOreRecipe recipe;

	private final HashSet<ItemMetaPair> unique = new HashSet<>();

	public ShapelessRecipeWrapper(ShapelessOreRecipe recipe)
	{
		this.recipe = recipe;

		for (Object obj : recipe.getInput())
		{
			if (obj instanceof ItemStack)
			{
				this.unique.add(new ItemMetaPair((ItemStack) obj));
			}
			else if (obj instanceof NonNullList)
			{
				Collection<ItemStack> collection = (Collection<ItemStack>) obj;
				collection.stream().map(ItemMetaPair::new).forEach(this.unique::add);
			}
		}
	}

	@Override
	public List<Object> getCraftingMatrix()
	{
		return Collections.unmodifiableList(this.recipe.getInput());
	}

	@Override
	public Collection<ItemMetaPair> getRecipeItems()
	{
		return this.unique;
	}

	@Override
	public int getRecipeWidth()
	{
		return this.recipe.getRecipeSize() % 3;
	}

	@Override
	public int getRecipeHeight()
	{
		return this.recipe.getRecipeSize() / 3;
	}

	@Override
	public ItemStack getResult()
	{
		return this.recipe.getRecipeOutput();
	}
}
