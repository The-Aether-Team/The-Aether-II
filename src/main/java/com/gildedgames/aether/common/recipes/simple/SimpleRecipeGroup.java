package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.api.recipes.simple.ISimpleRecipeGroup;
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
