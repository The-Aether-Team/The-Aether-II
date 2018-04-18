package com.gildedgames.aether.api.recipes.simple;

import java.util.Collection;

public interface ISimpleRecipeGroup
{

	Collection<ISimpleRecipe> getRecipes();

	void addRecipe(ISimpleRecipe recipe);

}
