package com.gildedgames.aether.client.models;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.BlocksAether;
import com.gildedgames.aether.blocks.natural.BlockAercloud;
import com.gildedgames.aether.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.blocks.natural.BlockAetherLog;
import com.gildedgames.aether.blocks.natural.BlockHolystone;
import com.gildedgames.aether.blocks.util.blockstates.BlockVariant;
import com.gildedgames.aether.items.ItemsAether;
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
	public static void prepareModels()
	{
		ModelLoader.setCustomStateMapper(BlocksAether.aether_leaves, new StateMap.Builder().setProperty(BlockAetherLeaves.LEAVES_VARIANT).addPropertiesToIgnore(BlockAetherLeaves.CHECK_DECAY, BlockAetherLeaves.DECAYABLE).build());
		ModelLoader.setCustomStateMapper(BlocksAether.aercloud, new StateMapperBase()
		{
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				LinkedHashMap mappings = Maps.newLinkedHashMap(state.getProperties());

				if (state.getValue(BlockAercloud.AERCLOUD_VARIANT) != BlockAercloud.PURPLE_AERCLOUD)
				{
					mappings.remove(BlockAercloud.FACING);
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
		registerBlockModelVariants(BlocksAether.aether_dirt, BlockAetherDirt.GRASS_VARIANT.getAllowedValues());
		registerBlockModelVariants(BlocksAether.holystone, BlockHolystone.HOLYSTONE_VARIANT.getAllowedValues());
		registerBlockModelVariants(BlocksAether.aercloud, BlockAercloud.AERCLOUD_VARIANT.getAllowedValues());
		registerBlockModelVariants(BlocksAether.aether_log, BlockAetherLog.LOG_VARIANT.getAllowedValues());
		registerBlockModelVariants(BlocksAether.aether_leaves, BlockAetherLeaves.LEAVES_VARIANT.getAllowedValues());
		registerBlockModel(BlocksAether.skyroot_planks);
		registerBlockModel(BlocksAether.ambrosium_ore);
		registerBlockModel(BlocksAether.zanite_ore);
		registerBlockModel(BlocksAether.gravitite_ore);
		registerBlockModel(BlocksAether.continuum_ore);
		registerBlockModel(BlocksAether.aether_portal);
		registerBlockModel(BlocksAether.aether_tall_grass);
		registerBlockModel(BlocksAether.quicksoil);
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
	}

	/**
	 * Registers all the models for variants of a block.
	 * @param block The block to bind to
	 * @param variants All of the block's variants
	 */
	private static void registerBlockModelVariants(Block block, Collection<BlockVariant> variants)
	{
		for (BlockVariant variant : variants)
		{
			Item item = Item.getItemFromBlock(block);

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
