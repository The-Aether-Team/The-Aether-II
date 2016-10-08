package com.gildedgames.aether.common.util;

import net.minecraft.item.ItemStack;

public interface Constraint
{

    boolean accept(ItemStack stack);

}