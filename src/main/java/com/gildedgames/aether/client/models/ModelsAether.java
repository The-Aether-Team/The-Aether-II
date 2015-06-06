package com.gildedgames.aether.client.models;

import com.gildedgames.aether.common.Aether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.blocks.natural.BlockBlueberryBush;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
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
	public static void prepareModelLoader()
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
	}

	public static void registerModels()
	{
		registerBlockModels();
		registerItemModels();
	}

	private static void registerBlockModels()
	{
		registerBlockModel(BlocksAether.aether_dirt, 0, "aether_dirt");

		registerBlockModelVariant(BlocksAether.aether_grass, BlockAetherGrass.AETHER_GRASS.getMeta(), "aether_grass");
		registerBlockModelVariant(BlocksAether.aether_grass, BlockAetherGrass.ENCHANTED_AETHER_GRASS.getMeta(), "enchanted_aether_grass");

		registerBlockModelVariant(BlocksAether.holystone, BlockHolystone.NORMAL_HOLYSTONE.getMeta(), "holystone");
		registerBlockModelVariant(BlocksAether.holystone, BlockHolystone.MOSSY_HOLYSTONE.getMeta(), "mossy_holystone");
		registerBlockModelVariant(BlocksAether.holystone, BlockHolystone.BLOOD_MOSS_HOLYSTONE.getMeta(), "blood_moss_holystone");

		registerBlockModelVariant(BlocksAether.aercloud, BlockAercloud.COLD_AERCLOUD.getMeta(), "cold_aercloud");
		registerBlockModelVariant(BlocksAether.aercloud, BlockAercloud.BLUE_AERCLOUD.getMeta(), "blue_aercloud");
		registerBlockModelVariant(BlocksAether.aercloud, BlockAercloud.GOLDEN_AERCLOUD.getMeta(), "golden_aercloud");
		registerBlockModelVariant(BlocksAether.aercloud, BlockAercloud.GREEN_AERCLOUD.getMeta(), "green_aercloud");
		registerBlockModelVariant(BlocksAether.aercloud, BlockAercloud.STORM_AERCLOUD.getMeta(), "storm_aercloud");
		registerBlockModelVariant(BlocksAether.aercloud, BlockAercloud.PURPLE_AERCLOUD.getMeta(), "purple_aercloud");

		registerBlockModelVariant(BlocksAether.aether_log, BlockAetherLog.SKYROOT_LOG.getMeta(), "skyroot_log");
		registerBlockModelVariant(BlocksAether.aether_log, BlockAetherLog.GOLDEN_OAK_LOG.getMeta(), "golden_oak_log");

		registerBlockModelVariant(BlocksAether.aether_leaves, BlockAetherLeaves.BLUE_SKYROOT_LEAVES.getMeta(), "blue_skyroot_leaves");
		registerBlockModelVariant(BlocksAether.aether_leaves, BlockAetherLeaves.DARK_BLUE_SKYROOT_LEAVES.getMeta(), "dark_blue_skyroot_leaves");
		registerBlockModelVariant(BlocksAether.aether_leaves, BlockAetherLeaves.GREEN_SKYROOT_LEAVES.getMeta(), "green_skyroot_leaves");
		registerBlockModelVariant(BlocksAether.aether_leaves, BlockAetherLeaves.GOLDEN_OAK_LEAVES.getMeta(), "golden_oak_leaves");
		registerBlockModelVariant(BlocksAether.aether_leaves, BlockAetherLeaves.PURPLE_CRYSTAL_LEAVES.getMeta(), "purple_crystal_leaves");
		registerBlockModelVariant(BlocksAether.aether_leaves, BlockAetherLeaves.PURPLE_FRUIT_LEAVES.getMeta(), "purple_fruit_leaves");

		registerBlockModelVariant(BlocksAether.blueberry_bush, BlockBlueberryBush.BERRY_BUSH_STEM, "blueberry_bush_stem");
		registerBlockModelVariant(BlocksAether.blueberry_bush, BlockBlueberryBush.BERRY_BUSH_RIPE, "blueberry_bush_ripe");

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
	}

	private static void registerItemModels()
	{
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
	}

	/**
	 * Registers the appropriate model to the block. This assumes the model resource name
	 * will be the same as the block's unlocalized name.
	 * @param block The block to register to.
	 */
	private static void registerBlockModel(Block block)
	{
		ResourceLocation resourceLocation = (ResourceLocation) Block.blockRegistry.getNameForObject(block);

		registerBlockModel(block, 0, resourceLocation.getResourcePath());
	}

	/**
	 * Registers the appropriate model to the item. This assumes the model resource name
	 * will be the same as the item's unlocalized name.
	 * @param item The item to register to.
	 */
	private static void registerItemModel(Item item)
	{
		ResourceLocation resourceLocation = (ResourceLocation) Item.itemRegistry.getNameForObject(item);

		registerItemModel(item, 0, resourceLocation.getResourcePath());
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
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Aether.getResource(resourcePath), "inventory");

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, modelResourceLocation);
	}

	/**
	 * Registers a model variant to the specified block and metadata.
	 * @param block The block to register to.
	 * @param meta The metadata to register to.
	 * @param resourcePath The path of the model in the AETHER domain.
	 */
	private static void registerBlockModelVariant(Block block, int meta, String resourcePath)
	{
		Item item = Item.getItemFromBlock(block);

		registerItemModel(item, meta, resourcePath);

		ModelBakery.addVariantName(item, Aether.getResource(resourcePath));
	}
}
