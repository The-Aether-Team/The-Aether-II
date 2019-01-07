package com.gildedgames.aether.api.shop;

import com.gildedgames.aether.api.AetherAPI;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ShopUtil
{
	public static int getFilteredPrice(IShopInstance instance, ItemStack stack, int price, List<IShopFilter> filters)
	{
		int filteredPrice = price;

		for (IShopFilter filter : filters)
		{
			filteredPrice = filter.getFilteredPrice(instance, stack, filteredPrice);
		}

		return filteredPrice;
	}

	public static int getFilteredPrice(IShopInstance instance, ItemStack stack, int price)
	{
		return getFilteredPrice(instance, stack, price, AetherAPI.content().shop().getGlobalFilters());
	}

	public static double getFilteredPrice(IShopInstance instance, ItemStack stack, double price, List<IShopFilter> filters)
	{
		double filteredPrice = price;

		for (IShopFilter filter : filters)
		{
			filteredPrice = filter.getFilteredPrice(instance, stack, filteredPrice);
		}

		return filteredPrice;
	}

	public static int getFilteredPrice(IShopInstance instance, IShopBuy buy, List<IShopFilter> filters)
	{
		return getFilteredPrice(instance, buy.getItemStack(), buy.getPrice(), filters);
	}

	public static int getFilteredPrice(IShopInstance instance, IShopBuy buy)
	{
		return getFilteredPrice(instance, buy, AetherAPI.content().shop().getGlobalFilters());
	}

	public static double getFilteredPrice(IShopInstance instance, ItemStack stack, double price)
	{
		return getFilteredPrice(instance, stack, price, AetherAPI.content().shop().getGlobalFilters());
	}
}
