package com.gildedgames.aether.common.world.aether.chunk;

public abstract class ChunkDataGenerator2D<T>
{
	// The scale of each sampled point in world coordinates.
	protected final double noiseScaleFactor;

	// The number of points that will be sampled, squared. This is always one greater than the noise scale factor, as we need
	// to generate samples at the top and right edges in order to interpolate cleanly into adjacent chunks.
	protected final int noiseSampleCount;

	public ChunkDataGenerator2D(int noiseResolution)
	{
		// Sanity check. We can't handle very high resolutions where the scale factor would be a fraction of a world coordinate.
		if (noiseResolution > 16)
		{
			throw new IllegalArgumentException("Resolution must not be greater than 16");
		}

		this.noiseScaleFactor = 16.0D / noiseResolution;
		this.noiseSampleCount = noiseResolution + 1;
	}

	public final T generate(int chunkX, int chunkZ)
	{
		T data = this.prepare(chunkX, chunkZ);

		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		for (int x = 0; x < this.noiseSampleCount; x++)
		{
			double worldX = posX + (x * this.noiseScaleFactor);

			for (int z = 0; z < this.noiseSampleCount; z++)
			{
				double worldZ = posZ + (z * this.noiseScaleFactor);

				this.generate(data, x, z, worldX, worldZ);
			}
		}

		return data;
	}

	protected abstract T prepare(int chunkX, int chunkZ);

	protected abstract void generate(T data, int x, int z, double worldX, double worldZ);
}
