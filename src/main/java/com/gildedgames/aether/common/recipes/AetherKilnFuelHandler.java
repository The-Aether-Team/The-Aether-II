package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class AetherKilnFuelHandler implements IFuelHandler
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
