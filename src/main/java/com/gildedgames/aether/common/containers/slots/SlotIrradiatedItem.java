package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotIrradiatedItem extends Slot
{
	public SlotIrradiatedItem(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	public boolean isItemValid(ItemStack stack)
	{
		Item item = stack.getItem();
		return item == ItemsAether.irradiated_armor || item == ItemsAether.irradiated_charm || item == ItemsAether.irradiated_neckwear
				|| item == ItemsAether.irradiated_ring || item == ItemsAether.irradiated_sword || item == ItemsAether.irradiated_tool;
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}
}
