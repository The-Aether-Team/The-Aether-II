package com.gildedgames.aether.api.orbis;

public interface IChunkRendererProvider
{
	IChunkRendererCapability get(final int chunkX, final int chunkZ);
}
