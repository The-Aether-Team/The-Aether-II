package com.gildedgames.aether.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotSell extends Slot
{

	public SlotSell(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		return this.inventory.isItemValidForSlot(this.getSlotIndex(), stack);
	}

}
