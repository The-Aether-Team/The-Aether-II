package com.gildedgames.aether.common.world.preparation.mask;

import com.gildedgames.aether.api.world.preparation.IChunkMask;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.chunk.ChunkPrimer;

/**
 * Very minimal alternative to chunks for terrain generation.
 *
 * This allows mods with very expensive terrain generation to quickly compose the general structure of blocks
 * inside a chunk without referring to specific block types or performing costly {@link IBlockState} operations.
 *
 * After the general structure has been created, a very quick conversion of the blocks contained in the mask can
 * be performed to create a {@link ChunkPrimer}.
 *
 * Block 0 is assumed to always be air.
 */
public class ChunkMask implements IChunkMask
{
	private final ChunkMaskSegment[] segments = new ChunkMaskSegment[32];

	private int maxY = Integer.MIN_VALUE, minY = Integer.MAX_VALUE;

	private boolean empty = true;

	private final int x, z;

	public ChunkMask(int x, int z)
	{
		this.x = x;
		this.z = z;
	}

	@Override
	public void setBlock(int x, int y, int z, int b)
	{
		int chunkY = y >> 3;

		ChunkMaskSegment segment = this.segments[chunkY];

		if (segment == null)
		{
			segment = new ChunkMaskSegment();

			this.segments[chunkY] = segment;

			this.maxY = Math.max(chunkY, this.maxY);
			this.minY = Math.min(chunkY, this.minY);

			this.empty = false;
		}

		segment.setBlock(x, y & 7, z, b);
	}

	@Override
	public int getBlock(int x, int y, int z)
	{
		ChunkMaskSegment segment = this.segments[y >> 3];

		if (segment == null)
		{
			return 0;
		}

		return segment.getBlock(x, y & 7, z);
	}

	@Override
	public int getX()
	{
		return this.x;
	}

	@Override
	public int getZ()
	{
		return this.z;
	}

	@Override
	public ChunkMaskSegment getSegment(int y)
	{
		return this.segments[y];
	}

	@Override
	public int getMaxYSegment()
	{
		return this.maxY;
	}

	@Override
	public int getMinYSegment()
	{
		return this.minY;
	}

	@Override
	public int getHighestBlock(int x, int z)
	{
		if (!this.empty)
		{
			for (int chunkY = this.maxY; chunkY >= this.minY; chunkY--)
			{
				ChunkMaskSegment segment = this.segments[chunkY];

				if (segment == null)
				{
					continue;
				}

				for (int y = 7; y >= 0; y--)
				{
					if (segment.getBlock(x, y, z) > 0)
					{
						return (chunkY * 8) + y;
					}
				}
			}
		}

		return -1;
	}

	@Override
	public void fill(int b)
	{
		for (int chunkY = 0; chunkY < 32; chunkY++)
		{
			ChunkMaskSegment segment = this.segments[chunkY];

			if (segment == null)
			{
				this.segments[chunkY] = segment = new ChunkMaskSegment();
			}

			segment.fill(b);
		}

		this.minY = 0;
		this.maxY = 31;

		this.empty = false;
	}
}
