package com.gildedgames.aether.client.util;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class ItemModelList
{
	private final Item item;

	private final HashMap<Integer, ResourceLocation> registrations = new HashMap<>();

	private String rootFolder = null;

	public ItemModelList(Item item)
	{
		this.item = item;
	}

	public ItemModelList(Block block)
	{
		this(Item.getItemFromBlock(block));
	}

	public ItemModelList root(String root)
	{
		if (!root.endsWith("/"))
		{
			throw new IllegalArgumentException("Path '" + root + "' isn't absolute");
		}

		this.rootFolder = root;

		return this;
	}

	public ItemModelList add(int meta, String path)
	{
		this.registrations.put(meta, new ResourceLocation(AetherCore.getResourcePath(this.rootFolder != null ? this.rootFolder + path : path)));

		return this;
	}

	public HashMap<Integer, ResourceLocation> getRegistrations()
	{
		return this.registrations;
	}

	public Item getItem()
	{
		return this.item;
	}
}
