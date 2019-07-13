package com.gildedgames.aether.common.blocks.properties;

import java.util.Optional;
import net.minecraft.state.IProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PropertyVariant implements IProperty<BlockVariant>
{
	private final String name;

	private final BlockVariant[] mappings;

	private final HashMap<String, BlockVariant> entries;

	private final List<BlockVariant> values = new ArrayList<>();

	protected PropertyVariant(String name, BlockVariant... variants)
	{
		if (variants.length <= 0)
		{
			throw new RuntimeException("At least one variant must be supplied");
		}

		this.name = name;

		this.mappings = variants;
		this.entries = new HashMap<>();

		for (BlockVariant variant : variants)
		{
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
		return Optional.ofNullable(this.entries.get(value));
	}

	@Override
	public String getName(BlockVariant value)
	{
		return value.getName();
	}

	public BlockVariant fromMeta(int meta)
	{
		if (meta < 0 || meta > this.mappings.length)
		{
			return this.mappings[0];
		}

		return this.mappings[meta];
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
