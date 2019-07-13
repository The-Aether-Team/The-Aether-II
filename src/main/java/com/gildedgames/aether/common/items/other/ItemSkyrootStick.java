package com.gildedgames.aether.common.items.other;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSkyrootStick extends Item
{
	public ItemSkyrootStick(Properties properties)
	{
		super(properties);
	}

	@Override
	public int getBurnTime(ItemStack itemStack)
	{
		return 100;
	}
}
