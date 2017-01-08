package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.registry.simple_crafting.ISimpleCraftingRegistry;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipe;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipeGroup;
import com.gildedgames.aether.common.recipes.simple.OreDictionaryRequirement;
import com.gildedgames.aether.common.recipes.simple.SimpleRecipeGroup;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SimpleCraftingRegistry implements ISimpleCraftingRegistry
{

	private HashBiMap<Integer, ISimpleRecipe> recipes = HashBiMap.create();

	private Map<Integer, ISimpleRecipeGroup> stackLookup = Maps.newHashMap();

	private Map<String, ISimpleRecipeGroup> oreDictionaryLookup = Maps.newHashMap();

	@Override
	public Collection<ISimpleRecipe> getAllRecipes()
	{
		return this.recipes.values();
	}

	@Override
	public void clearAllRecipes()
	{
		this.recipes.clear();
		this.stackLookup.clear();
		this.oreDictionaryLookup.clear();
	}

	@Override
	public void registerRecipe(int id, ISimpleRecipe recipe)
	{
		this.recipes.put(id, recipe);
	}

	@Override
	public void finalizeRecipes()
	{
		for (ISimpleRecipe recipe : this.recipes.values())
		{
			for (Object req : recipe.getRequired())
			{
				if (req instanceof ItemStack)
				{
					int hash = this.getHashForItemStack((ItemStack) req);

					if (!this.stackLookup.containsKey(hash))
					{
						ISimpleRecipeGroup group = new SimpleRecipeGroup();

						group.addRecipe(recipe);

						this.stackLookup.put(hash, group);
					}
					else
					{
						ISimpleRecipeGroup group = this.stackLookup.get(hash);

						if (!group.getRecipes().contains(recipe))
						{
							group.addRecipe(recipe);
						}
					}
				}
				else if (req instanceof OreDictionaryRequirement)
				{
					OreDictionaryRequirement oreReq = (OreDictionaryRequirement) req;

					if (!this.oreDictionaryLookup.containsKey(oreReq.getKey()))
					{
						ISimpleRecipeGroup group = new SimpleRecipeGroup();

						group.addRecipe(recipe);

						this.oreDictionaryLookup.put(oreReq.getKey(), group);
					}
					else
					{
						ISimpleRecipeGroup group = this.oreDictionaryLookup.get(oreReq.getKey());

						if (!group.getRecipes().contains(recipe))
						{
							group.addRecipe(recipe);
						}
					}
				}
			}
		}
	}

	@Override
	public ISimpleRecipe getRecipe(int id)
	{
		if (id < 0)
		{
			return null;
		}

		return this.recipes.get(id);
	}

	@Override
	public int getId(ISimpleRecipe recipe)
	{
		if (recipe == null)
		{
			return -1;
		}

		return this.recipes.inverse().get(recipe);
	}

	@Override
	public ISimpleRecipeGroup[] getRecipesFromRequirement(Object req)
	{
		if (req instanceof ItemStack)
		{
			ItemStack stack = (ItemStack) req;
			int[] ids = OreDictionary.getOreIDs(stack);

			if (ids.length > 0)
			{
				List<ISimpleRecipeGroup> groups = Lists.newArrayList();

				for (int id : ids)
				{
					String key = OreDictionary.getOreName(id);

					if (this.oreDictionaryLookup.containsKey(key))
					{
						groups.add(this.oreDictionaryLookup.get(key));
					}
				}

				if (groups.size() > 0)
				{
					return groups.toArray(new ISimpleRecipeGroup[groups.size()]);
				}
			}

			int hash = this.getHashForItemStack(stack);

			return new ISimpleRecipeGroup[] { this.stackLookup.get(hash) };
		}
		else if (req instanceof OreDictionaryRequirement)
		{
			return new ISimpleRecipeGroup[] { this.oreDictionaryLookup.get(((OreDictionaryRequirement) req).getKey()) };
		}
		else if (req instanceof String)
		{
			return new ISimpleRecipeGroup[] { this.oreDictionaryLookup.get(req) };
		}

		return new ISimpleRecipeGroup[] {};
	}

	private int getHashForItemStack(ItemStack stack)
	{
		int hash = (Item.getIdFromItem(stack.getItem()) & 0xFFFF) << 16;

		if (!stack.isItemStackDamageable() && stack.getItemDamage() != OreDictionary.WILDCARD_VALUE)
		{
			hash = hash | (stack.getItemDamage() & 0xFFFF);
		}

		return hash;
	}

}
