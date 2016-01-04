package com.gildedgames.aether.common.recipes.altar;

import net.minecraft.item.ItemStack;

public class AltarEnchantRecipe implements IAltarRecipe
{
	private final int ambrosiumNeeded;

	private final ItemStack output;

	private final ItemStack input;

	public AltarEnchantRecipe(int ambrosiumNeeded, ItemStack input, ItemStack output)
	{
		this.ambrosiumNeeded = ambrosiumNeeded;

		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matchesItem(ItemStack stack)
	{
		return this.input.isItemEqual(stack);
	}

	@Override
	public int getAmbrosiumCost(ItemStack stack)
	{
		return this.ambrosiumNeeded;
	}

	@Override
	public ItemStack getOutput(ItemStack stack)
	{
		return this.output.copy();
	}
}
