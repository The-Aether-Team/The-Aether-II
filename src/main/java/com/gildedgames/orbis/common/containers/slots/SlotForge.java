package com.gildedgames.orbis.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotForge extends Slot
{
	private final int indexOffset;

	public SlotForge(final IInventory inventory, final int indexOffset, final int index, final int xPosition, final int yPosition)
	{
		super(inventory, index, xPosition, yPosition);

		this.indexOffset = indexOffset;
	}

	@Override
	public ItemStack decrStackSize(final int amount)
	{
		return this.inventory.decrStackSize(this.getSlotIndex() - this.indexOffset, amount);
	}

	@Override
	public boolean isHere(final IInventory inv, final int slotIn)
	{
		return inv == this.inventory && slotIn == this.getSlotIndex();
	}

	@Override
	public ItemStack getStack()
	{
		return this.inventory.getStackInSlot(this.getSlotIndex() - this.indexOffset);
	}

	@Override
	public void putStack(final ItemStack stack)
	{
		this.inventory.setInventorySlotContents(this.getSlotIndex() - this.indexOffset, stack);
		this.onSlotChanged();
	}

	@Override
	public boolean isItemValid(final ItemStack stack)
	{
		return this.inventory.isItemValidForSlot(this.getSlotIndex() - this.indexOffset, stack);
	}
}
