package com.gildedgames.aether.api.shop;

import com.gildedgames.aether.api.AetherAPI;

import java.util.List;

public class ShopUtil
{
	public static int getFilteredPrice(int price, List<IShopFilter> filters)
	{
		int filteredPrice = price;

		for (IShopFilter filter : filters)
		{
			filteredPrice = filter.getFilteredPrice(filteredPrice);
		}

		return filteredPrice;
	}

	public static int getFilteredPrice(int price)
	{
		return getFilteredPrice(price, AetherAPI.content().shop().getGlobalFilters());
	}

	public static double getFilteredPrice(double price, List<IShopFilter> filters)
	{
		double filteredPrice = price;

		for (IShopFilter filter : filters)
		{
			filteredPrice = filter.getFilteredPrice(filteredPrice);
		}

		return filteredPrice;
	}

	public static int getFilteredPrice(IShopBuy buy, List<IShopFilter> filters)
	{
		return getFilteredPrice(buy.getPrice(), filters);
	}

	public static int getFilteredPrice(IShopBuy buy)
	{
		return getFilteredPrice(buy, AetherAPI.content().shop().getGlobalFilters());
	}

	public static double getFilteredPrice(double price)
	{
		return getFilteredPrice(price, AetherAPI.content().shop().getGlobalFilters());
	}
}
