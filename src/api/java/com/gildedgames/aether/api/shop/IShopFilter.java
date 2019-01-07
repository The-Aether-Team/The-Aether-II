package com.gildedgames.aether.api.shop;

public interface IShopFilter
{
	double getFilteredPrice(IShopInstance instance, double originalPrice);

	int getFilteredPrice(IShopInstance instance, int originalPrice);
}
