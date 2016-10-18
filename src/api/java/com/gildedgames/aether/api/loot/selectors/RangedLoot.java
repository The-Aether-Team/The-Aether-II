package com.gildedgames.aether.api.loot.selectors;

import com.gildedgames.aether.api.loot.Loot;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class RangedLoot implements Loot
{

	private ItemStack stack;

	private int min, max;

	public RangedLoot(ItemStack stack, int min, int max)
	{
		this.stack = stack;
		this.min = Math.max(1, min);
		this.max = max;
	}

	@Override
	public ItemStack create(Random random)
	{
		int rangedResult = this.min + random.nextInt(this.max - this.min);

		ItemStack copy = this.stack.copy();

		copy.stackSize = rangedResult;

		return copy;
	}

	@Override
	public ItemStack getCloningSource()
	{
		return this.stack;
	}

}
