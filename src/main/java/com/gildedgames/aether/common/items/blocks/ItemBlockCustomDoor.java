package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;

public class ItemBlockCustomDoor extends ItemDoor
{
    public ItemBlockCustomDoor(Block block)
    {
        super(block);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
       if (itemStack.getItem() != ItemsAether.arkenium_door_item)
       {
           return 200;
       }
       else
       {
           return 0;
       }
    }
}
