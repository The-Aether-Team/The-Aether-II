package com.gildedgames.aether.common.recipes.altar;

import net.minecraft.item.ItemStack;

public class AltarBasicRecipe implements IAltarRecipe
{
	private final int ambrosiumNeeded;

	private final ItemStack output;

	private final ItemStack input;

	public AltarBasicRecipe(int ambrosiumNeeded, ItemStack input, ItemStack output)
	{
		this.ambrosiumNeeded = ambrosiumNeeded;

		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matchesRecipe(int ambrosiumCount, ItemStack stack)
	{
		return ambrosiumCount >= ambrosiumNeeded && input.isItemEqual(stack);
	}

	@Override
	public int getAmbrosiumNeeded()
	{
		return this.ambrosiumNeeded;
	}

	@Override
	public ItemStack getOutput()
	{
		return this.output;
	}
}
