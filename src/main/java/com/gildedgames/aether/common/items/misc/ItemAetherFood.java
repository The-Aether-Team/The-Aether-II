package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.items.IDropOnDeath;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemAetherFood extends ItemFood implements IDropOnDeath
{
	private int duration = 32;

	public ItemAetherFood(int amount, boolean isWolfFood)
	{
		super(amount, isWolfFood);

		this.setCreativeTab(CreativeTabsAether.CONSUMABLES);
	}

	public ItemAetherFood(int amount, float saturation, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);

		this.setCreativeTab(CreativeTabsAether.CONSUMABLES);
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
