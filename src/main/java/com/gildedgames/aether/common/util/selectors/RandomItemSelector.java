package com.gildedgames.aether.common.util.selectors;

import com.gildedgames.aether.api.loot.Loot;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.Constraint;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomItemSelector implements Loot
{

	private ArrayList<Item> validStackCache;

	private Constraint constraint;

	public RandomItemSelector(Constraint constraint)
	{
		this.constraint = constraint;
	}

	@Override
	public ItemStack create(Random random)
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

				if (this.constraint.accept(item))
				{
					this.validStackCache.add(item);
				}
			}
		}

		Item item = this.validStackCache.get(random.nextInt(this.validStackCache.size()));

		return new ItemStack(item);
	}

	@Override
	public ItemStack getCloningSource()
	{
		return null;
	}

}
