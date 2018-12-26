package com.gildedgames.aether.common.shop.filters;

import com.gildedgames.aether.api.shop.IShopFilter;
import com.gildedgames.aether.common.AetherCelebrations;

public class ShopFilterHolidays implements IShopFilter
{
	public static final float DISCOUNT = 0.5F;

	@Override
	public double getFilteredPrice(double originalPrice)
	{
		if (AetherCelebrations.isHolidayEvent())
		{
			return originalPrice * DISCOUNT;
		}

		return originalPrice;
	}

	@Override
	public int getFilteredPrice(int originalPrice)
	{
		if (AetherCelebrations.isHolidayEvent())
		{
			return (int) Math.max(1, originalPrice * DISCOUNT);
		}

		return originalPrice;
	}
}
