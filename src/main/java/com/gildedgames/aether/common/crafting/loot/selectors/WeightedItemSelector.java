package com.gildedgames.aether.common.crafting.loot.selectors;

import com.gildedgames.aether.common.crafting.loot.IItemSelector;
import net.minecraft.item.ItemStack;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedItemSelector implements IItemSelector
{
	private final NavigableMap<Integer, ItemStack> items = new TreeMap<>();

	private int totalWeight = 0;

	public WeightedItemSelector addStack(int weight, ItemStack stack)
	{
		this.totalWeight += weight;
		this.items.put(weight, stack);

		return this;
	}

	@Override
	public ItemStack getRandomItem(Random random)
	{
		return this.items.ceilingEntry(random.nextInt(this.totalWeight)).getValue();
	}
}
