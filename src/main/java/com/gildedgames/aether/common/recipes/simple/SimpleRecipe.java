package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class SimpleRecipe implements ISimpleRecipe
{

	private ItemStack result;

	private Object[] required;

	private SimpleRecipe()
	{

	}

	public SimpleRecipe(ItemStack result, Object... required)
	{
		this.result = result;
		this.required = required;
	}

	@Override
	public Object[] getRequired()
	{
		return Arrays.copyOf(this.required, this.required.length);
	}

	@Override
	public ItemStack getResult()
	{
		return this.result.copy();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof SimpleRecipe)
		{
			SimpleRecipe r = (SimpleRecipe) obj;

			if (RecipeUtil.areEqual(r.getResult(), this.getResult()))
			{
				if (r.getRequired().length != this.getRequired().length)
				{
					return false;
				}

				for (int i = 0; i < this.getRequired().length; i++)
				{
					Object req1 = this.getRequired()[i];

					boolean equal = false;

					for (int j = 0; j < r.getRequired().length; j++)
					{
						Object req2 = r.getRequired()[j];

						if (RecipeUtil.areEqual(req1, req2))
						{
							equal = true;
						}
					}

					if (!equal)
					{
						return false;
					}
				}

				return true;
			}
		}

		return false;
	}

}
