package com.gildedgames.aether.common.world.util.data;

import com.gildedgames.aether.api.world.noise.IChunkHeightmap;

public class ChunkHeightmap implements IChunkHeightmap
{
	private final byte[] data = new byte[16 * 16];

	@Override
	public void setHeight(int x, int z, int height)
	{
		this.data[x << 4 | z] = (byte) height;
	}

	@Override
	public int getHeight(int x, int z)
	{
		return Byte.toUnsignedInt(this.data[x << 4 | z]);
	}

	@Override
	public boolean isEmpty()
	{
		for (byte i : this.data)
		{
			if (i != 0)
			{
				return false;
			}
		}

		return true;
	}
}
