package com.gildedgames.aether.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotCoolerOutput extends SlotOffset
{
	private int removeCount;

	public SlotCoolerOutput(IInventory inventoryIn, int index, int xPosition, int yPosition, int trueIndex)
	{
		super(inventoryIn, index, xPosition, yPosition, trueIndex);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 64;
	}

	@Override
	public ItemStack decrStackSize(int amount)
	{
		if (this.getHasStack())
		{
			this.removeCount += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}

}
