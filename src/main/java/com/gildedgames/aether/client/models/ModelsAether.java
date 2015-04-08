package com.gildedgames.aether.client.models;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.util.blockstates.BlockVariant;

public class ModelsAether
{
	private Minecraft mc;

	public ModelsAether()
	{
		this.mc = Minecraft.getMinecraft();
	}

	/**
	 * Registers each of the block's subtypes' item renderers to the appropriate metadata.
	 * Assumes the model name is the block's unlocalizedName plus the variant's name.
	 * @param block The block
	 * @param variants The block's subtypes
	 */
	public void registerItemRenderer(Block block, Collection<BlockVariant> variants)
	{
		String[] names = new String[variants.size()];

		Iterator<BlockVariant> iterator = variants.iterator();
		for (int i = 0; iterator.hasNext(); i++)
		{
			BlockVariant variant = iterator.next();
			names[i] = (Aether.MOD_ID + ":") + variant.getName();

			this.registerItemRenderer(names[i], Item.getItemFromBlock(block), variant.getMeta());
		}

		ModelBakery.addVariantName(Item.getItemFromBlock(block), names);
	}

	/**
	 * Registers the block's item renderer and binds it to the specified metadata.
	 * This assumes the unlocalized name of the block is the model name.
	 * @param block The block to register
	 * @param meta The metadata to bind to
	 */
	public void registerItemRenderer(int meta, Block block)
	{
		this.registerItemRenderer(meta, Item.getItemFromBlock(block));
	}

	/**
	 * Registers multiples item renderers at the same metadata index.
	 * This assumes the unlocalized name of the item is the model name.
	 * @param meta The meta to bind to
	 * @param items Array of items to register
	 */
	public void registerItemRenderers(int meta, Item... items)
	{
		for (Item item : items)
		{
			this.registerItemRenderer(meta, item);
		}
	}

	/**
	 * Registers the item's item renderer and binds it to the specified metadata.
	 * This assumes the unlocalized name of the item is the model name.
	 * @param item The item to register
	 * @param meta The meta to bind to
	 */
	public void registerItemRenderer(int meta, Item item)
	{
		String name = (Aether.MOD_ID + ":") + item.getUnlocalizedName().substring(5);

		this.registerItemRenderer(name, item, meta);
	}

	/**
	 * Registers the block's item renderer with the specified name and binds it to the metadata.
	 * @param name The model resource name
	 * @param block The block to register
	 * @param meta The metadata to bind to
	 */
	public void registerItemRenderer(String name, Block block, int meta)
	{
		this.registerItemRenderer(name, Item.getItemFromBlock(block), meta);
	}

	/**
	 * Registers the item's item renderer with the specified name and binds it to the metadata.
	 * @param name The model resource name
	 * @param item The item to register
	 * @param meta The metadata to bind to
	 */
	public void registerItemRenderer(String name, Item item, int meta)
	{
		this.mc.getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(name, "inventory"));
	}
}
