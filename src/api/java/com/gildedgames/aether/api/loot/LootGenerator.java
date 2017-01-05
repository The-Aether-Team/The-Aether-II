package com.gildedgames.aether.api.loot;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.properties.ItemRarity;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;

public class LootGenerator
{

	public static ItemStack generate(LootPool possibleLoot, Random rand)
	{
		return LootGenerator.generate(possibleLoot, null, rand);
	}

	public static ItemStack generate(LootPool possibleLoot, ItemRarity rarity, Random rand)
	{
		List<Loot> stacksWithRarity;

		if (rarity == null)
		{
			stacksWithRarity = Lists.newArrayList(possibleLoot.getPossibleLoot());
		}
		else
		{
			stacksWithRarity = Lists.newArrayList();

			for (Loot loot : possibleLoot.getPossibleLoot())
			{
				ItemStack stack = loot.getCloningSource();

				if (stack != null)
				{
					// TODO: Port
//					IItemPropertiesCapability properties = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);
//
//					if (properties.getRarity() == rarity)
//					{
//						stacksWithRarity.add(loot);
//					}
				}
			}
		}

		ItemStack result = null;

		while (result == null)
		{
			result = stacksWithRarity.get(rand.nextInt(stacksWithRarity.size())).create(rand);
		}

		return result;
	}

}
