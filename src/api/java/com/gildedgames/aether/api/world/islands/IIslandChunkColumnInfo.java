package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.world.noise.IChunkHeightmap;
import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer2D;

public interface IIslandChunkColumnInfo
{
	IChunkNoiseBuffer2D getTerrainDepthBuffer();

	IChunkNoiseBuffer2D getCloudDepthBuffer();

	IChunkHeightmap getHeightmap();

	boolean hasSoil(int x, int z);
}
