package com.gildedgames.aether.island_gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

public class ChunkPrimerIsland extends ChunkPrimer
{
	private static final IBlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();

	private final char[] data = new char[65536];

	private static int getBlockIndex(final int x, final int y, final int z)
	{
		return x << 12 | z << 8 | y;
	}

	@Override
	public IBlockState getBlockState(final int x, final int y, final int z)
	{
		final IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(this.data[getBlockIndex(x, y, z)]);
		return iblockstate == null ? DEFAULT_STATE : iblockstate;
	}

	@Override
	public void setBlockState(final int x, final int y, final int z, final IBlockState state)
	{
		this.data[getBlockIndex(x, y, z)] = (char) Block.BLOCK_STATE_IDS.get(state);
	}

	/**
	 * Counting down from the highest block in the sky, find the first non-air block for the given location
	 * (actually, looks like mostly checks x, z+1? And actually checks only the very top sky block of actual x, z)
	 */
	@Override
	public int findGroundBlockIdx(final int x, final int z)
	{
		final int i = (x << 12 | z << 8) + 256 - 1;

		for (int j = 255; j >= 0; --j)
		{
			final IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(this.data[i + j]);

			if (iblockstate != null && iblockstate != DEFAULT_STATE)
			{
				return j;
			}
		}

		return 0;
	}

	public IBlockState findGroundBlock(final int x, final int z)
	{
		final int i = (x << 12 | z << 8) + 256 - 1;

		for (int j = 255; j >= 0; --j)
		{
			final IBlockState iblockstate = Block.BLOCK_STATE_IDS.getByValue(this.data[i + j]);

			if (iblockstate != null && iblockstate != Blocks.AIR.getDefaultState())
			{
				return iblockstate;
			}
		}

		return null;
	}

}
