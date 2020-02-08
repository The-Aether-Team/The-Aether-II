package com.gildedgames.aether.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockCloudwool extends ItemBlock
{
    public ItemBlockCloudwool(Block block)
    {
        super(block);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
        return 100;
    }
}
