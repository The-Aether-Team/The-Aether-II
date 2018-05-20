package com.gildedgames.aether.api.shop;

import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public interface IShopBuy extends NBT
{
	ItemStack getItemStack();

	void addStock(int stock);

	int getStock();

	Collection<String> getUnlocalizedDescriptions();

	int getMaxStock();

	int getTicksUntilRestock();

	int getPrice();

	double getSellingPrice();

	void tick();

	boolean isDirty();

	void markClean();
}
