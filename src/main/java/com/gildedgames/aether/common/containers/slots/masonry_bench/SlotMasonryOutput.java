package com.gildedgames.aether.common.containers.slots.masonry_bench;

import com.gildedgames.aether.common.containers.slots.SlotOffset;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotMasonryOutput extends SlotOffset
{
    private int removeCount;

    public SlotMasonryOutput(IInventory inventoryIn, int index, int xPosition, int yPosition, int trueIndex)
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
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return false;
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
