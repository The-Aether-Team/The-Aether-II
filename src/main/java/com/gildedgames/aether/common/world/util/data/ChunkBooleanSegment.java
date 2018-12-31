package com.gildedgames.aether.common.world.util.data;

public class ChunkBooleanSegment
{
	private final boolean[] data = new boolean[16 * 16];

	public void set(int x, int z, boolean i)
	{
		this.data[x << 4 | z] = i;
	}

	public boolean get(int x, int z)
	{
		return this.data[x << 4 | z];
	}
}
