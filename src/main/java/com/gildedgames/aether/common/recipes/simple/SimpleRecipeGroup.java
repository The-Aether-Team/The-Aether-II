package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipe;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipeGroup;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

public class SimpleRecipeGroup implements ISimpleRecipeGroup
{

	private List<ISimpleRecipe> recipes = Lists.newArrayList();

	@Override
	public Collection<ISimpleRecipe> getRecipes()
	{
		return this.recipes;
	}

	@Override
	public void addRecipe(ISimpleRecipe recipe)
	{
		this.recipes.add(recipe);
	}

}
