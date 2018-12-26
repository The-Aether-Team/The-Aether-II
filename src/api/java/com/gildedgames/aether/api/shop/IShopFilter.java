package com.gildedgames.aether.api.shop;

public interface IShopFilter
{
	double getFilteredPrice(double originalPrice);

	int getFilteredPrice(int originalPrice);
}
