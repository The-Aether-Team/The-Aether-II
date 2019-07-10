package com.gildedgames.aether.common.world.island;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandChunkInfo;
import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer2D;
import com.gildedgames.aether.common.world.noise.ChunkDataGenerator2DSingle;
import com.gildedgames.aether.common.world.noise.impl.NoiseGeneratorClouds;
import com.gildedgames.aether.common.world.noise.impl.NoiseGeneratorSoilDepth;

public abstract class AbstractIslandChunkInfo implements IIslandChunkInfo
{
	// Lazily initialized.
	private IChunkNoiseBuffer2D terrainDepthBuffer, cloudDepthBuffer;

	private final OpenSimplexNoise noise;

	private final int chunkX, chunkZ;

	protected AbstractIslandChunkInfo(OpenSimplexNoise noise, int chunkX, int chunkZ)
	{
		this.noise = noise;

		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	@Override
	public final IChunkNoiseBuffer2D getTerrainDepthBuffer()
	{
		if (this.terrainDepthBuffer == null)
		{
			this.terrainDepthBuffer = new ChunkDataGenerator2DSingle(new NoiseGeneratorSoilDepth(this.noise), 2)
					.generate(this.chunkX, this.chunkZ)
					.createInterpolatedNoiseBuffer();
		}

		return this.terrainDepthBuffer;
	}

	@Override
	public final IChunkNoiseBuffer2D getCloudDepthBuffer()
	{
		if (this.cloudDepthBuffer == null)
		{
			this.cloudDepthBuffer = new ChunkDataGenerator2DSingle(new NoiseGeneratorClouds(this.noise), 2)
					.generate(this.chunkX, this.chunkZ)
					.createInterpolatedNoiseBuffer();
		}

		return this.cloudDepthBuffer;
	}
}
