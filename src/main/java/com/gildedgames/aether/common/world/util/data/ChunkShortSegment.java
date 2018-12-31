package com.gildedgames.aether.common.world.util.data;

public class ChunkShortSegment
{
	private final short[] data = new short[16 * 16];

	public void set(int x, int z, short i)
	{
		this.data[x << 4 | z] = i;
	}

	public short get(int x, int z)
	{
		return this.data[x << 4 | z];
	}
}
