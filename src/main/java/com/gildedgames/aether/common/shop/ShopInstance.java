package com.gildedgames.aether.common.shop;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ShopInstance implements IShopInstance, IInventory
{
	private LinkedList<IShopBuy> stock;

	private List<String> greetings;

	private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);

	private boolean isDirty;

	private ShopInstance()
	{

	}

	public ShopInstance(LinkedList<IShopBuy> stock, List<String> greetings)
	{
		this.stock = stock;
		this.greetings = greetings;
	}

	@Override
	public List<IShopBuy> getStock()
	{
		return this.stock;
	}

	@Override
	public Collection<String> getUnlocalizedGreetings()
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
	public IInventory getInventory()
	{
		return this;
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
	public void write(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setList("stock", this.stock);
		funnel.setStringList("greetings", this.greetings);
		ItemStackHelper.saveAllItems(tag, this.stacks);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		this.stock = Lists.newLinkedList(funnel.getList("stock"));
		this.greetings = funnel.getStringList("greetings");
		ItemStackHelper.loadAllItems(tag, this.stacks);
	}

	@Override
	public int getSizeInventory()
	{
		return this.stacks.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.stacks)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.stacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.stacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = this.stacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag)
		{
			this.markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{

	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{

	}

	@Override
	public void closeInventory(EntityPlayer player)
	{

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		int hash = ItemHelper.getHashForItemStack(stack);

		for (IShopBuy buy : this.getStock())
		{
			int buyHash = ItemHelper.getHashForItemStack(buy.getItemStack());

			if (buyHash == hash)
			{
				return true;
			}
		}

		return AetherAPI.content().currency().hasValue(stack);
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{

	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		this.stacks.clear();
	}

	@Override
	public String getName()
	{
		return "Shop";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation(this.getName());
	}
}
