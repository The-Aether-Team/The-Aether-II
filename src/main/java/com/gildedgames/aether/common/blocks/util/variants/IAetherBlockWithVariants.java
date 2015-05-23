package com.gildedgames.aether.common.blocks.util.variants;

import net.minecraft.item.ItemStack;

public interface IAetherBlockWithVariants
{
	String getVariantNameFromStack(ItemStack stack);
}
