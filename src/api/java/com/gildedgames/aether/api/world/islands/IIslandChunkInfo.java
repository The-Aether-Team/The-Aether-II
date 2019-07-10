package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer2D;

public interface IIslandChunkInfo
{
	IChunkNoiseBuffer2D getTerrainDepthBuffer();

	IChunkNoiseBuffer2D getCloudDepthBuffer();
}
