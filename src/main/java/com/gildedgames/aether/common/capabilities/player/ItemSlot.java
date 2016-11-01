package com.gildedgames.aether.common.capabilities.player;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemSlot implements NBT
{

	private int slot;

	private ItemStack stack;

	private ItemSlot()
	{

	}

	public ItemSlot(int slot, ItemStack stack)
	{
		this.slot = slot;
		this.stack = stack;
	}

	public int getSlot()
	{
		return this.slot;
	}

	public ItemStack getStack()
	{
		return this.stack;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setInteger("slot", this.slot);

		if (this.stack != null)
		{
			NBTTagCompound stackTag = new NBTTagCompound();
			this.stack.writeToNBT(stackTag);

			output.setTag("stack", stackTag);
		}
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.slot = input.getInteger("slot");

		if (input.hasKey("stack"))
		{
			this.stack = ItemStack.loadItemStackFromNBT(input.getCompoundTag("stack"));
		}
	}
}
