package com.gildedgames.aether.api.orbis;

public interface IChunkRendererProvider
{
	IChunkRenderer get(final int chunkX, final int chunkZ);
}
