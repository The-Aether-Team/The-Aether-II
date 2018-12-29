package com.gildedgames.aether.common.recipes.simple;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRequirement
{

	private final String key;

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

	public void addCount(int count)
	{
		this.count += count;
	}

	public boolean matches(ItemStack stack)
	{
		int[] stackIds = OreDictionary.getOreIDs(stack);
		int ore = OreDictionary.getOreID(this.getKey());

		for (int id : stackIds)
		{
			if (ore == id)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}

		OreDictionaryRequirement other = (OreDictionaryRequirement) obj;

		return OreDictionary.getOreID(other.getKey()) == OreDictionary.getOreID(this.getKey());
	}
}
