package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesAether
{
	public static void preInit()
	{
		registerNaturalRecipes();
		registerToolRecipes();
		registerArmorRecipes();
		registerUtilityRecipes();
	}

	private static void registerNaturalRecipes()
	{
		registerShapelessRecipe(new ItemStack(BlocksAether.skyroot_planks, 4), new ItemStack(BlocksAether.aether_log, 1));

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_stick, 4), "X", "X",
				'X', new ItemStack(BlocksAether.skyroot_planks, 1));
	}

	private static void registerToolRecipes()
	{
		ItemStack skyrootStick = new ItemStack(ItemsAether.skyroot_stick);

		ItemStack skyroot = new ItemStack(BlocksAether.skyroot_planks);
		ItemStack holystone = new ItemStack(BlocksAether.holystone);
		ItemStack zanite = new ItemStack(ItemsAether.zanite_gemstone);

		// Skyroot Tools

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_axe, 1), "XX ", "XY ", " Y ",
				'X', skyroot, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_pickaxe, 1), "XXX", " Y ", " Y ",
				'X', skyroot, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_shovel, 1), "X", "Y", "Y",
				'X', skyroot, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_sword, 1), "X", "X", "Y",
				'X', skyroot, 'Y', skyrootStick);

		// Holystone Tools

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_axe, 1), "XX ", "XY ", " Y ",
				'X', holystone, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_pickaxe, 1), "XXX", " Y ", " Y ",
				'X', holystone, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_shovel, 1), "X", "Y", "Y",
				'X', holystone, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_sword, 1), "X", "X", "Y",
				'X', holystone, 'Y', skyrootStick);

		// Zanite Tools

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_axe, 1), "XX ", "XY ", " Y ",
				'X', zanite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_pickaxe, 1), "XXX", " Y ", " Y ",
				'X', zanite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_shovel, 1), "X", "Y", "Y",
				'X', zanite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_sword, 1), "X", "X", "Y",
				'X', zanite, 'Y', skyrootStick);

		// TODO: Gravitite tools!
	}

	private static void registerArmorRecipes()
	{
		ItemStack zanite = new ItemStack(ItemsAether.zanite_gemstone);

		// Zanite Armor

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_helmet, 1), "XXX", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_chestplate, 1), "X X", "XXX", "XXX",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_leggings, 1), "XXX", "X X", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_boots, 1), "X X", "X X",
				'X', zanite);

		// TODO: Other armor types.
	}

	private static void registerUtilityRecipes()
	{
		ItemStack zanite = new ItemStack(ItemsAether.zanite_gemstone);

		registerShapedRecipe(new ItemStack(BlocksAether.zanite_block, 1), "XXX", "XXX", "XXX",
				'X', zanite);
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
