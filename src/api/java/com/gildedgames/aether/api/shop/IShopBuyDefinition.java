package com.gildedgames.aether.api.shop;

import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Optional;

public interface IShopBuyDefinition
{
	Optional<ItemStack> getItemStack();

	Collection<String> getUnlocalizedDescriptions();

	int getTicksUntilRestock();

	float getRarityWeight();

	double getMinSellPrice();

	double getMaxSellPrice();

	int getMinPrice();

	int getMaxPrice();

	int getMaxStock();

	int getMinStock();
}
