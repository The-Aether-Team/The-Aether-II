package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.items.ItemDropOnDeath;
import net.minecraft.item.ItemStack;

public class ItemAmbrosiumShard extends ItemDropOnDeath
{
	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		return 2000;
	}
}
