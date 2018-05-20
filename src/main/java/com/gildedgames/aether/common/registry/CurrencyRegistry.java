package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.shop.ICurrencyRegistry;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class CurrencyRegistry implements ICurrencyRegistry
{
	private Map<Integer, Double> registries = Maps.newHashMap();

	@Override
	public void clearRegistrations()
	{
		this.registries.clear();
	}

	@Override
	public void registerValue(ItemStack stack, double value)
	{
		int hash = ItemHelper.getHashForItemStack(stack);

		this.registries.put(hash, value);
	}

	@Override
	public double getValue(ItemStack stack)
	{
		return this.getSingleValue(stack) * stack.getCount();
	}

	@Override
	public double getSingleValue(ItemStack stack)
	{
		Double value = this.registries.get(ItemHelper.getHashForItemStack(stack));

		if (value == null)
		{
			value = this.registries.get(ItemHelper.getHashForItemStack(stack, false));

			return value == null ? 0 : value;
		}

		return value;
	}

	@Override
	public boolean hasValue(ItemStack stack)
	{
		boolean has = this.registries.containsKey(ItemHelper.getHashForItemStack(stack));

		return has || this.registries.containsKey(ItemHelper.getHashForItemStack(stack, false));
	}
}
