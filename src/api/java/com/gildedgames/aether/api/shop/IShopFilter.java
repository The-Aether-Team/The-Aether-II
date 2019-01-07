package com.gildedgames.aether.api.shop;

import net.minecraft.item.ItemStack;

public interface IShopFilter
{
	double getFilteredPrice(IShopInstance instance, ItemStack stack, double originalPrice);

	int getFilteredPrice(IShopInstance instance, ItemStack stack, int originalPrice);
}
