package com.gildedgames.aether.common.shop;

import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ShopBuy implements IShopBuy
{
	private ItemStack stack;

	private int price;

	private double sellingPrice;

	private int ticksUntilRestock, maxStock;

	private int currentTicks;

	// TODO: Should be saving the shop ResourceLocation and the buy definition tag, then fetching back the descriptions
	private List<String> descriptions;

	private boolean isDirty;

	private int stock;

	private ShopBuy()
	{

	}

	public ShopBuy(ItemStack stack, List<String> descriptions, int price, double sellingPrice, int ticksUntilRestock, int maxStock)
	{
		this.stack = stack;
		this.descriptions = descriptions;
		this.price = price;
		this.sellingPrice = sellingPrice;
		this.ticksUntilRestock = ticksUntilRestock;
		this.maxStock = maxStock;
		this.stock = maxStock;
	}

	@Override
	public ItemStack getItemStack()
	{
		return this.stack;
	}

	@Override
	public void addStock(int stock)
	{
		this.stock = (Math.max(0, this.stock + stock));

		this.isDirty = true;
	}

	@Override
	public int getStock()
	{
		return this.stock;
	}

	@Override
	public List<String> getUnlocalizedDescriptions()
	{
		return this.descriptions;
	}

	@Override
	public int getMaxStock()
	{
		return this.maxStock;
	}

	@Override
	public int getTicksUntilRestock()
	{
		return this.ticksUntilRestock;
	}

	@Override
	public int getPrice()
	{
		return this.price;
	}

	@Override
	public double getSellingPrice()
	{
		return this.sellingPrice;
	}

	@Override
	public void tick()
	{
		if (this.getStock() < this.getMaxStock())
		{
			this.currentTicks++;

			if (this.currentTicks >= this.getTicksUntilRestock())
			{
				this.currentTicks = 0;
				this.addStock(1);
			}
		}
		else
		{
			this.currentTicks = 0;
		}
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
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setStack("stack", this.stack);
		funnel.setStringList("descriptions", this.descriptions);
		tag.setInteger("price", this.price);
		tag.setDouble("sellingPrice", this.sellingPrice);
		tag.setInteger("maxStock", this.maxStock);
		tag.setInteger("ticksUntilRestock", this.ticksUntilRestock);
		tag.setInteger("stock", this.stock);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		this.stack = funnel.getStack("stack");
		this.descriptions = funnel.getStringList("descriptions");
		this.price = tag.getInteger("price");
		this.sellingPrice = tag.getDouble("sellingPrice");
		this.maxStock = tag.getInteger("maxStock");
		this.ticksUntilRestock = tag.getInteger("ticksUntilRestock");
		this.stock = tag.getInteger("stock");
	}
}
