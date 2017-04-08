package com.gildedgames.aether.api.loot.selectors;

import com.gildedgames.aether.api.loot.Loot;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class RangedLoot implements Loot
{

	private final ItemStack stack;

	private final int min;

	private final int max;

	public RangedLoot(ItemStack stack, int min, int max)
	{
		this.stack = stack;
		this.min = Math.max(1, min);
		this.max = max;
	}

	@Override
	public ItemStack create(Random random)
	{
		int amount = this.min + random.nextInt(this.max - this.min);

		return new ItemStack(this.stack.getItem(), amount);
	}

	@Override
	public ItemStack getCloningSource()
	{
		return this.stack;
	}

}
