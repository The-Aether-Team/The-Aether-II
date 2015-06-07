package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemAetherFood extends ItemFood
{

	public ItemAetherFood(int amount, boolean isWolfFood)
	{
		this(amount, 0.6f, isWolfFood);
	}

	public ItemAetherFood(int amount, float saturation, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);

		this.setCreativeTab(AetherCreativeTabs.tabConsumables);
	}

}
