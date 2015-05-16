package com.gildedgames.aether.blocks.util.blockstates;

import net.minecraft.block.properties.IProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class PropertyVariant implements IProperty
{
	private String name;

	private ArrayList<BlockVariant> values;

	private HashMap<Integer, BlockVariant> metaMap;

	protected PropertyVariant(String name, BlockVariant... variants)
	{
		this.name = name;
		this.values = new ArrayList<BlockVariant>(Arrays.asList(variants));
		this.metaMap = new HashMap<Integer, BlockVariant>();

		for (BlockVariant variant : variants)
		{
			this.metaMap.put(variant.getMeta(), variant);
		}
	}

	public static PropertyVariant create(String name, BlockVariant... variants)
	{
		return new PropertyVariant(name, variants);
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public Collection<BlockVariant> getAllowedValues()
	{
		return this.values;
	}

	@Override
	public Class<BlockVariant> getValueClass()
	{
		return BlockVariant.class;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public String getName(Comparable value)
	{
		return ((BlockVariant) value).getName();
	}

	public BlockVariant getVariantFromMeta(int meta)
	{
		return this.metaMap.get(meta);
	}

}
