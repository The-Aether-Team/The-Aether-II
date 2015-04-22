package com.gildedgames.aether.client.models;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.util.blockstates.BlockVariant;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

import java.util.Collection;

public class ModelsAether
{
	private Minecraft mc;

	public ModelsAether(Minecraft mc)
	{
		this.mc = mc;
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
		return new ModelResourceLocation((Aether.MOD_ID + ":") + name, type);
	}
}
