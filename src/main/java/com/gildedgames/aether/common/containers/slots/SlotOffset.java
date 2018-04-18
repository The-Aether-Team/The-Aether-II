package com.gildedgames.aether.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOffset extends Slot
{
	private int trueIndex;

	public SlotOffset(IInventory inventoryIn, int index, int xPosition, int yPosition, int trueIndex)
	{
		super(inventoryIn, index, xPosition, yPosition);

		this.trueIndex = trueIndex;
	}

	@Override
	public ItemStack decrStackSize(final int amount)
	{
		return this.inventory.decrStackSize(this.trueIndex, amount);
	}

	@Override
	public boolean isHere(final IInventory inv, final int slotIn)
	{
		return inv == this.inventory && slotIn == this.getSlotIndex();
	}

	@Override
	public ItemStack getStack()
	{
		return this.inventory.getStackInSlot(this.trueIndex);
	}

	@Override
	public void putStack(final ItemStack stack)
	{
		this.inventory.setInventorySlotContents(this.trueIndex, stack);
		this.onSlotChanged();
	}

	@Override
	public boolean isItemValid(final ItemStack stack)
	{
		return this.inventory.isItemValidForSlot(this.trueIndex, stack);
	}
}
