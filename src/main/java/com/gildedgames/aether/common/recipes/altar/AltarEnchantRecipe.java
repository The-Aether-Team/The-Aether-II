package com.gildedgames.aether.common.recipes.altar;

import com.gildedgames.aether.api.recipes.altar.IAltarRecipe;
import net.minecraft.item.ItemStack;

public class AltarEnchantRecipe implements IAltarRecipe
{
	private final int cost;

	private final ItemStack output;

	private final ItemStack input;

	public AltarEnchantRecipe(int cost, ItemStack input, ItemStack output)
	{
		this.cost = cost;
		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matchesInput(ItemStack stack)
	{
		return this.input.isItemEqual(stack);
	}

	@Override
	public boolean matchesOutput(ItemStack stack)
	{
		return this.input.isItemEqual(stack);
	}

	@Override
	public int getAmbrosiumCost(ItemStack stack)
	{
		return this.cost;
	}

	@Override
	public ItemStack getOutput(ItemStack stack)
	{
		return this.output.copy();
	}

	@Override
	public ItemStack getInput()
	{
		return this.input;
	}
}
