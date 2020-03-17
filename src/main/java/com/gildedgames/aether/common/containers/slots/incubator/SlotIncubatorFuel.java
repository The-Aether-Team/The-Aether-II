package com.gildedgames.aether.common.containers.slots.incubator;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotIncubatorFuel extends Slot
{
	public SlotIncubatorFuel(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		return stack.getItem() == ItemsAether.ambrosium_chunk || stack.getItem() == ItemsAether.irradiated_dust;
	}
}
