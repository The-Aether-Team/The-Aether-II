package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.item.ItemFood;

public class ItemAetherFood extends ItemFood
{
	public ItemAetherFood(int amount, boolean isWolfFood)
	{
		super(amount, isWolfFood);

		this.setCreativeTab(CreativeTabsAether.CONSUMABLES);
	}
}
