package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.recipes.IIndexableRecipe;
import com.gildedgames.aether.api.util.ItemMetaPair;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.commons.lang3.Validate;

import java.util.*;

public class ShapedRecipeWrapper implements IIndexableRecipe
{
	private final ShapedOreRecipe recipe;

	private final List<Object> matrix;

	private final Set<ItemMetaPair> unique;

	public ShapedRecipeWrapper(final ShapedOreRecipe recipe)
	{
		Validate.notNull(recipe.getRecipeOutput(), "Recipe is missing output stack");
		Validate.notEmpty(recipe.getIngredients(), "Recipe is missing inputs");

		this.recipe = recipe;

		this.matrix = Arrays.asList(recipe.getIngredients());
		this.unique = new HashSet<>();

		for (final Object matrixItem : this.matrix)
		{
			if (matrixItem instanceof ItemStack)
			{
				final ItemStack stack = (ItemStack) matrixItem;

				if (!stack.isEmpty())
				{
					this.unique.add(new ItemMetaPair(stack));
				}
			}
			else if (matrixItem instanceof NonNullList)
			{
				final NonNullList list = (NonNullList) matrixItem;

				for (final Object obj : list)
				{
					if (obj instanceof ItemStack)
					{
						final ItemStack stack = (ItemStack) obj;

						if (!stack.isEmpty())
						{
							this.unique.add(new ItemMetaPair(stack));
						}
					}
				}
			}
		}
	}

	@Override
	public List<Object> getCraftingMatrix()
	{
		return Collections.unmodifiableList(this.matrix);
	}

	@Override
	public Collection<ItemMetaPair> getRecipeItems()
	{
		return this.unique;
	}

	@Override
	public int getRecipeWidth()
	{
		return this.recipe.getWidth();
	}

	@Override
	public int getRecipeHeight()
	{
		return this.recipe.getHeight();
	}

	@Override
	public ItemStack getCraftingResult()
	{
		return this.recipe.getRecipeOutput();
	}
}
