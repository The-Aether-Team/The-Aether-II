package com.gildedgames.aether.common.world.labyrinth;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.properties.IItemPropertiesCapability;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;

public class LootGenerator
{

	public static ItemStack generate(Supplier<ItemStack[]> possibleLoot, ItemRarity rarity, Random rand)
	{
		List<ItemStack> stacksWithRarity = Lists.newArrayList();

		for (ItemStack stack : possibleLoot.get())
		{
			IItemPropertiesCapability properties = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

			if (properties.getRarity() == rarity)
			{
				stacksWithRarity.add(stack);
			}
		}

		return stacksWithRarity.get(rand.nextInt(stacksWithRarity.size()));
	}

	public static ItemStack generate(Supplier<ItemStack[]> possibleLoot)
	{
		int maxRoll = 0;
		int roll;

		List<ItemStack> table = Lists.newArrayList();

		for (ItemStack stack : possibleLoot.get())
		{
			table.add(stack);

			IItemPropertiesCapability properties = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

			maxRoll += properties.getRarity().getWeight();
		}

		if(table.size() == 0)
		{
			return null;
		}

		roll = (int)(Math.random() * maxRoll);

		for (ItemStack stack : table)
		{
			IItemPropertiesCapability properties = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);

			// return element if roll < weight
			if(roll < properties.getRarity().getWeight())
			{
				return stack;
			}

			// otherwise, subtract weight before moving on
			roll -= properties.getRarity().getWeight();
		}

		return null;
	}

}
