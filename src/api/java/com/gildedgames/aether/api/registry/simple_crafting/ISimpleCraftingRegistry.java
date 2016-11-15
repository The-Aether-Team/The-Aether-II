package com.gildedgames.aether.api.registry.simple_crafting;

import net.minecraft.item.ItemStack;

import java.util.Collection;

public interface ISimpleCraftingRegistry
{

	Collection<ISimpleRecipe> getAllRecipes();

	void registerRecipe(int id, ISimpleRecipe recipe);

	void finalizeRecipes();

	ISimpleRecipe getRecipe(int id);

	int getId(ISimpleRecipe recipe);

	ISimpleRecipeGroup[] getRecipesFromRequirement(Object req);

}
