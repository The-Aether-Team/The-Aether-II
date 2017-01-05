package com.gildedgames.aether.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SlotInventory extends Slot
{

    public SlotInventory(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public boolean isItemValid(@Nullable ItemStack stack)
    {
        if (stack == null)
        {
            return true;
        }

        return this.inventory.isItemValidForSlot(this.getSlotIndex(), stack);
    }

}
