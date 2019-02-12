package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.world.noise.IChunkHeightmap;
import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer;

public interface IIslandChunkColumnInfo
{
	IChunkNoiseBuffer getTerrainDepthBuffer();

	IChunkNoiseBuffer getCloudDepthBuffer();

	IChunkHeightmap getHeightmap();

	boolean hasSoil(int x, int z);
}
