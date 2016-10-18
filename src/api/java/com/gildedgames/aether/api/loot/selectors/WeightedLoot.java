package com.gildedgames.aether.api.loot.selectors;

import com.gildedgames.aether.api.loot.Loot;
import net.minecraft.item.ItemStack;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedLoot implements Loot
{
	private final NavigableMap<Integer, ItemStack> items = new TreeMap<>();

	private int totalWeight = 0;

	public WeightedLoot addStack(int weight, ItemStack stack)
	{
		this.totalWeight += weight;
		this.items.put(weight, stack);

		return this;
	}

	@Override
	public ItemStack create(Random random)
	{
		return this.items.ceilingEntry(random.nextInt(this.totalWeight)).getValue();
	}

	@Override
	public ItemStack getCloningSource()
	{
		return null;
	}

}
