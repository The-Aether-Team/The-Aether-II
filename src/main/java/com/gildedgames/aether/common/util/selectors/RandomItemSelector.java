package com.gildedgames.aether.common.util.selectors;

import com.gildedgames.aether.api.loot.Loot;
import com.gildedgames.aether.common.util.Constraint;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomItemSelector implements Loot
{

	private ArrayList<ItemStack> validStackCache;

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

			for (final Item item  : GameData.getItemRegistry().typeSafeIterable())//TODO: Make sure this gets all items
			{
				if (item == null)
				{
					continue;
				}

				List<ItemStack> subItems = new ArrayList<>();

				item.getSubItems(item, item.getCreativeTab(), subItems);

				for (final ItemStack stack : subItems)
				{
					if (this.constraint.accept(stack))
					{
						this.validStackCache.add(stack);
					}
				}
			}
		}

		ItemStack stack = this.validStackCache.get(random.nextInt(this.validStackCache.size())).copy();

		stack.stackSize = 1;

		return stack;
	}

	@Override
	public ItemStack getCloningSource()
	{
		return null;
	}

}
