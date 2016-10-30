package com.gildedgames.aether.common.util.helpers;

import net.minecraft.item.ItemStack;

import java.util.Random;

public class ItemUtil
{

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
