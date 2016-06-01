package com.gildedgames.aether.common.items.properties;

import net.minecraft.item.ItemStack;

public interface IPhoenixChillable
{
	boolean canChillItemstack(ItemStack stack);

	ItemStack getChilledItemstack(ItemStack stack);
}
