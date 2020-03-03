package com.gildedgames.aether.common.items.irradiated;

import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.item.ItemStack;

public class ItemIrradiatedDust extends ItemIrradiatedVisuals implements IDropOnDeath
{
	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		return 9600;
	}
}
