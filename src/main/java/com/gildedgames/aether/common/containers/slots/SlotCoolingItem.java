package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotCoolingItem extends SlotOffset
{
	public SlotCoolingItem(IInventory inventoryIn, int index, int xPosition, int yPosition, int trueIndex)
	{
		super(inventoryIn, index, xPosition, yPosition, trueIndex);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return super.isItemValid(stack) && stack.getItem() == ItemsAether.icestone;
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 64;
	}

}
