package com.gildedgames.aether.common.util.selectors;

import com.gildedgames.aether.api.items.loot.Loot;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

public class RandomItemSelector implements Loot
{

	private ArrayList<Item> validStackCache;

	private final Predicate<Item> constraint;

	public RandomItemSelector(Predicate<Item> constraint)
	{
		this.constraint = constraint;
	}

	@Override
	public ItemStack select(Random random)
	{
		if (this.validStackCache == null)
		{
			this.validStackCache = new ArrayList<>();

			for (Item item : ItemsAether.getRegisteredItems())
			{
				if (item == null)
				{
					continue;
				}

				if (this.constraint.test(item))
				{
					this.validStackCache.add(item);
				}
			}
		}

		Item item = this.validStackCache.get(random.nextInt(this.validStackCache.size()));

		return new ItemStack(item);
	}

}
