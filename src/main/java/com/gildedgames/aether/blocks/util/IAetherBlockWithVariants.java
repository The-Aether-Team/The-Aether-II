package com.gildedgames.aether.blocks.util;

import net.minecraft.item.ItemStack;

public interface IAetherBlockWithVariants
{
	String getVariantNameFromStack(ItemStack stack);
}
