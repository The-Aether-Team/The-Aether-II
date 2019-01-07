package com.gildedgames.aether.common.shop.filters;

import com.gildedgames.aether.api.shop.IShopFilter;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.common.AetherCelebrations;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import net.minecraft.item.ItemStack;

public class ShopFilterNewYearsEdisonSale implements IShopFilter
{
	public static final float DISCOUNT = 0.5F;

	@Override
	public double getFilteredPrice(IShopInstance instance, ItemStack stack, double originalPrice)
	{
		if (AetherCelebrations.isEdisonNewYearsSale(instance) && instance.getStock().stream()
				.anyMatch((stock) -> ItemHelper.areEqual(stock.getItemStack(), stack)))
		{
			return originalPrice * DISCOUNT;
		}

		return originalPrice;
	}

	@Override
	public int getFilteredPrice(IShopInstance instance, ItemStack stack, int originalPrice)
	{
		if (AetherCelebrations.isEdisonNewYearsSale(instance) && instance.getStock().stream()
				.anyMatch((stock) -> ItemHelper.areEqual(stock.getItemStack(), stack)))
		{
			return (int) Math.max(1, originalPrice * DISCOUNT);
		}

		return originalPrice;
	}
}
