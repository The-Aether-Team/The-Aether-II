package com.gildedgames.aether.blocks.util.blockstates;

import java.util.Collection;
import java.util.HashMap;

import net.minecraft.block.properties.IProperty;

import com.google.common.collect.ImmutableSet;

public class PropertyVariant implements IProperty
{
	private String name;

	private ImmutableSet<BlockVariant> valuesSet;

	private HashMap<Integer, BlockVariant> metaMap;

	protected PropertyVariant(String name, BlockVariant... variants)
	{
		this.name = name;
		this.valuesSet = ImmutableSet.copyOf(variants);
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
		return this.valuesSet;
	}

	@Override
	public Class<BlockVariant> getValueClass()
	{
		return BlockVariant.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getName(Comparable value)
	{
		return ((BlockVariant) value).getName();
	}

	public BlockVariant getVariantFromMeta(int meta)
	{
		return this.metaMap.get(meta);
	}

}
