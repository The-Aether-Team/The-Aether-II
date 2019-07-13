package com.gildedgames.aether.common.items.other;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemAmbrosiumShard extends Item
{
	public ItemAmbrosiumShard(Properties properties)
	{
		super(properties);
	}

	@Override
	public int getBurnTime(ItemStack itemStack)
	{
		return 2000;
	}
}
