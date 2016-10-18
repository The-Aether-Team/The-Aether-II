package com.gildedgames.aether.api.loot.selectors;

import com.gildedgames.aether.api.loot.Loot;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ChanceLoot implements Loot
{

	private ItemStack stack;

	private float chance;

	public ChanceLoot(ItemStack stack, float chance)
	{
		this.stack = stack;
		this.chance = chance;
	}

	@Override
	public ItemStack create(Random random)
	{
		float percentResult = random.nextFloat() * 100;
		float percentRequired = this.chance * 100;

		if (percentResult > percentRequired)
		{
			return null;
		}

		return this.stack.copy();
	}

	@Override
	public ItemStack getCloningSource()
	{
		return this.stack;
	}

}
