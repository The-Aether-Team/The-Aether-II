package com.gildedgames.aether.api.registry.simple_crafting;

import net.minecraft.item.ItemStack;

public interface ISimpleRecipe
{

	Object[] getRequired();

	ItemStack getResult();

}
