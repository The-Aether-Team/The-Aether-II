package com.gildedgames.aether.items.consumables;

import net.minecraft.item.ItemFood;

public class ItemAetherFood extends ItemFood
{

	public ItemAetherFood(int amount, boolean isWolfFood)
	{
		super(amount, isWolfFood);
	}

	public ItemAetherFood(int amount, float saturation, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);
	}

}
