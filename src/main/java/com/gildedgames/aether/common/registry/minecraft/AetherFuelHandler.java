package com.gildedgames.aether.common.registry.minecraft;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

import java.util.ArrayList;

public class AetherFuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		ArrayList<Item> fuelTools = new ArrayList<Item>();
		fuelTools.add(ItemsAether.skyroot_axe);
		fuelTools.add(ItemsAether.skyroot_pickaxe);
		fuelTools.add(ItemsAether.skyroot_shovel);
		fuelTools.add(ItemsAether.skyroot_crossbow);
		fuelTools.add(ItemsAether.skyroot_shield);
		fuelTools.add(ItemsAether.skyroot_stick);

		Item item = fuel.getItem();

		if (item == ItemsAether.ambrosium_shard)
		{
			return 2000;
		}
		else if (item == ItemsAether.skyroot_sword)
		{
			return 200;
		}
		else if (fuelTools.contains(item))
		{
			return 100;
		}

		return 0;
	}
}
