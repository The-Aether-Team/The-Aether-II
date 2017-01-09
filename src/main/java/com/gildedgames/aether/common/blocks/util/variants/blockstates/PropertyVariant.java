package com.gildedgames.aether.common.blocks.util.variants.blockstates;

import com.google.common.base.Optional;
import net.minecraft.block.properties.IProperty;

import java.util.Collection;
import java.util.HashMap;

@Deprecated
public class PropertyVariant implements IProperty<BlockVariant>
{
	private final String name;

	private final BlockVariant defaultVariant;

	private final HashMap<Integer, BlockVariant> metaMap;

	private final HashMap<String, BlockVariant> nameMap;

	protected PropertyVariant(String name, BlockVariant... variants)
	{
		this.name = name;
		this.metaMap = new HashMap<>();
		this.nameMap = new HashMap<>();

		this.defaultVariant = variants[0];

		for (BlockVariant variant : variants)
		{
			this.metaMap.put(variant.getMeta(), variant);
			this.nameMap.put(variant.getName(), variant);
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
		return this.metaMap.values();
	}

	@Override
	public Class<BlockVariant> getValueClass()
	{
		return BlockVariant.class;
	}

	@Override
	public Optional<BlockVariant> parseValue(String value)
	{
		return Optional.fromNullable(this.nameMap.get(value));
	}

	@Override
	public String getName(BlockVariant value)
	{
		return value.getName();
	}

	public BlockVariant fromMeta(int meta)
	{
		BlockVariant variant = this.metaMap.get(meta);

		return variant != null ? variant : this.defaultVariant;
	}
}
