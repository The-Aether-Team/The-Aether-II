package com.gildedgames.aether.common.shop;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class ShopInventory implements IInventory, NBT
{
	private final NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);

	private ShopInstance shopInstance;

	private ShopInventory()
	{

	}

	public ShopInventory(ShopInstance shopInstance)
	{
		this.shopInstance = shopInstance;
	}

	public void setShopInstance(ShopInstance shopInstance)
	{
		this.shopInstance = shopInstance;
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
		int hash = ItemHelper.getKeyForItemStack(stack);

		for (IShopBuy buy : this.shopInstance.getStock())
		{
			int buyHash = ItemHelper.getKeyForItemStack(buy.getItemStack());

			if (buyHash == hash)
			{
				return true;
			}
		}

		return AetherAPI.content().currency().hasValue(stack, this.shopInstance.getCurrencyType().getClass());
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

	@Override
	public void write(NBTTagCompound tag)
	{
		ItemStackHelper.saveAllItems(tag, this.stacks);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		ItemStackHelper.loadAllItems(tag, this.stacks);
	}

}
