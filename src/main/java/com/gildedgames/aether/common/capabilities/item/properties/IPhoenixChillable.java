package com.gildedgames.aether.common.capabilities.item.properties;

import net.minecraft.item.ItemStack;

public interface IPhoenixChillable
{
	boolean canChillItemstack(ItemStack stack);

	ItemStack getChilledItemstack(ItemStack stack);
}
