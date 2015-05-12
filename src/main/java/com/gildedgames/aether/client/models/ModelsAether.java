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
	private Minecraft mc;

	public ModelsAether(Minecraft mc)
	{
		this.mc = mc;
	}

	public void prepareModels()
	{
		BlocksAether blocks = Aether.getBlocks();

		ModelLoader.setCustomStateMapper(blocks.aether_leaves, new StateMap.Builder().setProperty(BlockAetherLeaves.LEAVES_VARIANT).addPropertiesToIgnore(BlockAetherLeaves.CHECK_DECAY, BlockAetherLeaves.DECAYABLE).build());
		ModelLoader.setCustomStateMapper(blocks.aercloud, new StateMapperBase()
		{
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				LinkedHashMap mappings = Maps.newLinkedHashMap(state.getProperties());

				if (state.getValue(BlockAercloud.AERCLOUD_VARIANT) != BlockAercloud.PURPLE_AERCLOUD)
				{
					mappings.remove(BlockAercloud.FACING);
				}

				return new ModelResourceLocation((ResourceLocation) Block.blockRegistry.getNameForObject(state.getBlock()), this.getPropertyString(mappings));
			}
		});
	}

	public void registerModels()
	{
		this.registerBlockModels();
		this.registerItemModels();
	}

	private void registerBlockModels()
	{
		BlocksAether blocks = Aether.getBlocks();

		this.registerBlockModelVariants(blocks.aether_dirt, BlockAetherDirt.GRASS_VARIANT.getAllowedValues());
		this.registerBlockModelVariants(blocks.holystone, BlockHolystone.HOLYSTONE_VARIANT.getAllowedValues());
		this.registerBlockModelVariants(blocks.aercloud, BlockAercloud.AERCLOUD_VARIANT.getAllowedValues());
		this.registerBlockModelVariants(blocks.aether_log, BlockAetherLog.LOG_VARIANT.getAllowedValues());
		this.registerBlockModelVariants(blocks.aether_leaves, BlockAetherLeaves.LEAVES_VARIANT.getAllowedValues());
		this.registerBlockModel(blocks.skyroot_planks);
		this.registerBlockModel(blocks.ambrosium_ore);
		this.registerBlockModel(blocks.zanite_ore);
		this.registerBlockModel(blocks.gravitite_ore);
		this.registerBlockModel(blocks.continuum_ore);
		this.registerBlockModel(blocks.aether_portal);
		this.registerBlockModel(blocks.aether_tall_grass);
	}

	private void registerItemModels()
	{
		ItemsAether items = Aether.getItems();

		this.registerItemModel(items.skyroot_stick);
		this.registerItemModel(items.ambrosium_shard);
		this.registerItemModel(items.continuum_orb);
		this.registerItemModel(items.zanite_gemstone);

		this.registerItemModel(items.skyroot_pickaxe);
		this.registerItemModel(items.skyroot_axe);
		this.registerItemModel(items.skyroot_shovel);
		this.registerItemModel(items.skyroot_sword);

		this.registerItemModel(items.holystone_pickaxe);
		this.registerItemModel(items.holystone_axe);
		this.registerItemModel(items.holystone_shovel);
		this.registerItemModel(items.holystone_sword);

		this.registerItemModel(items.zanite_pickaxe);
		this.registerItemModel(items.zanite_axe);
		this.registerItemModel(items.zanite_shovel);
		this.registerItemModel(items.zanite_sword);

		this.registerItemModel(items.gravitite_pickaxe);
		this.registerItemModel(items.gravitite_axe);
		this.registerItemModel(items.gravitite_shovel);
		this.registerItemModel(items.gravitite_sword);

		this.registerItemModel(items.zanite_helmet);
		this.registerItemModel(items.zanite_chestplate);
		this.registerItemModel(items.zanite_leggings);
		this.registerItemModel(items.zanite_boots);

		this.registerItemModel(items.gravitite_helmet);
		this.registerItemModel(items.gravitite_chestplate);
		this.registerItemModel(items.gravitite_leggings);
		this.registerItemModel(items.gravitite_boots);
	}

	/**
	 * Registers all the models for variants of a block.
	 * @param block The block to bind to
	 * @param variants All of the block's variants
	 */
	public void registerBlockModelVariants(Block block, Collection<BlockVariant> variants)
	{
		for (BlockVariant variant : variants)
		{
			Item item = Item.getItemFromBlock(block);

			this.registerItemModel(item, variant.getName(), variant.getMeta());
			ModelBakery.addVariantName(item, (Aether.MOD_ID + ":") + variant.getName());
		}
	}

	/**
	 * Registers a block model and binds it to the block's unlocalized name and metadata 0.
	 * @param block The block to bind to
	 */
	public void registerBlockModel(Block block)
	{
		this.registerBlockModel(block, block.getUnlocalizedName().substring(5), 0);
	}

	/**
	 * Registers a block model and binds it to the specified block, name and metadata.
	 * @param block The block to bind to
	 * @param name The name to bind to
	 * @param meta The metadata to bind to
	 */
	public void registerBlockModel(Block block, String name, int meta)
	{
		this.registerItemModel(Item.getItemFromBlock(block), name, meta);
	}

	/**
	 * Registers a item model and binds it to the item's unlocalized name and metadata 0.
	 * @param item The item to bind to
	 */
	public void registerItemModel(Item item)
	{
		this.registerItemModel(item, item.getUnlocalizedName().substring(5), 0);
	}

	/**
	 * Registers an item model and binds it to the specified item, name, and metadata.
	 * @param item The item to bind to
	 * @param name The name to bind to
	 * @param meta The metadata to bind to
	 */
	public void registerItemModel(Item item, String name, int meta)
	{
		this.mc.getRenderItem().getItemModelMesher().register(item, meta, this.getModelResource(name, "inventory"));
	}

	private ModelResourceLocation getModelResource(String name, String type)
	{
		return new ModelResourceLocation(Aether.getResource(name), type);
	}
}
