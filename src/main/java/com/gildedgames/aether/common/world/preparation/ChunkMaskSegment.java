package com.gildedgames.aether.common.world.preparation;

import com.gildedgames.aether.api.world.preparation.IChunkMaskSegment;

import java.util.Arrays;

/**
 * 16x8x16 segment of ChunkMask blocks.
 */
public class ChunkMaskSegment implements IChunkMaskSegment
{
	private final byte[] blocks = new byte[16 * 8 * 16];

	@Override
	public void setBlock(int x, int y, int z, int b)
	{
		this.blocks[x << 7 | z << 3 | y] = (byte) b;
	}

	@Override
	public int getBlock(int x, int y, int z)
	{
		return this.blocks[x << 7 | z << 3 | y];
	}

	@Override
	public void fill(int b)
	{
		Arrays.fill(this.blocks, (byte) b);
	}
}
