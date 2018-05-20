package com.gildedgames.aether.api.shop;

import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.inventory.IInventory;

import java.util.Collection;
import java.util.List;

public interface IShopInstance extends NBT
{
	List<IShopBuy> getStock();

	Collection<String> getUnlocalizedGreetings();

	void tick();

	IInventory getInventory();

	boolean isDirty();

	void markClean();
}
