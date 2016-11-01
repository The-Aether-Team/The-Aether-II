package com.gildedgames.aether.common.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ItemUtil
{

    public static IBlockState getBlockState(ItemStack stack)
    {
        if (stack.getItem() instanceof ItemBlock)
        {
            return ((ItemBlock) stack.getItem()).block.getStateFromMeta(stack.getItemDamage());
        }

        return null;
    }

    public static ItemStack getItemStack(IBlockState state, int amount)
    {
        final Block block = state.getBlock();
        return new ItemStack(block, amount, block.getMetaFromState(state));
    }

    public static void damageItem(ItemStack stack, int amount, Random rand)
    {
        if (stack.attemptDamageItem(amount, rand))
        {
            --stack.stackSize;

            if (stack.stackSize < 0)
            {
                stack.stackSize = 0;
            }

            stack.setItemDamage(0);
        }
    }

}
