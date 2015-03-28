package net.aetherteam.aether.blocks.util;

import net.minecraft.item.ItemStack;

public interface ISubtypedAetherBlock
{
	public abstract String getNameFromSubtype(ItemStack stack);
}
