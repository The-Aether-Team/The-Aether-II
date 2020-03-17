package com.gildedgames.aether.common.containers.slots.icestone_cooler;

import com.gildedgames.aether.common.containers.slots.SlotOffset;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotCoolingInputPrimary extends SlotOffset
{
	public SlotCoolingInputPrimary(IInventory inventoryIn, int index, int xPosition, int yPosition, int trueIndex)
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
		return 64;
	}
}
