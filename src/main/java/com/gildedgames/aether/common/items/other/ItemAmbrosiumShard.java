package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.common.items.ItemDropOnDeath;
import net.minecraft.item.ItemStack;

public class ItemAmbrosiumShard extends ItemDropOnDeath
{
	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		return 1600;
	}
}
