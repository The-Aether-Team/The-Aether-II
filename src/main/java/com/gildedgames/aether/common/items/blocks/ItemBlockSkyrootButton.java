package com.gildedgames.aether.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSkyrootButton extends ItemBlock
{
    public ItemBlockSkyrootButton(Block block)
    {
        super(block);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
        return 100;
    }
}
