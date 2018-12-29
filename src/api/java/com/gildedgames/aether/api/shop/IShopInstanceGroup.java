package com.gildedgames.aether.api.shop;

import com.gildedgames.orbis_api.util.mc.NBT;

import java.util.Collection;

public interface IShopInstanceGroup extends NBT
{
	IShopInstance getShopInstance(int key);

	void setShopInstance(int key, IShopInstance shopInstance);

	Collection<IShopInstance> getShopInstances();
}
