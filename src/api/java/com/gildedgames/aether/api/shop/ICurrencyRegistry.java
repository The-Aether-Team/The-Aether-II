package com.gildedgames.aether.api.shop;

import net.minecraft.item.ItemStack;

public interface ICurrencyRegistry
{

	void clearRegistrations();

	void registerValue(ItemStack stack, double value);

	double getValue(ItemStack stack);

	double getSingleValue(ItemStack stack);

	boolean hasValue(ItemStack stack);

}
