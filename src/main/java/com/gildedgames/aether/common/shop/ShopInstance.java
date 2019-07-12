package com.gildedgames.aether.common.shop;

import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopCurrency;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShopInstance implements IShopInstance
{
	private LinkedList<IShopBuy> stock;

	private List<String> greetings;

	private boolean isDirty;

	private Map<UUID, ShopInventory> inventories = Maps.newHashMap();

	private IShopCurrency currencyType;

	private ResourceLocation shopDefinitionLocation;

	private ShopInstance()
	{

	}

	public ShopInstance(ResourceLocation shopDefinitionLocation, LinkedList<IShopBuy> stock, List<String> greetings, IShopCurrency currencyType)
	{
		this.shopDefinitionLocation = shopDefinitionLocation;
		this.stock = stock;
		this.greetings = greetings;
		this.currencyType = currencyType;
	}

	@Override
	public ResourceLocation getShopDefinitionLocation()
	{
		return this.shopDefinitionLocation;
	}

	@Override
	public List<IShopBuy> getStock()
	{
		return this.stock;
	}

	@Override
	public List<String> getUnlocalizedGreetings()
	{
		return this.greetings;
	}

	@Override
	public void tick()
	{
		for (IShopBuy buy : this.stock)
		{
			buy.tick();

			if (buy.isDirty())
			{
				this.isDirty = true;
			}
		}
	}

	@Override
	public IInventory getInventory(PlayerEntity player)
	{
		if (!this.inventories.containsKey(player.getUniqueID()))
		{
			this.inventories.put(player.getUniqueID(), new ShopInventory(this));
		}

		return this.inventories.get(player.getUniqueID());
	}

	@Override
	public boolean isDirty()
	{
		return this.isDirty;
	}

	@Override
	public void markClean()
	{
		this.isDirty = false;

		for (IShopBuy buy : this.stock)
		{
			buy.markClean();
		}
	}

	@Override
	public IShopCurrency getCurrencyType()
	{
		return this.currencyType;
	}

	@Override
	public void write(CompoundNBT tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setList("stock", this.stock);
		funnel.setStringList("greetings", this.greetings);
		funnel.setMap("inventories", this.inventories, NBTFunnel.UUID_SETTER, NBTFunnel.setter());
		funnel.set("currencyType", this.currencyType);

		if (this.shopDefinitionLocation != null)
		{
			tag.putString("shopDefinitionLocation", this.shopDefinitionLocation.toString());
		}
	}

	@Override
	public void read(CompoundNBT tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		this.stock = Lists.newLinkedList(funnel.getList("stock"));
		this.greetings = funnel.getStringList("greetings");
		this.inventories = funnel.getMap("inventories", NBTFunnel.UUID_GETTER, NBTFunnel.getter());

		for (ShopInventory inventory : this.inventories.values())
		{
			inventory.setShopInstance(this);
		}

		this.currencyType = funnel.getWithDefault("currencyType", ShopCurrencyGilt::new);

		if (tag.contains("shopDefinitionLocation"))
		{
			this.shopDefinitionLocation = new ResourceLocation(tag.getString("shopDefinitionLocation"));
		}
	}

}
