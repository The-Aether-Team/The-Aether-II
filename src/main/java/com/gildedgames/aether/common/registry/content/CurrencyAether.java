package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.recipes.IIndexableRecipe;
import com.gildedgames.aether.api.recipes.altar.IAltarRecipe;
import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.api.shop.ICurrencyRegistry;
import com.gildedgames.aether.api.shop.IShopCurrency;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.recipes.simple.OreDictionaryRequirement;
import com.gildedgames.aether.common.shop.ShopCurrencyGilt;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collection;

public class CurrencyAether
{
	public static int GILT = 1, GILTAE = 100, GILTAEN = 10000, GILTAENI = 1000000;

	public static void onServerAboutToStart()
	{
		AetherAPI.content().currency().clearRegistrations();

		GiltBuilder builder = new GiltBuilder();

		ICurrencyRegistry c = AetherAPI.content().currency();
		c.registerValue(new ItemStack(BlocksAether.aether_dirt, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.aether_grass, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.holystone, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.golden_oak_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.blue_dark_skyroot_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.blue_light_skyroot_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(),
				ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.blue_skyroot_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.dark_blue_dark_skyroot_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(),
				ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.dark_blue_light_skyroot_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(),
				ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.dark_blue_skyroot_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.green_dark_skyroot_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(),
				ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.green_light_skyroot_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(),
				ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.green_skyroot_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.therawood_leaves, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(80).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.therawood_log, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(80).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.thera_dirt, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(80).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.thera_grass, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(80).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.therastone_brick, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(80).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.scatterglass, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(3).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.crude_scatterglass, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(3).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.ferrosite_sand, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(2).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.quicksoil, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(2).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.aercloud, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(5).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.ferrosite, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(3).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.aether_sapling, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(5).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.aether_flower, 1, BlockAetherFlower.WHITE_ROSE.getMeta()), builder.gilt(5).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.aether_flower, 1, BlockAetherFlower.PURPLE_FLOWER.getMeta()), builder.gilt(5).flush(),
				ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.aether_flower, 1, BlockAetherFlower.BURSTBLOSSOM.getMeta()), builder.gilt(5).flush(),
				ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.aether_flower, 1, BlockAetherFlower.AECHOR_SPROUT.getMeta()), builder.gilt(15).flush(),
				ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.plumproot, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(10).flush(),
				ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.orange_tree, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(30).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.blueberry_bush, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(30).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.valkyrie_grass, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(5).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.valkyrie_wings, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(5).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.skyroot_log, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(3).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.ambrosium_shard, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(40).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.zanite_gemstone, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(90).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.icestone, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(66).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.arkenium_ore, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(20).giltae(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.arkenium, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(40).giltae(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.arkenium_strip, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(35).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.gravitite_ore, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(80).giltae(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.gravitite_plate, 1, OreDictionary.WILDCARD_VALUE), builder.giltae(2).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.cloudwool_block, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(4).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.moa_feather, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.cockatrice_feather, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(3).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.golden_amber, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(2).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.taegore_hide, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(10).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.burrukai_pelt, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(15).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.brettl_cane, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(5).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.brettl_grass, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(20).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.swet_gel, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(3).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.swet_sugar, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(6).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.blueberries, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(2).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.wyndberry, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(5).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.raw_taegore_meat, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(25).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.burrukai_rib_cut, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(45).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.kirrid_loin, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(15).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.moa_egg, 1, OreDictionary.WILDCARD_VALUE), builder.giltae(1).flush(), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(ItemsAether.aechor_petal, 1, OreDictionary.WILDCARD_VALUE), builder.gilt(7).flush(), ShopCurrencyGilt.class);

		for (Block block : BlocksAether.getRegisteredBlocks())
		{
			registerBlockCurrency(block, builder, c, ShopCurrencyGilt.class);
		}

		for (Item item : ItemsAether.getRegisteredItems())
		{
			registerItemCurrency(item, builder, c, ShopCurrencyGilt.class);
		}

		for (ISimpleRecipe recipe : AetherAPI.content().masonry().getAllRecipes())
		{
			if (recipe.getResult() != null && !c.hasValue(recipe.getResult(), ShopCurrencyGilt.class))
			{
				double value = 0;

				for (Object obj : recipe.getRequired())
				{
					if (obj instanceof ItemStack)
					{
						ItemStack stack = (ItemStack) obj;

						value += c.getValue(stack, ShopCurrencyGilt.class);
					}
					else if (obj instanceof OreDictionaryRequirement)
					{
						//TODO: Get item stacks from Ore requirement, get currency value, add. Can't right now.
					}
				}

				c.registerValue(recipe.getResult(), value, ShopCurrencyGilt.class);
			}
		}

		c.registerValue(new ItemStack(ItemsAether.secret_skyroot_door, 1, OreDictionary.WILDCARD_VALUE),
				c.getValue(new ItemStack(ItemsAether.skyroot_door, 1, OreDictionary.WILDCARD_VALUE), ShopCurrencyGilt.class), ShopCurrencyGilt.class);
		c.registerValue(new ItemStack(BlocksAether.secret_skyroot_trapdoor, 1, OreDictionary.WILDCARD_VALUE),
				c.getValue(new ItemStack(BlocksAether.skyroot_trapdoor, 1, OreDictionary.WILDCARD_VALUE), ShopCurrencyGilt.class), ShopCurrencyGilt.class);
	}

	private static void registerBlockCurrency(Block block, GiltBuilder builder, ICurrencyRegistry c, Class<? extends IShopCurrency> currency)
	{
		boolean doneDefaultMeta = false;

		for (IBlockState state : block.getBlockState().getValidStates())
		{
			int meta = block.getMetaFromState(state);

			if (meta == 0)
			{
				doneDefaultMeta = true;
			}

			ItemStack stack = new ItemStack(block, 1, meta);

			registerCurrency(null, stack, builder, c, currency);
		}

		if (!doneDefaultMeta)
		{
			ItemStack stack = new ItemStack(block, 1, 0);

			registerCurrency(null, stack, builder, c, currency);
		}
	}

	private static void registerItemCurrency(Item item, GiltBuilder builder, ICurrencyRegistry c, Class<? extends IShopCurrency> currency)
	{
		ItemStack stack = new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);

		registerCurrency(null, stack, builder, c, currency);
	}

	private static void registerCurrency(ItemStack recursiveParent, ItemStack stack, GiltBuilder builder, ICurrencyRegistry c,
			Class<? extends IShopCurrency> currency)
	{
		Collection<IIndexableRecipe> recipes = AetherAPI.content().craftable().getRecipesContainingResult(stack);

		double highestValue = Double.MIN_VALUE;

		for (IIndexableRecipe recipe : recipes)
		{
			ItemStack result = recipe.getCraftingResult();

			for (Ingredient ingredient : recipe.getCraftingMatrix())
			{
				double lowestValue = Double.MAX_VALUE;

				for (ItemStack s : ingredient.getMatchingStacks())
				{
					if (!c.hasValue(s, currency) && (recursiveParent == null || ItemHelper.getHashForItemStack(recursiveParent) != ItemHelper
							.getHashForItemStack(s)))
					{
						if (recursiveParent == null)
						{
							registerCurrency(s, s, new GiltBuilder(), c, currency);
						}
						else
						{
							registerCurrency(recursiveParent, s, new GiltBuilder(), c, currency);
						}
					}

					double value = c.getValue(s, ShopCurrencyGilt.class);

					if (value > 0 && value < lowestValue)
					{
						lowestValue = value;
					}
				}

				if (lowestValue == Double.MAX_VALUE)
				{
					lowestValue = 0;
				}

				builder.value += lowestValue;
			}

			double value = builder.flush() / (double) result.getCount();

			if (value > highestValue)
			{
				highestValue = value;
			}
		}

		IAltarRecipe recipe = AetherAPI.content().altar().getMatchingRecipeFromOutput(stack);

		if (recipe != null && recipe.getInput() != null)
		{
			ItemStack s = recipe.getInput();

			if (!c.hasValue(s, currency) && (recursiveParent == null || ItemHelper.getHashForItemStack(recursiveParent) != ItemHelper.getHashForItemStack(s)))
			{
				if (recursiveParent == null)
				{
					registerCurrency(s, s, new GiltBuilder(), c, currency);
				}
				else
				{
					registerCurrency(recursiveParent, s, new GiltBuilder(), c, currency);
				}
			}

			double value = c.getValue(s, currency) + c.getValue(new ItemStack(ItemsAether.ambrosium_shard, 1, recipe.getAmbrosiumCost(s)), currency);

			if (value > highestValue)
			{
				highestValue = value;
			}
		}

		if (highestValue == Double.MIN_VALUE)
		{
			highestValue = 0;
		}

		if (highestValue > 0)
		{
			c.registerValue(stack, highestValue, currency);
		}

		ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(stack);

		if (!smeltingResult.isEmpty() && !c.hasValue(smeltingResult, currency))
		{
			c.registerValue(smeltingResult, (c.getValue(stack, currency) + 20) / (double) smeltingResult.getCount(), currency);
		}
	}

	private static class GiltBuilder
	{
		private double value;

		private GiltBuilder()
		{

		}

		public GiltBuilder gilt(int count)
		{
			this.value += count * GILT;

			return this;
		}

		public GiltBuilder giltae(int count)
		{
			this.value += count * GILTAE;

			return this;
		}

		public GiltBuilder giltaen(int count)
		{
			this.value += count * GILTAEN;

			return this;
		}

		public GiltBuilder giltaeni(int count)
		{
			this.value += count * GILTAENI;

			return this;
		}

		public double flush()
		{
			double value = this.value;

			this.value = 0;

			return value;
		}

	}
}
