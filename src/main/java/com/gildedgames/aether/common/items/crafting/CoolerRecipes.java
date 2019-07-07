package com.gildedgames.aether.common.items.crafting;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class CoolerRecipes
{
	private static final CoolerRecipes COOLING_BASE = new CoolerRecipes();

	private final Map<ItemStack, ItemStack[]> coolingList = Maps.newHashMap();

	private final Random rand = new Random();

	private CoolerRecipes()
	{
		this.addCoolingFromItem(ItemsAether.irradiated_armor,
				new ItemStack(ItemsAether.arkenium_helmet),
				new ItemStack(ItemsAether.arkenium_chestplate),
				new ItemStack(ItemsAether.arkenium_leggings),
				new ItemStack(ItemsAether.arkenium_boots),
				new ItemStack(ItemsAether.gravitite_helmet),
				new ItemStack(ItemsAether.arkenium_gloves),
				new ItemStack(ItemsAether.gravitite_chestplate),
				new ItemStack(ItemsAether.gravitite_leggings),
				new ItemStack(ItemsAether.gravitite_boots),
				new ItemStack(ItemsAether.gravitite_gloves),
				new ItemStack(ItemsAether.zanite_helmet),
				new ItemStack(ItemsAether.zanite_chestplate),
				new ItemStack(ItemsAether.zanite_leggings),
				new ItemStack(ItemsAether.zanite_boots),
				new ItemStack(ItemsAether.zanite_gloves));

		this.addCoolingFromItem(ItemsAether.irradiated_sword,
				new ItemStack(ItemsAether.arkenium_sword),
				new ItemStack(ItemsAether.gravitite_sword),
				new ItemStack(ItemsAether.holystone_sword),
				new ItemStack(ItemsAether.zanite_sword),
				new ItemStack(ItemsAether.skyroot_sword));

		this.addCoolingFromItem(ItemsAether.irradiated_tool,
				new ItemStack(ItemsAether.arkenium_axe),
				new ItemStack(ItemsAether.arkenium_pickaxe),
				new ItemStack(ItemsAether.arkenium_shovel),
				new ItemStack(ItemsAether.zanite_axe),
				new ItemStack(ItemsAether.zanite_pickaxe),
				new ItemStack(ItemsAether.zanite_shovel),
				new ItemStack(ItemsAether.gravitite_axe),
				new ItemStack(ItemsAether.gravitite_pickaxe),
				new ItemStack(ItemsAether.gravitite_shovel),
				new ItemStack(ItemsAether.holystone_axe),
				new ItemStack(ItemsAether.holystone_pickaxe),
				new ItemStack(ItemsAether.holystone_shovel),
				new ItemStack(ItemsAether.skyroot_axe),
				new ItemStack(ItemsAether.skyroot_pickaxe),
				new ItemStack(ItemsAether.skyroot_shovel));
	}

	public static CoolerRecipes instance()
	{
		return COOLING_BASE;
	}

	public void addCoolingFromItem(Item input, ItemStack... stackList)
	{
		this.addCoolingRecipe(new ItemStack(input, 1, 32767), stackList);
	}

	public void addCoolingRecipe(ItemStack input, ItemStack... stackList)
	{
		if (this.getCoolingResult(input) != ItemStack.EMPTY)
		{
			AetherCore.LOGGER.warn("Ignored cooling recipe with conflicting input: " + input + stackList[0]);
			return;
		}

		this.coolingList.put(input, stackList);
	}

	public ItemStack getCoolingResult(ItemStack stack)
	{
		for (Entry<ItemStack, ItemStack[]> entry : this.coolingList.entrySet())
		{
			if (this.compareItemStacks(stack, entry.getKey()))
			{
				return entry.getValue()[this.rand.nextInt(entry.getValue().length)];
			}
		}
		return ItemStack.EMPTY;
	}

	public Map<ItemStack, ItemStack[]> getCoolingList()
	{
		return this.coolingList;
	}

	private boolean compareItemStacks(ItemStack stack, ItemStack toCompare)
	{
		return toCompare.getItem() == stack.getItem() && (toCompare.getMetadata() == 32767 || toCompare.getMetadata() == stack.getMetadata());
	}

}
