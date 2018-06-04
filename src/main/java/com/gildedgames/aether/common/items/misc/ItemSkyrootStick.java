package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.items.ItemDropOnDeath;
import net.minecraft.item.ItemStack;

public class ItemSkyrootStick extends ItemDropOnDeath
{
	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		return 100;
	}
}
