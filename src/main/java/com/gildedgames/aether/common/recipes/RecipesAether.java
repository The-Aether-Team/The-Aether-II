package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesAether
{
	public static void preInit()
	{
		registerShapelessRecipe(new ItemStack(BlocksAether.skyroot_planks, 4), new ItemStack(BlocksAether.aether_log, 1));

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_stick, 4), "X", "X",
				'X', new ItemStack(BlocksAether.skyroot_planks, 1));
	}

	private static void registerShapelessRecipe(ItemStack output, Object... stacks)
	{
		GameRegistry.addShapelessRecipe(output, stacks);
	}

	private static void registerShapedRecipe(ItemStack output, Object... params)
	{
		GameRegistry.addShapedRecipe(output, params);
	}
}
