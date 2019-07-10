package com.gildedgames.aether.api.world.preparation;

public interface IChunkMaskSegment
{
	void setBlock(int x, int y, int z, int b);

	int getBlock(int x, int y, int z);

	void fill(int b);
}
