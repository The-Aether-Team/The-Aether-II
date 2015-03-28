package net.aetherteam.aether.blocks.util;

import net.minecraft.item.ItemStack;

public interface IAetherBlockWithVariants
{
	public abstract String getVariantNameFromStack(ItemStack stack);
}
