package com.gildedgames.aether.common.recipes;

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

	private final Map<Map<ItemStack, ItemStack>, ItemStack[]> coolingList = Maps.newHashMap();

	private final Map<Map<ItemStack, ItemStack>, ItemStack> outputList = Maps.newHashMap();

	private final Random rand = new Random();

	public static CoolerRecipes instance()
	{
		return COOLING_BASE;
	}

	public void addCoolingFromItem(Item input, Item inputSecondary, ItemStack outputSecondary, ItemStack... stackList)
	{
		Map<ItemStack, ItemStack> map = Maps.newHashMap();
		map.put(new ItemStack(input, 1, 32767), new ItemStack(inputSecondary, 1, 32767));

		this.addCoolingRecipe(map, outputSecondary, stackList);
	}

	public void addCoolingRecipe(Map<ItemStack, ItemStack> inputMap, ItemStack outputSecondary, ItemStack... stackList)
	{
		for (Entry<ItemStack, ItemStack> entry : inputMap.entrySet())
		{
			if (this.getCoolingResult(entry.getKey(), entry.getValue()) != ItemStack.EMPTY)
			{
				AetherCore.LOGGER.warn("Ignored cooling recipe with conflicting input: " + inputMap + stackList[0]);
				return;
			}
		}

		this.outputList.put(inputMap, outputSecondary);
		this.coolingList.put(inputMap, stackList);
	}

	public ItemStack getPrimaryOutput(ItemStack stack)
	{
		for (Entry<Map<ItemStack, ItemStack>, ItemStack[]> entry : this.coolingList.entrySet())
		{
			for (Entry<ItemStack, ItemStack> entryInput : entry.getKey().entrySet())
			{
				if (this.compareItemStacks(stack, entryInput.getKey()))
				{
					return entry.getValue()[this.rand.nextInt(entry.getValue().length)];
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getSecondaryOutput(ItemStack stack)
	{
		for (Entry<Map<ItemStack, ItemStack>, ItemStack> entry : this.outputList.entrySet())
		{
			for (Entry<ItemStack, ItemStack> entryInput : entry.getKey().entrySet())
			{
				if (this.compareItemStacks(stack, entryInput.getKey()))
				{
					return entry.getValue();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getSecondaryInputForSlot(ItemStack stack)
	{
		for (Entry<Map<ItemStack, ItemStack>, ItemStack[]> entry : this.coolingList.entrySet())
		{
			for (Entry<ItemStack, ItemStack> entryInput : entry.getKey().entrySet())
			{
				if (this.compareItemStacks(stack, entryInput.getValue()))
				{
					return entryInput.getValue();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getSecondaryInput(ItemStack stack)
	{
		for (Entry<Map<ItemStack, ItemStack>, ItemStack[]> entry : this.coolingList.entrySet())
		{
			for (Entry<ItemStack, ItemStack> entryInput : entry.getKey().entrySet())
			{
				if (this.compareItemStacks(stack, entryInput.getKey()))
				{
					return entryInput.getValue();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getCoolingResult(ItemStack stack, ItemStack stackSecondary)
	{
		for (Entry<Map<ItemStack, ItemStack>, ItemStack[]> entry : this.coolingList.entrySet())
		{
			for (Entry<ItemStack, ItemStack> entryInput : entry.getKey().entrySet())
			{
				if (this.compareItemStacks(stack, entryInput.getKey()))
				{
					if (this.compareItemStacks(stackSecondary, entryInput.getValue()))
					{
						return entry.getValue()[this.rand.nextInt(entry.getValue().length)];
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public Map<Map<ItemStack, ItemStack>, ItemStack[]> getCoolingList()
	{
		return this.coolingList;
	}

	private boolean compareItemStacks(ItemStack stack, ItemStack toCompare)
	{
		return toCompare.getItem() == stack.getItem() && (toCompare.getMetadata() == 32767 || toCompare.getMetadata() == stack.getMetadata());
	}

}
