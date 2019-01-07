package com.gildedgames.aether.api.shop;

import com.gildedgames.aether.api.AetherAPI;

import java.util.List;

public class ShopUtil
{
	public static int getFilteredPrice(IShopInstance instance, int price, List<IShopFilter> filters)
	{
		int filteredPrice = price;

		for (IShopFilter filter : filters)
		{
			filteredPrice = filter.getFilteredPrice(instance, filteredPrice);
		}

		return filteredPrice;
	}

	public static int getFilteredPrice(IShopInstance instance, int price)
	{
		return getFilteredPrice(instance, price, AetherAPI.content().shop().getGlobalFilters());
	}

	public static double getFilteredPrice(IShopInstance instance, double price, List<IShopFilter> filters)
	{
		double filteredPrice = price;

		for (IShopFilter filter : filters)
		{
			filteredPrice = filter.getFilteredPrice(instance, filteredPrice);
		}

		return filteredPrice;
	}

	public static int getFilteredPrice(IShopInstance instance, IShopBuy buy, List<IShopFilter> filters)
	{
		return getFilteredPrice(instance, buy.getPrice(), filters);
	}

	public static int getFilteredPrice(IShopInstance instance, IShopBuy buy)
	{
		return getFilteredPrice(instance, buy, AetherAPI.content().shop().getGlobalFilters());
	}

	public static double getFilteredPrice(IShopInstance instance, double price)
	{
		return getFilteredPrice(instance, price, AetherAPI.content().shop().getGlobalFilters());
	}
}
