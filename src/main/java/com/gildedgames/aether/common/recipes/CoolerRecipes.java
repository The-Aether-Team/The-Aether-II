package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.Maps;
import net.minecraft.init.Blocks;
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

	private CoolerRecipes()
	{
		this.addCoolingFromItem(ItemsAether.irradiated_armor, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				new ItemStack(ItemsAether.arkenium_helmet),
				new ItemStack(ItemsAether.arkenium_chestplate),
				new ItemStack(ItemsAether.arkenium_leggings),
				new ItemStack(ItemsAether.arkenium_boots),
				new ItemStack(ItemsAether.arkenium_gloves),
				new ItemStack(ItemsAether.gravitite_helmet),
				new ItemStack(ItemsAether.gravitite_chestplate),
				new ItemStack(ItemsAether.gravitite_leggings),
				new ItemStack(ItemsAether.gravitite_boots),
				new ItemStack(ItemsAether.gravitite_gloves),
				new ItemStack(ItemsAether.zanite_helmet),
				new ItemStack(ItemsAether.zanite_chestplate),
				new ItemStack(ItemsAether.zanite_leggings),
				new ItemStack(ItemsAether.zanite_boots),
				new ItemStack(ItemsAether.zanite_gloves));

		this.addCoolingFromItem(ItemsAether.irradiated_charm, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				new ItemStack(ItemsAether.irradiated_dust));

		this.addCoolingFromItem(ItemsAether.irradiated_chunk, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				new ItemStack(ItemsAether.irradiated_dust));

		this.addCoolingFromItem(ItemsAether.irradiated_neckwear, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				new ItemStack(ItemsAether.irradiated_dust));

		this.addCoolingFromItem(ItemsAether.irradiated_ring, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				new ItemStack(ItemsAether.irradiated_dust));

		this.addCoolingFromItem(ItemsAether.irradiated_sword, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				new ItemStack(ItemsAether.arkenium_sword),
				new ItemStack(ItemsAether.gravitite_sword),
				new ItemStack(ItemsAether.holystone_sword),
				new ItemStack(ItemsAether.zanite_sword),
				new ItemStack(ItemsAether.skyroot_sword));

		this.addCoolingFromItem(ItemsAether.irradiated_tool, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
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

		this.addCoolingFromItem(ItemsAether.water_vial, ItemsAether.valkyrie_wings, null,
				new ItemStack(ItemsAether.valkyrie_tea));

		this.addCoolingFromItem(ItemsAether.skyroot_water_bucket, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.skyroot_bucket),
				new ItemStack(BlocksAether.highlands_ice));
	}

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
