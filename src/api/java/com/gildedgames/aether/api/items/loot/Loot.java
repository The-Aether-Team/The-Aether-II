package com.gildedgames.aether.api.items.loot;

import net.minecraft.item.ItemStack;

import java.util.Random;

@Deprecated
// Will be replaced with vanilla's loot tables.
public interface Loot
{
	/**
	 * Selects a random {@link ItemStack} from this loot pool.
	 * @param random An instance of {@link Random}
	 * @return A non-empty {@link ItemStack} picked from the pool
	 */
	ItemStack select(Random random);
}
