package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.shop.ICurrencyRegistry;
import com.gildedgames.aether.api.shop.IShopCurrency;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class CurrencyRegistry implements ICurrencyRegistry
{
	private final Map<Class<? extends IShopCurrency>, Map<Integer, Double>> registries = Maps.newHashMap();

	@Override
	public void clearRegistrations()
	{
		this.registries.clear();
	}

	private Map<Integer, Double> getHashToValue(Class<? extends IShopCurrency> currency)
	{
		if (!this.registries.containsKey(currency))
		{
			this.registries.put(currency, Maps.newHashMap());
		}

		return this.registries.get(currency);
	}

	@Override
	public void registerValue(ItemStack stack, double value, Class<? extends IShopCurrency> currency)
	{
		int hash = ItemHelper.getKeyForItemStack(stack);

		this.getHashToValue(currency).put(hash, value);
	}

	@Override
	public double getValue(ItemStack stack, Class<? extends IShopCurrency> currency)
	{
		return this.getSingleValue(stack, currency) * stack.getCount();
	}

	@Override
	public double getSingleValue(ItemStack stack, Class<? extends IShopCurrency> currency)
	{
		Map<Integer, Double> hashToValue = this.getHashToValue(currency);

		Double value = hashToValue.get(ItemHelper.getKeyForItemStack(stack));

		if (value == null)
		{
			value = hashToValue.get(ItemHelper.getKeyForItemStack(stack, false));

			return value == null ? 0 : value;
		}

		return value;
	}

	@Override
	public boolean hasValue(ItemStack stack, Class<? extends IShopCurrency> currency)
	{
		boolean has = this.getHashToValue(currency).containsKey(ItemHelper.getKeyForItemStack(stack));

		return has || this.getHashToValue(currency).containsKey(ItemHelper.getKeyForItemStack(stack, false));
	}
}
