package com.gildedgames.aether.common.items.blocks;

import com.gildedgames.aether.api.registrar.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFloorObject extends ItemBlock
{
    public ItemBlockFloorObject(Block block)
    {
        super(block);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
        if (block == BlocksAether.skyroot_twigs)
        {
            return 100;
        }
        else
        {
            return 0;
        }
    }
}
