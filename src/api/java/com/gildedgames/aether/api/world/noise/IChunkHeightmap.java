package com.gildedgames.aether.api.world.noise;

public interface IChunkHeightmap
{
	void setHeight(int x, int z, int height);

	int getHeight(int x, int z);

	boolean isEmpty();
}
