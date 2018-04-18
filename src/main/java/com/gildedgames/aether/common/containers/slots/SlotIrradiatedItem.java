package com.gildedgames.aether.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotIrradiatedItem extends SlotOffset
{
	public SlotIrradiatedItem(IInventory inventoryIn, int index, int xPosition, int yPosition, int trueIndex)
	{
		super(inventoryIn, index, xPosition, yPosition, trueIndex);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return super.isItemValid(stack);
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}
}
