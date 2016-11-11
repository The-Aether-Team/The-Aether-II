package com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.minecraft.util.wrappers.MinecraftItemStackRender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryItemStack implements InventoryElement
{

	private ItemStack stack;

	public InventoryItemStack(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public String name()
	{
		return this.stack.getDisplayName();
	}

	@Override
	public GuiFrame createIcon()
	{
		return new MinecraftItemStackRender(this.stack);
	}

	@Override
	public void write(NBTTagCompound output)
	{

	}

	@Override
	public void read(NBTTagCompound input)
	{

	}

}
