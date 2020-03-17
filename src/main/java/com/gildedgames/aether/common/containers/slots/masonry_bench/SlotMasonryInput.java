package com.gildedgames.aether.common.containers.slots.masonry_bench;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.containers.slots.SlotOffset;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotMasonryInput extends SlotOffset
{
    public SlotMasonryInput(IInventory inventoryIn, int index, int xPosition, int yPosition, int trueIndex)
    {
        super(inventoryIn, index, xPosition, yPosition, trueIndex);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof ItemBlock || stack.getItem() == ItemsAether.skyroot_door_item;
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return 64;
    }
}
