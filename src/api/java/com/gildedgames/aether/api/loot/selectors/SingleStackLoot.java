package com.gildedgames.aether.api.loot.selectors;

import com.gildedgames.aether.api.loot.Loot;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class SingleStackLoot implements Loot
{

	private final ItemStack stack;

	public SingleStackLoot(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public ItemStack create(Random random)
	{
		return this.stack.copy();
	}

	@Override
	public ItemStack getCloningSource()
	{
		return this.stack;
	}

}
