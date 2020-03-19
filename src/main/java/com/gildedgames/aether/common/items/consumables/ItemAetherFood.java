package com.gildedgames.aether.common.items.consumables;

import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemAetherFood extends ItemFood implements IDropOnDeath
{
	private int duration = 32;

	public ItemAetherFood(int amount, boolean isWolfFood)
	{
		super(amount, isWolfFood);
	}

	public ItemAetherFood(int amount, float saturation, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);

		this.setCreativeTab(CreativeTabsAether.TAB_CONSUMABLES);
	}

	public ItemAetherFood setConsumptionDuration(int duration)
	{
		this.duration = duration;

		return this;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return this.duration;
	}
}
