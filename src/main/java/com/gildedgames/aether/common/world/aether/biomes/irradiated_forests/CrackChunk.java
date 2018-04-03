package com.gildedgames.aether.common.world.aether.biomes.irradiated_forests;

public class CrackChunk
{
	private final CrackPos[] data = new CrackPos[16 * 16];

	public CrackChunk()
	{

	}

	public void set(final CrackPos pos, final int x, final int z)
	{
		if (x < 0 || z < 0)
		{
			return;
		}

		final int index = x + (z * 16);

		this.data[index] = pos;
	}

	public CrackPos get(final int x, final int z)
	{
		if (x < 0 || z < 0)
		{
			return null;
		}

		final int index = x + (z * 16);

		return this.data[index];
	}
}
