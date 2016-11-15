package com.gildedgames.aether.common.recipes.simple;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRequirement
{

	private String key;

	private int count;

	public OreDictionaryRequirement(String key, int count)
	{
		this.key = key;
		this.count = count;
	}

	public String getKey()
	{
		return this.key;
	}

	public int getCount()
	{
		return this.count;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ItemStack)
		{
			ItemStack stack = (ItemStack)obj;

			int[] stackIds = OreDictionary.getOreIDs(stack);
			int ore = OreDictionary.getOreID(this.getKey());

			for (int id : stackIds)
			{
				if (ore == id)
				{
					return true;
				}
			}
		}
		else if (obj instanceof OreDictionaryRequirement)
		{
			OreDictionaryRequirement oreReq = (OreDictionaryRequirement)obj;

			return OreDictionary.getOreID(oreReq.getKey()) == OreDictionary.getOreID(this.getKey());
		}
		else if (obj instanceof String)
		{
			String key = (String)obj;

			return OreDictionary.getOreID(key) == OreDictionary.getOreID(this.getKey());
		}

		return false;
	}

}
