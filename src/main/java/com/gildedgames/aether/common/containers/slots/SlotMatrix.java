package com.gildedgames.aether.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SlotMatrix extends Slot
{

	private boolean isSimpleCrafting;

	public SlotMatrix(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	public void setSimpleCrafting(boolean flag)
	{
		this.isSimpleCrafting = flag;
	}

	public boolean isSimpleCrafting()
	{
		return this.isSimpleCrafting;
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack)
	{
		return !this.isSimpleCrafting;
	}

}
