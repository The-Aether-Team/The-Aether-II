package com.gildedgames.aether.api.shop;

import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import java.util.List;

public interface IShopInstance extends NBT
{
	List<IShopBuy> getStock();

	List<String> getUnlocalizedGreetings();

	void tick();

	IInventory getInventory(EntityPlayer player);

	boolean isDirty();

	void markClean();

	IShopCurrency getCurrencyType();
}
