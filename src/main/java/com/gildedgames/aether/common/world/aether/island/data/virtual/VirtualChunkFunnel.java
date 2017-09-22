package com.gildedgames.aether.common.world.aether.island.data.virtual;

import com.gildedgames.aether.api.world.islands.IVirtualChunk;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

/**
 * Created to wrap around ChunkPrimer and funnel the methods into a
 * VirtualChunk object inside.
 */
public class VirtualChunkFunnel extends ChunkPrimer
{

	private static final IBlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();

	private final IVirtualChunk chunk;

	public VirtualChunkFunnel(final IVirtualChunk chunk)
	{
		this.chunk = chunk;
	}

	@Override
	public IBlockState getBlockState(final int x, final int y, final int z)
	{
		if (y > VirtualChunk.HEIGHT)
		{
			return DEFAULT_STATE;
		}

		return this.chunk.getState(x, y, z);
	}

	@Override
	public void setBlockState(final int x, final int y, final int z, final IBlockState state)
	{
		this.chunk.setState(x, y, z, state);
	}

}
