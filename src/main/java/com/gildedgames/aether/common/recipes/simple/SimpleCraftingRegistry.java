package com.gildedgames.aether.common.recipes.simple;

import com.gildedgames.aether.api.recipes.simple.ISimpleCraftingRegistry;
import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.api.recipes.simple.ISimpleRecipeGroup;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

public class SimpleCraftingRegistry implements ISimpleCraftingRegistry
{

	private final HashBiMap<Integer, ISimpleRecipe> recipes = HashBiMap.create();

	private final Map<Integer, ISimpleRecipeGroup> stackLookup = Maps.newHashMap();

	private final Map<String, ISimpleRecipeGroup> oreDictionaryLookup = Maps.newHashMap();

	private int id;

	@Override
	public Collection<ISimpleRecipe> getAllRecipes()
	{
		return Collections.unmodifiableCollection(this.recipes.values());
	}

	@Override
	public void registerRecipe(ISimpleRecipe recipe)
	{
		this.recipes.put(this.id, recipe);

		this.id++;
	}

	public void finalizeRecipes()
	{
		this.stackLookup.clear();
		this.oreDictionaryLookup.clear();

		for (ISimpleRecipe recipe : this.recipes.values())
		{
			for (Object req : recipe.getRequired())
			{
				if (req instanceof ItemStack)
				{
					if (((ItemStack) req).isEmpty())
					{
						continue;
					}

					int hash = this.getItemStackKey((ItemStack) req);

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
	public ISimpleRecipe getRecipeFromID(int id)
	{
		if (id < 0)
		{
			return null;
		}

		return this.recipes.get(id);
	}

	@Override
	public int getIDFromRecipe(ISimpleRecipe recipe)
	{
		if (recipe == null)
		{
			return -1;
		}

		return this.recipes.inverse().get(recipe);
	}

	@Override
	public Collection<ISimpleRecipeGroup> getRecipesFromRequirement(Object req)
	{
		if (req instanceof ItemStack)
		{
			ItemStack stack = (ItemStack) req;

			if (stack.isEmpty())
			{
				return new ArrayList<>();
			}

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
					int key = this.getItemStackKey(stack);

					return Lists.newArrayList(this.stackLookup.get(key));
				}
			}

			int hash = this.getItemStackKey(stack);

			return Lists.newArrayList(this.stackLookup.get(hash));
		}
		else if (req instanceof OreDictionaryRequirement)
		{
			return Lists.newArrayList(this.oreDictionaryLookup.get(((OreDictionaryRequirement) req).getKey()));
		}
		else if (req instanceof String)
		{
			return Lists.newArrayList(this.oreDictionaryLookup.get(req));
		}

		return new ArrayList<>();
	}

	private int getItemStackKey(ItemStack stack)
	{
		int hash = (Item.getIdFromItem(stack.getItem()) & 0xFFFF) << 16;

		if (!stack.isItemStackDamageable() && stack.getItemDamage() != OreDictionary.WILDCARD_VALUE)
		{
			hash = hash | (stack.getItemDamage() & 0xFFFF);
		}

		return hash;
	}

}
