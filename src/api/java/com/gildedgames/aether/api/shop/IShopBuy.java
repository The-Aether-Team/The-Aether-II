package com.gildedgames.aether.api.shop;

import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IShopBuy extends NBT
{
	ItemStack getItemStack();

	void addStock(int stock);

	int getStock();

	List<String> getUnlocalizedDescriptions();

	int getMaxStock();

	int getTicksUntilRestock();

	int getPrice();

	double getSellingPrice();

	void tick();

	boolean isDirty();

	void markClean();
}
