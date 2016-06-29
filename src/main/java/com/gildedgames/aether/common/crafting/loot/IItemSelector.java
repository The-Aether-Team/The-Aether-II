package com.gildedgames.aether.common.crafting.loot;

import net.minecraft.item.ItemStack;

import java.util.Random;

public interface IItemSelector
{
	/**
	 * @return A random item from this selector.
	 */
	ItemStack getRandomItem(Random random);
}
