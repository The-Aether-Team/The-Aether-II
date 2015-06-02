package com.gildedgames.aether.client.models;

import com.gildedgames.aether.common.Aether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
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

import java.util.Collection;
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

				return new ModelResourceLocation((ResourceLocation) Block.blockRegistry.getNameForObject(state.getBlock()), getPropertyString(mappings));
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
		registerBlockModelWithVariants(BlocksAether.aether_grass, BlockAetherGrass.PROPERTY_VARIANT.getAllowedValues());
		registerBlockModelWithVariants(BlocksAether.holystone, BlockHolystone.PROPERTY_VARIANT.getAllowedValues());
		registerBlockModelWithVariants(BlocksAether.aercloud, BlockAercloud.PROPERTY_VARIANT.getAllowedValues());
		registerBlockModelWithVariants(BlocksAether.aether_log, BlockAetherLog.PROPERTY_VARIANT.getAllowedValues());
		registerBlockModelWithVariants(BlocksAether.aether_leaves, BlockAetherLeaves.PROPERTY_VARIANT.getAllowedValues());
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
	}

	/**
	 * Registers all the models for variants of a block.
	 * @param block The block to bind to
	 * @param variants All of the block's variants
	 */
	private static void registerBlockModelWithVariants(Block block, Collection<BlockVariant> variants)
	{
		Item item = Item.getItemFromBlock(block);

		for (BlockVariant variant : variants)
		{
			registerItemModel(item, variant.getName(), variant.getMeta());

			ModelBakery.addVariantName(item, (Aether.MOD_ID + ":") + variant.getName());
		}
	}

	/**
	 * Registers a block model and binds it to the block's unlocalized name and metadata 0.
	 * @param block The block to bind to
	 */
	private static void registerBlockModel(Block block)
	{
		registerBlockModel(block, block.getUnlocalizedName().substring(5), 0);
	}

	/**
	 * Registers a block model and binds it to the specified block, name and metadata.
	 * @param block The block to bind to
	 * @param name The name to bind to
	 * @param meta The metadata to bind to
	 */
	private static void registerBlockModel(Block block, String name, int meta)
	{
		registerItemModel(Item.getItemFromBlock(block), name, meta);
	}

	/**
	 * Registers a item model and binds it to the item's unlocalized name and metadata 0.
	 * @param item The item to bind to
	 */
	public static void registerItemModel(Item item)
	{
		registerItemModel(item, item.getUnlocalizedName().substring(5), 0);
	}

	/**
	 * Registers an item model and binds it to the specified item, name, and metadata.
	 * @param item The item to bind to
	 * @param name The name to bind to
	 * @param meta The metadata to bind to
	 */
	public static void registerItemModel(Item item, String name, int meta)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, getModelResource(name, "inventory"));
	}

	private static ModelResourceLocation getModelResource(String name, String type)
	{
		return new ModelResourceLocation(Aether.getResource(name), type);
	}
}
