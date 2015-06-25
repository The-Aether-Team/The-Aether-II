package com.gildedgames.aether.client.models;

import com.gildedgames.aether.common.Aether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.blocks.natural.BlockBlueberryBush;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.BlockOrangeTree;
import com.gildedgames.aether.common.items.ItemsAether;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.LinkedHashMap;

public class ModelsAether
{
	public static void prepareBakery()
	{
		registerStateMappers();

		addVariantNames(BlocksAether.aether_grass, "aether_grass", "enchanted_aether_grass");

		addVariantNames(BlocksAether.holystone, "holystone", "mossy_holystone", "blood_moss_holystone");

		addVariantNames(BlocksAether.aercloud, "cold_aercloud", "blue_aercloud", "golden_aercloud", "green_aercloud", "storm_aercloud", "purple_aercloud");

		addVariantNames(BlocksAether.aether_log, "skyroot_log", "golden_oak_log");

		addVariantNames(BlocksAether.aether_leaves, "blue_skyroot_leaves", "dark_blue_skyroot_leaves", "green_skyroot_leaves",
				"golden_oak_leaves", "purple_crystal_leaves", "purple_fruit_leaves");

		addVariantNames(BlocksAether.blueberry_bush, "blueberry_bush_stem", "blueberry_bush_ripe");

		addVariantNames(BlocksAether.aether_flower, "white_rose", "purple_flower");
	}

	/**
	 * Registers state mappers.
	 */
	private static void registerStateMappers()
	{
		ModelLoader.setCustomStateMapper(BlocksAether.aether_leaves, new StateMap.Builder().addPropertiesToIgnore(BlockAetherLeaves.PROPERTY_CHECK_DECAY, BlockAetherLeaves.PROPERTY_DECAYABLE).build());

		ModelLoader.setCustomStateMapper(BlocksAether.aercloud, new StateMapperBase()
		{
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				LinkedHashMap mappings = Maps.newLinkedHashMap(state.getProperties());

				if (state.getValue(BlockAercloud.PROPERTY_VARIANT) != BlockAercloud.PURPLE_AERCLOUD)
				{
					mappings.remove(BlockAercloud.PROPERTY_FACING);
				}

				ResourceLocation resourceLocation = (ResourceLocation) Block.blockRegistry.getNameForObject(state.getBlock());

				return new ModelResourceLocation(resourceLocation, getPropertyString(mappings));
			}
		});

