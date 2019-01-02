package com.gildedgames.aether.common.world.aether.island.gen;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandChunkColumnInfo;
import com.gildedgames.aether.api.world.islands.INoiseProvider;
import com.gildedgames.aether.common.util.ChunkNoiseGenerator;
import com.gildedgames.aether.common.world.util.data.ChunkShortSegment;

public abstract class AbstractIslandChunkColumnInfo implements IIslandChunkColumnInfo
{
	// Lazily initialized.
	private ChunkNoiseGenerator terrainDepthBuffer, cloudDepthBuffer;

	private final ChunkShortSegment heightmap = new ChunkShortSegment((short) -1);

	private final OpenSimplexNoise noise;

	private final int chunkX, chunkZ;

	protected AbstractIslandChunkColumnInfo(OpenSimplexNoise noise, int chunkX, int chunkZ)
	{
		this.noise = noise;

		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	@Override
	public final INoiseProvider getTerrainDepthBuffer()
	{
		if (this.terrainDepthBuffer == null)
		{
			this.terrainDepthBuffer = new ChunkNoiseGenerator(this.chunkX * 16, this.chunkZ * 16, 4, 5, 0, 0, 0.0625D)
			{
				@Override
				protected double getSample(double nx, double nz)
				{
					return (AbstractIslandChunkColumnInfo.this.noise.eval(nx, nz) / 3.0D + 3.0D);
				}
			};
		}

		return this.terrainDepthBuffer;
	}

	@Override
	public ChunkNoiseGenerator getCloudDepthBuffer()
	{
		if (this.cloudDepthBuffer == null)
		{
			this.cloudDepthBuffer = new ChunkNoiseGenerator(this.chunkX * 16, this.chunkZ * 16, 4, 5, 0, 0, 100.0D)
			{
				@Override
				protected double getSample(double nx, double nz)
				{
					return NoiseUtil.normalise(NoiseUtil.something(AbstractIslandChunkColumnInfo.this.noise, nx, nz));
				}
			};
		}

		return this.cloudDepthBuffer;
	}

	@Override
	public int getHeight(int x, int z)
	{
		return (int) this.heightmap.get(x, z);
	}

	@Override
	public void setHeight(int x, int z, int height)
	{
		this.heightmap.set(x, z, (short) height);
	}

	@Override
	public boolean isEmpty()
	{
		for (short i : this.heightmap.getRawArray())
		{
			if (i >= 0)
			{
				return false;
			}
		}

		return true;
	}
}
