package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.recipes.IIndexableRecipe;
import com.gildedgames.aether.api.util.ItemMetaPair;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//TODO COMPLETELY WRONG, NOW USES INGREDIENT OBJECTS
public class ShapelessRecipeWrapper implements IIndexableRecipe
{
	private final ShapelessOreRecipe recipe;

	private final HashSet<ItemMetaPair> unique = new HashSet<>();

	public ShapelessRecipeWrapper(final ShapelessOreRecipe recipe)
	{
		this.recipe = recipe;

		for (final Object obj : recipe.getIngredients())
		{
			if (obj instanceof ItemStack)
			{
				this.unique.add(new ItemMetaPair((ItemStack) obj));
			}
			else if (obj instanceof NonNullList)
			{
				final Collection<ItemStack> collection = (Collection<ItemStack>) obj;
				collection.stream().map(ItemMetaPair::new).forEach(this.unique::add);
			}
		}
	}

	@Override
	public List<Object> getCraftingMatrix()
	{
		return Collections.unmodifiableList(this.recipe.getIngredients());
	}

	@Override
	public Collection<ItemMetaPair> getRecipeItems()
	{
		return this.unique;
	}

	@Override
	public int getRecipeWidth()
	{
		return this.recipe.getIngredients().size() % 3;
	}

	@Override
	public int getRecipeHeight()
	{
		return this.recipe.getIngredients().size() / 3;
	}

	@Override
	public ItemStack getCraftingResult()
	{
		return this.recipe.getRecipeOutput();
	}
}