		ModelLoader.setCustomStateMapper(BlocksAether.orange_tree, new StateMapperBase()
		{
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				LinkedHashMap mappings = Maps.newLinkedHashMap(state.getProperties());

				if ((Boolean) state.getValue(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK))
				{
					if ((Integer) state.getValue(BlockOrangeTree.PROPERTY_STAGE) < 3)
					{
						mappings.remove(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK);
						mappings.remove(BlockOrangeTree.PROPERTY_STAGE);
					}
				}

				ResourceLocation resourceLocation = (ResourceLocation) Block.blockRegistry.getNameForObject(state.getBlock());

				return new ModelResourceLocation(resourceLocation, getPropertyString(mappings));
			}
		});
	}

	/**
	 * Registers block/item models.
	 */
	public static void registerItemModels()
	{
		registerBlockModel(BlocksAether.aether_dirt);

		registerBlockModel(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta(), Aether.getResource("aether_grass"));
		registerBlockModel(BlocksAether.aether_grass, BlockAetherGrass.ENCHANTED_AETHER_GRASS.getMeta(), Aether.getResource("enchanted_aether_grass"));

		registerBlockModel(BlocksAether.holystone, BlockHolystone.NORMAL_HOLYSTONE.getMeta(), Aether.getResource("holystone"));
		registerBlockModel(BlocksAether.holystone, BlockHolystone.MOSSY_HOLYSTONE.getMeta(), Aether.getResource("mossy_holystone"));
		registerBlockModel(BlocksAether.holystone, BlockHolystone.BLOOD_MOSS_HOLYSTONE.getMeta(), Aether.getResource("blood_moss_holystone"));

		registerBlockModel(BlocksAether.aercloud, BlockAercloud.COLD_AERCLOUD.getMeta(), Aether.getResource("cold_aercloud"));
		registerBlockModel(BlocksAether.aercloud, BlockAercloud.BLUE_AERCLOUD.getMeta(), Aether.getResource("blue_aercloud"));
		registerBlockModel(BlocksAether.aercloud, BlockAercloud.GOLDEN_AERCLOUD.getMeta(), Aether.getResource("golden_aercloud"));
		registerBlockModel(BlocksAether.aercloud, BlockAercloud.GREEN_AERCLOUD.getMeta(), Aether.getResource("green_aercloud"));
		registerBlockModel(BlocksAether.aercloud, BlockAercloud.STORM_AERCLOUD.getMeta(), Aether.getResource("storm_aercloud"));
		registerBlockModel(BlocksAether.aercloud, BlockAercloud.PURPLE_AERCLOUD.getMeta(), Aether.getResource("purple_aercloud"));

		registerBlockModel(BlocksAether.aether_log, BlockAetherLog.SKYROOT_LOG.getMeta(), Aether.getResource("skyroot_log"));
		registerBlockModel(BlocksAether.aether_log, BlockAetherLog.GOLDEN_OAK_LOG.getMeta(), Aether.getResource("golden_oak_log"));

		registerBlockModel(BlocksAether.aether_leaves, BlockAetherLeaves.BLUE_SKYROOT_LEAVES.getMeta(), Aether.getResource("blue_skyroot_leaves"));
		registerBlockModel(BlocksAether.aether_leaves, BlockAetherLeaves.DARK_BLUE_SKYROOT_LEAVES.getMeta(), Aether.getResource("dark_blue_skyroot_leaves"));
		registerBlockModel(BlocksAether.aether_leaves, BlockAetherLeaves.GREEN_SKYROOT_LEAVES.getMeta(), Aether.getResource("green_skyroot_leaves"));
		registerBlockModel(BlocksAether.aether_leaves, BlockAetherLeaves.GOLDEN_OAK_LEAVES.getMeta(), Aether.getResource("golden_oak_leaves"));
		registerBlockModel(BlocksAether.aether_leaves, BlockAetherLeaves.PURPLE_CRYSTAL_LEAVES.getMeta(), Aether.getResource("purple_crystal_leaves"));
		registerBlockModel(BlocksAether.aether_leaves, BlockAetherLeaves.PURPLE_FRUIT_LEAVES.getMeta(), Aether.getResource("purple_fruit_leaves"));

		registerBlockModel(BlocksAether.blueberry_bush, BlockBlueberryBush.BERRY_BUSH_STEM, Aether.getResource("blueberry_bush_stem"));
		registerBlockModel(BlocksAether.blueberry_bush, BlockBlueberryBush.BERRY_BUSH_RIPE, Aether.getResource("blueberry_bush_ripe"));

		registerBlockModel(BlocksAether.aether_flower, BlockAetherFlower.WHITE_ROSE.getMeta(), Aether.getResource("white_rose"));
		registerBlockModel(BlocksAether.aether_flower, BlockAetherFlower.PURPLE_FLOWER.getMeta(), Aether.getResource("purple_flower"));

		registerBlockModel(BlocksAether.aether_dirt);
		registerBlockModel(BlocksAether.skyroot_planks);
		registerBlockModel(BlocksAether.ambrosium_ore);
		registerBlockModel(BlocksAether.zanite_ore);
		registerBlockModel(BlocksAether.gravitite_ore);
		registerBlockModel(BlocksAether.continuum_ore);
		registerBlockModel(BlocksAether.aether_portal);
		registerBlockModel(BlocksAether.tall_aether_grass);
		registerBlockModel(BlocksAether.quicksoil);
		registerBlockModel(BlocksAether.skyroot_crafting_table);
		registerBlockModel(BlocksAether.orange_tree);
		registerBlockModel(BlocksAether.altar);
		registerBlockModel(BlocksAether.icestone);
		registerBlockModel(BlocksAether.aerogel);
		registerBlockModel(BlocksAether.zanite_block);

		registerItemModel(ItemsAether.skyroot_stick);
		registerItemModel(ItemsAether.ambrosium_shard);
		registerItemModel(ItemsAether.continuum_orb);
		registerItemModel(ItemsAether.zanite_gemstone);

		registerItemModel(ItemsAether.skyroot_pickaxe);
		registerItemModel(ItemsAether.skyroot_axe);
		registerItemModel(ItemsAether.skyroot_shovel);
		registerItemModel(ItemsAether.skyroot_sword);

		registerItemModel(ItemsAether.holystone_pickaxe);
		registerItemModel(ItemsAether.holystone_axe);
		registerItemModel(ItemsAether.holystone_shovel);
		registerItemModel(ItemsAether.holystone_sword);

		registerItemModel(ItemsAether.zanite_pickaxe);
		registerItemModel(ItemsAether.zanite_axe);
		registerItemModel(ItemsAether.zanite_shovel);
		registerItemModel(ItemsAether.zanite_sword);

		registerItemModel(ItemsAether.gravitite_pickaxe);
		registerItemModel(ItemsAether.gravitite_axe);
		registerItemModel(ItemsAether.gravitite_shovel);
		registerItemModel(ItemsAether.gravitite_sword);

		registerItemModel(ItemsAether.zanite_helmet);
		registerItemModel(ItemsAether.zanite_chestplate);
		registerItemModel(ItemsAether.zanite_leggings);
		registerItemModel(ItemsAether.zanite_boots);

		registerItemModel(ItemsAether.gravitite_helmet);
		registerItemModel(ItemsAether.gravitite_chestplate);
		registerItemModel(ItemsAether.gravitite_leggings);
		registerItemModel(ItemsAether.gravitite_boots);

		registerItemModel(ItemsAether.obsidian_helmet);
		registerItemModel(ItemsAether.obsidian_chestplate);
		registerItemModel(ItemsAether.obsidian_leggings);
		registerItemModel(ItemsAether.obsidian_boots);

		registerItemModel(ItemsAether.neptune_helmet);
		registerItemModel(ItemsAether.neptune_chestplate);
		registerItemModel(ItemsAether.neptune_leggings);
		registerItemModel(ItemsAether.neptune_boots);

		registerItemModel(ItemsAether.phoenix_helmet);
		registerItemModel(ItemsAether.phoenix_chestplate);
		registerItemModel(ItemsAether.phoenix_leggings);
		registerItemModel(ItemsAether.phoenix_boots);

		registerItemModel(ItemsAether.valkyrie_helmet);
		registerItemModel(ItemsAether.valkyrie_chestplate);
		registerItemModel(ItemsAether.valkyrie_leggings);
		registerItemModel(ItemsAether.valkyrie_boots);

		registerItemModel(ItemsAether.golden_amber);

		registerItemModel(ItemsAether.blueberry);
		registerItemModel(ItemsAether.orange);
	}

	/**
	 * Utility method for ModelBakery.addVariantName.
	 * @param block The block to add names to.
	 * @param names The list of names to add.
	 */
	private static void addVariantNames(Block block, String... names)
	{
		for (int i = 0; i < names.length; i++)
		{
			names[i] = Aether.getResource(names[i]);
		}

		ModelBakery.addVariantName(Item.getItemFromBlock(block), names);
	}

	/**
	 * Registers the appropriate model to the block. This assumes the model resource name
	 * will be the same as the block's unlocalized name.
	 * @param block The block to register to.
	 */
	private static void registerBlockModel(Block block)
	{
		ResourceLocation resourceLocation = (ResourceLocation) Block.blockRegistry.getNameForObject(block);

		registerBlockModel(block, 0, resourceLocation.toString());
	}

	/**
	 * Registers the appropriate model to the item. This assumes the model resource name
	 * will be the same as the item's unlocalized name.
	 * @param item The item to register to.
	 */
	private static void registerItemModel(Item item)
	{
		ResourceLocation resourceLocation = (ResourceLocation) Item.itemRegistry.getNameForObject(item);

		registerItemModel(item, 0, resourceLocation.toString());
	}

	/**
	 * Registers a model to the specified block and metadata.
	 * @param block The block to register to.
	 * @param meta The metadata to register to.
	 * @param modelName The path to the the model in the AETHER domain.
	 */
	private static void registerBlockModel(Block block, int meta, String modelName)
	{
		registerItemModel(Item.getItemFromBlock(block), meta, modelName);
	}

	/**
	 * Registers a model to the specified item and metadata.
	 * @param item The item to register to.
	 * @param meta The metadata to register to.
	 * @param resourcePath The path to the model in the AETHER domain.
	 */
	private static void registerItemModel(Item item, int meta, String resourcePath)
	{
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(resourcePath, "inventory");

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, modelResourceLocation);
	}
}
