package com.gildedgames.aether.api.recipes.simple;

import java.util.Collection;

public interface ISimpleCraftingRegistry
{

	Collection<ISimpleRecipe> getAllRecipes();

	void registerRecipe(ISimpleRecipe recipe);

	ISimpleRecipe getRecipeFromID(int id);

	int getIDFromRecipe(ISimpleRecipe recipe);

	Collection<ISimpleRecipeGroup> getRecipesFromRequirement(Object req);

}
