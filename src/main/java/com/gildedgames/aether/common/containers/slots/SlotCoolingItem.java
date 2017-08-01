package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCoolingItem extends Slot
{
	public SlotCoolingItem(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.getItem() == ItemsAether.icestone;
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 64;
	}

}
