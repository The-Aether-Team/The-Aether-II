package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipe;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPlanks;
import com.gildedgames.aether.common.blocks.containers.BlockAetherCraftingTable;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.tools.ItemSkyrootTool;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.recipes.simple.OreDictionaryRequirement;
import com.gildedgames.aether.common.recipes.simple.SimpleRecipe;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class RecipesAether
{

	private static int nextId;

	private static boolean hasInit;

	private RecipesAether()
	{

	}

	public static void preInit()
	{
		if (hasInit)
		{
			return;
		}

		/*add(new ItemStack(BlocksAether.aether_planks, 4, BlockAetherPlanks.SKYROOT.getMeta()), new ItemStack(BlocksAether.skyroot_log));
		add(new ItemStack(BlocksAether.aether_crafting_table, 1, BlockAetherCraftingTable.SKYROOT.getMeta()), new ItemStack(BlocksAether.aether_planks, 4, BlockAetherPlanks.SKYROOT.getMeta()));
		add(new ItemStack(ItemsAether.skyroot_stick, 4), new ItemStack(BlocksAether.aether_planks, 2, BlockAetherPlanks.SKYROOT.getMeta()));

		*//** Cloud Parachutes **//*
		add(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.COLD.ordinal()), new ItemStack(BlocksAether.aercloud, 3, BlockAercloud.COLD_AERCLOUD.getMeta()), new ItemStack(Items.STRING));
		add(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.BLUE.ordinal()), new ItemStack(BlocksAether.aercloud, 3, BlockAercloud.BLUE_AERCLOUD.getMeta()), new ItemStack(Items.STRING));
		add(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.GOLDEN.ordinal()), new ItemStack(BlocksAether.aercloud, 3, BlockAercloud.GOLDEN_AERCLOUD.getMeta()), new ItemStack(Items.STRING));
		add(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.PURPLE.ordinal()), new ItemStack(BlocksAether.aercloud, 3, BlockAercloud.PURPLE_AERCLOUD.getMeta()), new ItemStack(Items.STRING));
		add(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.GREEN.ordinal()), new ItemStack(BlocksAether.aercloud, 3, BlockAercloud.GREEN_AERCLOUD.getMeta()), new ItemStack(Items.STRING));

		*//** Saddle **//*
		add(new ItemStack(Items.SADDLE), new ItemStack(Items.LEATHER), new ItemStack(Items.STRING));

		add(new ItemStack(ItemsAether.skyroot_bed), new ItemStack(Blocks.WOOL, 3, OreDictionary.WILDCARD_VALUE), new ItemStack(BlocksAether.aether_planks, 3));
		add(new ItemStack(BlocksAether.skyroot_chest), new ItemStack(BlocksAether.aether_planks, 8));
		add(new ItemStack(BlocksAether.altar), new ItemStack(BlocksAether.holystone, 4, OreDictionary.WILDCARD_VALUE), new ItemStack(ItemsAether.zanite_gemstone), new ItemStack(ItemsAether.arkenium, 2));
		add(new ItemStack(BlocksAether.frostpine_cooler), new ItemStack(BlocksAether.holystone, 2, OreDictionary.WILDCARD_VALUE), new ItemStack(ItemsAether.arkenium, 2), new ItemStack(ItemsAether.icestone), new ItemStack(BlocksAether.aether_planks, 3));
		add(new ItemStack(ItemsAether.arkenium_strip, 2), new ItemStack(ItemsAether.arkenium));

		*//** Framed Glass **//*
		add(new ItemStack(BlocksAether.skyroot_frame_quicksoil_glass), new ItemStack(BlocksAether.quicksoil_glass), new ItemStack(ItemsAether.skyroot_stick));
		add(new ItemStack(BlocksAether.skyroot_frame_scatterglass), new ItemStack(BlocksAether.scatterglass), new ItemStack(ItemsAether.skyroot_stick));
		add(new ItemStack(BlocksAether.arkenium_frame_quicksoil_glass), new ItemStack(BlocksAether.quicksoil_glass), new ItemStack(ItemsAether.arkenium_strip));
		add(new ItemStack(BlocksAether.arkenium_frame_scatterglass), new ItemStack(BlocksAether.scatterglass), new ItemStack(ItemsAether.arkenium_strip));

		*//** Doors **//*
		add(new ItemStack(ItemsAether.skyroot_door, 3), new ItemStack(BlocksAether.aether_planks, 6));
		add(new ItemStack(ItemsAether.arkenium_door, 3), new ItemStack(ItemsAether.arkenium, 6));
		add(new ItemStack(BlocksAether.skyroot_trapdoor, 2), new ItemStack(BlocksAether.aether_planks, 6));

		*//** Ladders **//*
		add(new ItemStack(BlocksAether.skyroot_ladder, 3), new ItemStack(ItemsAether.skyroot_stick, 7));

		*//** Pressure Plates **//*
		add(new ItemStack(BlocksAether.skyroot_pressure_plate), new ItemStack(BlocksAether.aether_planks, 2));
		add(new ItemStack(BlocksAether.holystone_pressure_plate), new ItemStack(BlocksAether.holystone, 2, OreDictionary.WILDCARD_VALUE));

		*//** Buttons **//*
		add(new ItemStack(BlocksAether.skyroot_button), new ItemStack(BlocksAether.aether_planks));
		add(new ItemStack(BlocksAether.holystone_button), new ItemStack(BlocksAether.holystone, 1, OreDictionary.WILDCARD_VALUE));

		*//** Dart Shooters **//*
		add(new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()), new ItemStack(BlocksAether.aether_planks, 2), new ItemStack(ItemsAether.golden_amber));
		add(new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.POISON.ordinal()), new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()), new ItemStack(ItemsAether.skyroot_poison_bucket));

		*//** Darts **//*
		add(new ItemStack(ItemsAether.dart, 8, ItemDartType.GOLDEN.ordinal()), new ItemStack(ItemsAether.golden_amber), new ItemStack(ItemsAether.skyroot_stick), new ItemStack(Items.FEATHER));
		add(new ItemStack(ItemsAether.dart, 8, ItemDartType.POISON.ordinal()), new ItemStack(ItemsAether.dart, 8, ItemDartType.GOLDEN.ordinal()), new ItemStack(ItemsAether.skyroot_poison_bucket));

		add(new ItemStack(ItemsAether.skyroot_bucket), new ItemStack(BlocksAether.aether_planks, 3));

		*//** Blocks **//*
		add(new ItemStack(BlocksAether.zanite_block), new ItemStack(ItemsAether.zanite_gemstone, 9));
		add(new ItemStack(BlocksAether.gravitite_block), new ItemStack(ItemsAether.gravitite_plate, 9));

		add(new ItemStack(ItemsAether.zanite_gemstone, 9), new ItemStack(BlocksAether.zanite_block));
		add(new ItemStack(ItemsAether.gravitite_plate, 9), new ItemStack(BlocksAether.gravitite_block));

		add(new ItemStack(BlocksAether.holystone_brick, 1), new ItemStack(BlocksAether.holystone, 1, OreDictionary.WILDCARD_VALUE));
		add(new ItemStack(BlocksAether.icestone_bricks, 1), new ItemStack(ItemsAether.icestone));*/

		List<ISimpleRecipe> recipes = Lists.newArrayList();

		top: for (IRecipe recipe : CraftingManager.getInstance().getRecipeList())
		{
			if (recipe instanceof ShapedRecipes)
			{
				ShapedRecipes shaped = (ShapedRecipes)recipe;

				List<ItemStack> req = Lists.newArrayList();

				outer: for (ItemStack stack : shaped.recipeItems)
				{
					if (stack != null)
					{
						for (ItemStack reqStack : req)
						{
							if (RecipeUtil.areEqual(reqStack, stack))
							{
								reqStack.stackSize += stack.stackSize;
								continue outer;
							}
						}

						req.add(stack);
					}
				}

				SimpleRecipe simpleRecipe = new SimpleRecipe(shaped.getRecipeOutput(), req.toArray(new ItemStack[req.size()]));

				for (ISimpleRecipe r : recipes)
				{
					if (r.equals(simpleRecipe))
					{
						continue top;
					}
				}

				recipes.add(simpleRecipe);

				System.out.println(simpleRecipe.getResult());

				AetherAPI.crafting().registerRecipe(nextId++, simpleRecipe);
			}
			else if (recipe instanceof ShapelessRecipes)
			{
				ShapelessRecipes shapeless = (ShapelessRecipes) recipe;

				List<ItemStack> req = Lists.newArrayList();

				outer: for (ItemStack stack : shapeless.recipeItems)
				{
					if (stack != null)
					{
						for (ItemStack reqStack : req)
						{
							if (RecipeUtil.areEqual(reqStack, stack))
							{
								reqStack.stackSize += stack.stackSize;
								continue outer;
							}
						}

						req.add(stack);
					}
				}

				SimpleRecipe simpleRecipe = new SimpleRecipe(shapeless.getRecipeOutput(), req.toArray(new ItemStack[req.size()]));

				for (ISimpleRecipe r : recipes)
				{
					if (r.equals(simpleRecipe))
					{
						continue top;
					}
				}

				recipes.add(simpleRecipe);

				AetherAPI.crafting().registerRecipe(nextId++, simpleRecipe);
			}
		}

		AetherAPI.crafting().finalizeRecipes();

		hasInit = true;
	}

	private static void add(ItemStack result, Object... required)
	{
		AetherAPI.crafting().registerRecipe(nextId++, new SimpleRecipe(result, required));
	}

}
