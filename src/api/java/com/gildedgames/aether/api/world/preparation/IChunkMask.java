package com.gildedgames.aether.api.world.preparation;

public interface IChunkMask
{
	void setBlock(int x, int y, int z, int b);

	int getBlock(int x, int y, int z);

	int getX();

	int getZ();

	IChunkMaskSegment getSegment(int y);

	int getMaxYSegment();

	int getMinYSegment();

	int getHighestBlock(int x, int z);

	void fill(int b);
}
