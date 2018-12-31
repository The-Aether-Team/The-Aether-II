package com.gildedgames.aether.common.world.util.data;

public class ChunkDoubleSegment
{
	private final double[] data = new double[16 * 16];

	public void set(int x, int z, double i)
	{
		this.data[x << 4 | z] = i;
	}

	public double get(int x, int z)
	{
		return this.data[x << 4 | z];
	}
}
