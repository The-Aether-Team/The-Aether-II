package com.gildedgames.aether.api.recipes.simple;

import net.minecraft.item.ItemStack;

public interface ISimpleRecipe
{

	Object[] getRequired();

	ItemStack getResult();

}
