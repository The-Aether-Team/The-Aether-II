package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.INoiseProvider;
import com.gildedgames.aether.common.util.ChunkNoiseGenerator;

public abstract class AbstractIslandChunkColumnInfo implements IIslandChunkColumnInfo
{
	private final ChunkNoiseGenerator terrainDepthBuffer, cloudDepthBuffer;

	private final short[][] heightmap = new short[16][16];

	protected AbstractIslandChunkColumnInfo(OpenSimplexNoise noise, int chunkX, int chunkZ)
	{
		this.terrainDepthBuffer = new ChunkNoiseGenerator(noise, chunkX * 16, chunkZ * 16, 4, 5, 0, 0, 0.0625D)
		{
			@Override
			protected double sample(double nx, double nz)
			{
				return (this.generator.eval(nx, nz) / 3.0D + 3.0D);
			}
		};

		this.cloudDepthBuffer = new ChunkNoiseGenerator(noise, chunkX * 16, chunkZ * 16, 4, 5, 0, 0, 70.0D)
		{
			@Override
			protected double sample(double nx, double nz)
			{
				return NoiseUtil.normalise(NoiseUtil.something(this.generator, nx, nz));
			}
		};
	}

	@Override
	public final INoiseProvider getTerrainDepthBuffer()
	{
		return this.terrainDepthBuffer;
	}

	@Override
	public ChunkNoiseGenerator getCloudDepthBuffer()
	{
		return this.cloudDepthBuffer;
	}

	@Override
	public int getHeight(int x, int y)
	{
		return (int) this.heightmap[x][y];
	}

	@Override
	public void setHeight(int x, int y, int height)
	{
		this.heightmap[x][y] = (short) height;
	}
}
