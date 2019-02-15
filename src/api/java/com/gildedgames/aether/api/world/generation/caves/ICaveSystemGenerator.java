package com.gildedgames.aether.api.world.generation.caves;

public interface ICaveSystemGenerator
{
	CaveSystemNode getNode(int chunkX, int chunkZ);

	int getNeighborChunkSearchRadius();
}
