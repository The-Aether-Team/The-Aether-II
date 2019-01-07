package com.gildedgames.aether.common.shop.filters;

import com.gildedgames.aether.api.shop.IShopFilter;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.common.AetherCelebrations;

public class ShopFilterNewYearsEdisonSale implements IShopFilter
{
	public static final float DISCOUNT = 0.5F;

	@Override
	public double getFilteredPrice(IShopInstance instance, double originalPrice)
	{
		if (AetherCelebrations.isEdisonNewYearsSale(instance))
		{
			return originalPrice * DISCOUNT;
		}

		return originalPrice;
	}

	@Override
	public int getFilteredPrice(IShopInstance instance, int originalPrice)
	{
		if (AetherCelebrations.isEdisonNewYearsSale(instance))
		{
			return (int) Math.max(1, originalPrice * DISCOUNT);
		}

		return originalPrice;
	}
}
