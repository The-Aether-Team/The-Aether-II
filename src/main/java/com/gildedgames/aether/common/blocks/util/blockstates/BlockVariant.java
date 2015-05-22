package com.gildedgames.aether.common.blocks.util.blockstates;

public class BlockVariant implements Comparable<BlockVariant>
{
	private int meta;

	private String name;

	public BlockVariant(int meta, String name)
	{
		this.meta = meta;
		this.name = name;
	}

	public int getMeta()
	{
		return this.meta;
	}

	public String getName()
	{
		return this.name;
	}

	@Override
	public int compareTo(BlockVariant o)
	{
		// Huh?! Apparently this method is used to sort things.
		// It's probably best case to sort by meta.
		return this.meta;
	}

	@Override
	public String toString()
	{
		return this.getName();
	}
}
