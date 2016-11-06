package com.gildedgames.aether.common.registry.minecraft;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class AetherFuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		Item item = fuel.getItem();

		if (item == ItemsAether.ambrosium_shard)
		{
			return 2000;
		}

		return 0;
	}
}
