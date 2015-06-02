package com.gildedgames.aether.common.blocks.util.variants;

import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import net.minecraft.item.ItemStack;

public interface IAetherBlockWithVariants
{
	BlockVariant getVariantFromStack(ItemStack stack);
}
