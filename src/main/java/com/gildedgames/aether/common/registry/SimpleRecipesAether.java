package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipe;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.BlockAngelstoneBrick;
import com.gildedgames.aether.common.blocks.construction.BlockFadedHolystoneBrick;
import com.gildedgames.aether.common.blocks.construction.BlockHolystoneBrick;
import com.gildedgames.aether.common.blocks.construction.BlockSkyrootPlanks;
import com.gildedgames.aether.common.recipes.simple.OreDictionaryRequirement;
import com.gildedgames.aether.common.recipes.simple.SimpleRecipe;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

public class SimpleRecipesAether
{

	private static int nextId, nextIdMasonry;

	private static boolean hasInit;

	private SimpleRecipesAether()
	{

	}
	
	private static void registerMasonryRecipes()
	{
		addMasonry(new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.BASE_BRICKS.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.BASE_PILLAR.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.CAPSTONE_BRICKS.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.CAPSTONE_PILLAR.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.FLAGSTONES.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.HEADSTONE.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.KEYSTONE.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.holystone_pillar), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));

		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick, 1, BlockFadedHolystoneBrick.NORMAL.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick, 1, BlockFadedHolystoneBrick.BASE_BRICKS.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick, 1, BlockFadedHolystoneBrick.BASE_PILLAR.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick, 1, BlockFadedHolystoneBrick.CAPSTONE_BRICKS.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick, 1, BlockFadedHolystoneBrick.CAPSTONE_PILLAR.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick, 1, BlockFadedHolystoneBrick.FLAGSTONES.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick, 1, BlockFadedHolystoneBrick.HEADSTONE.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_brick, 1, BlockFadedHolystoneBrick.KEYSTONE.getMeta()), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.faded_holystone_pillar), new ItemStack(BlocksAether.holystone_brick, 1, BlockHolystoneBrick.NORMAL.getMeta()));

		addMasonry(new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.BASE_BRICKS.getMeta()), new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.BASE_PILLAR.getMeta()), new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.CAPSTONE_BRICKS.getMeta()), new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.CAPSTONE_PILLAR.getMeta()), new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.FLAGSTONES.getMeta()), new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.KEYSTONE.getMeta()), new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.angelstone_pillar), new ItemStack(BlocksAether.angelstone_brick, 1, BlockAngelstoneBrick.NORMAL.getMeta()));

		addMasonry(new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.BASE_PLANKS.getMeta()), new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.BASE_BEAM.getMeta()), new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.TOP_PLANKS.getMeta()), new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.TOP_BEAM.getMeta()), new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.FLOORBOARDS.getMeta()), new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.HIGHLIGHT.getMeta()), new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.TILES.getMeta()), new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.TILES_SMALL.getMeta()), new ItemStack(BlocksAether.skyroot_planks, 1, BlockSkyrootPlanks.NORMAL.getMeta()));
		addMasonry(new ItemStack(BlocksAether.skyroot_beam), new ItemStack(BlocksAether.skyroot_planks, 1, BlockAngelstoneBrick.NORMAL.getMeta()));

		addMasonry(new ItemStack(BlocksAether.skyroot_frame_quicksoil_glass), new ItemStack(BlocksAether.quicksoil_glass));
		addMasonry(new ItemStack(BlocksAether.skyroot_frame_scatterglass), new ItemStack(BlocksAether.scatterglass));

		addMasonry(new ItemStack(BlocksAether.arkenium_frame_quicksoil_glass), new ItemStack(BlocksAether.quicksoil_glass));
		addMasonry(new ItemStack(BlocksAether.arkenium_frame_scatterglass), new ItemStack(BlocksAether.scatterglass));
	}

	public static void postInit()
	{
		if (hasInit)
		{
			return;
		}

		registerMasonryRecipes();
		
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

				SimpleRecipe simpleRecipe = new SimpleRecipe(shaped.getRecipeOutput(), req.toArray());

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

				SimpleRecipe simpleRecipe = new SimpleRecipe(shapeless.getRecipeOutput(), req.toArray());

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
			else if (recipe instanceof ShapedOreRecipe)
			{
				ShapedOreRecipe shaped = (ShapedOreRecipe) recipe;

				List<Object> req = Lists.newArrayList();

				outer: for (Object obj : shaped.getInput())
				{
					if (obj != null)
					{
						for (Object reqObj : req)
						{
							if (obj instanceof List)
							{
								List list = (List)obj;

								if (list.size() > 0 && list.get(0) instanceof ItemStack)
								{
									int[] oreIDs = OreDictionary.getOreIDs((ItemStack) list.get(0));

									for (int id : oreIDs)
									{
										if (RecipeUtil.areEqual(reqObj, OreDictionary.getOreName(id)))
										{
											if (reqObj instanceof ItemStack)
											{
												ItemStack reqStack = (ItemStack)reqObj;
												reqStack.stackSize++;
											}
											else if (reqObj instanceof OreDictionaryRequirement)
											{
												OreDictionaryRequirement oreReq = (OreDictionaryRequirement)reqObj;

												oreReq.addCount(1);
											}

											continue outer;
										}
									}
								}
							}

							if (RecipeUtil.areEqual(reqObj, obj))
							{
								if (reqObj instanceof ItemStack)
								{
									ItemStack reqStack = (ItemStack)reqObj;
									reqStack.stackSize++;
								}
								else if (reqObj instanceof OreDictionaryRequirement)
								{
									OreDictionaryRequirement oreReq = (OreDictionaryRequirement)reqObj;

									oreReq.addCount(1);
								}

								continue outer;
							}
						}

						if (obj instanceof List)
						{
							List list = (List)obj;

							if (list.size() > 0 && list.get(0) instanceof ItemStack)
							{
								int[] oreIDs = OreDictionary.getOreIDs((ItemStack) list.get(0));

								for (int id : oreIDs)
								{
									req.add(new OreDictionaryRequirement(OreDictionary.getOreName(id), 1));
								}
							}
						}
						else
						{
							req.add(obj);
						}
					}
				}

				SimpleRecipe simpleRecipe = new SimpleRecipe(shaped.getRecipeOutput(), req.toArray(new Object[req.size()]));

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
		AetherAPI.masonry().finalizeRecipes();

		hasInit = true;
	}

	private static void add(ItemStack result, Object... required)
	{
		AetherAPI.crafting().registerRecipe(nextId++, new SimpleRecipe(result, required));
	}

	private static void addMasonry(ItemStack result, Object... required)
	{
		AetherAPI.masonry().registerRecipe(nextIdMasonry++, new SimpleRecipe(result, required));
	}

}
