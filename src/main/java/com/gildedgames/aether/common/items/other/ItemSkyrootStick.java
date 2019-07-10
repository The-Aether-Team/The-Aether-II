package com.gildedgames.aether.common.items.other;

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
