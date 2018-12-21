package com.gildedgames.aether.api.shop;

import net.minecraft.item.ItemStack;

public interface ICurrencyRegistry
{

	void clearRegistrations();

	void registerValue(ItemStack stack, double value, Class<? extends IShopCurrency> currency);

	double getValue(ItemStack stack, Class<? extends IShopCurrency> currency);

	double getSingleValue(ItemStack stack, Class<? extends IShopCurrency> currency);

	boolean hasValue(ItemStack stack, Class<? extends IShopCurrency> currency);

}
