package com.gildedgames.aether.api.registry.altar;

import net.minecraft.item.ItemStack;

public interface IAltarRecipe
{
	/**
	 * Used internally by the Altar to search for matching recipes.
	 *
	 * @param stack The {@link ItemStack} on the Altar.
	 * @return True if this recipe accepts this item.
	 */
	boolean matchesRecipe(ItemStack stack);

	/**
	 * Used internally to determine how much Ambrosium this recipe will use upon completion with
	 * the {@param stack}.
	 *
	 * @param stack The {@link ItemStack} on the Altar.
	 * @return The amount of Ambrosium the Altar should consume when this recipe is completed.
	 */
	int getAmbrosiumCost(ItemStack stack);

	/**
	 * Returns the output {@link ItemStack} this recipe will create upon completion with the
	 * {@param stack}.
	 *
	 * @param stack The {@link ItemStack} on the Altar.
	 * @return The output of this recipe when completed.
	 */
	ItemStack getOutput(ItemStack stack);
}
