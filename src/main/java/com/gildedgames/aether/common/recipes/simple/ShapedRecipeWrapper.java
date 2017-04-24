package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.registry.recipes.IIndexableRecipe;
import com.gildedgames.aether.api.registry.recipes.ItemMetaPair;
import net.minecraft.item.Item;
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

	public ShapedRecipeWrapper(ShapedOreRecipe recipe)
	{
		Validate.notNull(recipe.getRecipeOutput(), "Recipe is missing output stack");
		Validate.notEmpty(recipe.getInput(), "Recipe is missing inputs");

		this.recipe = recipe;

		this.matrix = Arrays.asList(recipe.getInput());
		this.unique = new HashSet<>();

		for (Object matrixItem : this.matrix)
		{
			if (matrixItem instanceof ItemStack)
			{
				ItemStack stack = (ItemStack) matrixItem;

				if (!stack.isEmpty())
				{
					this.unique.add(new ItemMetaPair(stack));
				}
			}
			else if (matrixItem instanceof NonNullList)
			{
				NonNullList list = (NonNullList) matrixItem;

				for (Object obj : list)
				{
					if (obj instanceof ItemStack)
					{
						ItemStack stack = (ItemStack) obj;

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
	public ItemStack getResult()
	{
		return this.recipe.getRecipeOutput();
	}
}
