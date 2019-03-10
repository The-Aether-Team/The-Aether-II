package com.gildedgames.aether.common.shop;

import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.google.common.collect.Maps;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Collection;
import java.util.Map;

public class ShopInstanceGroup implements IShopInstanceGroup
{
	private Map<Integer, IShopInstance> keyToShopInstance = Maps.newHashMap();

	@Override
	public IShopInstance getShopInstance(int key)
	{
		return this.keyToShopInstance.get(key);
	}

	@Override
	public void setShopInstance(int key, IShopInstance shopInstance)
	{
		this.keyToShopInstance.put(key, shopInstance);
	}

	@Override
	public Collection<IShopInstance> getShopInstances()
	{
		return this.keyToShopInstance.values();
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setMap("keyToShopInstance", this.keyToShopInstance, NBTFunnel.INTEGER_SETTER, NBTFunnel.setter());
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		this.keyToShopInstance = funnel.getMap("keyToShopInstance", NBTFunnel.INTEGER_GETTER, NBTFunnel.getter());
	}
}
