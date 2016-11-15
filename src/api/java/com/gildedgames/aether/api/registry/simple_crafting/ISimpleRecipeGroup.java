package com.gildedgames.aether.api.registry.simple_crafting;

import java.util.Collection;

public interface ISimpleRecipeGroup
{

	Collection<ISimpleRecipe> getRecipes();

	void addRecipe(ISimpleRecipe recipe);

}
