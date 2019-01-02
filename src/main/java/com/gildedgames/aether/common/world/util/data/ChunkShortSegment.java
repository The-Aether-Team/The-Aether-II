package com.gildedgames.aether.common.world.util.data;

import java.util.Arrays;

public class ChunkShortSegment
{
	private final short[] data = new short[16 * 16];

	public ChunkShortSegment()
	{

	}

	public ChunkShortSegment(short def)
	{
		Arrays.fill(this.data, def);
	}

	public void set(int x, int z, short i)
	{
		this.data[x << 4 | z] = i;
	}

	public short get(int x, int z)
	{
		return this.data[x << 4 | z];
	}

	public short[] getRawArray()
	{
		return this.data;
	}
}
