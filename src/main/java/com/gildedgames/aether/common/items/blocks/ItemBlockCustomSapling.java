package com.gildedgames.aether.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockCustomSapling extends ItemBlockMultiName
{
    public ItemBlockCustomSapling(Block block)
    {
        super(block);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
        return 100;
    }
}
