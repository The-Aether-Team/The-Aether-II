package com.gildedgames.aether.common.blocks.properties;

import com.google.common.base.Optional;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.IntHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PropertyVariant implements IProperty<BlockVariant>
{
	private final String name;

	private final BlockVariant defaultVariant;

	private final IntHashMap<BlockVariant> mappings;

	private final HashMap<String, BlockVariant> entries;

	private final List<BlockVariant> values = new ArrayList<>();

	protected PropertyVariant(String name, BlockVariant... variants)
	{
		this.name = name;

		this.mappings = new IntHashMap<>();
		this.entries = new HashMap<>();

		this.defaultVariant = variants[0];

		for (BlockVariant variant : variants)
		{
			this.mappings.addKey(variant.getMeta(), variant);
			this.entries.put(variant.getName(), variant);
			this.values.add(variant);
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
		return this.entries.values();
	}

	@Override
	public Class<BlockVariant> getValueClass()
	{
		return BlockVariant.class;
	}

	@Override
	public Optional<BlockVariant> parseValue(String value)
	{
		return Optional.fromNullable(this.entries.get(value));
	}

	@Override
	public String getName(BlockVariant value)
	{
		return value.getName();
	}

	public BlockVariant fromMeta(int meta)
	{
		BlockVariant variant = this.mappings.lookup(meta);

		return variant != null ? variant : this.defaultVariant;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		else if (other instanceof PropertyVariant && super.equals(other))
		{
			PropertyVariant otherProperty = (PropertyVariant) other;

			return this.values.equals(otherProperty.values) && this.entries.equals(otherProperty.entries);
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		int i = super.hashCode();
		i = 31 * i + this.values.hashCode();
		i = 31 * i + this.entries.hashCode();

		return i;
	}
}
