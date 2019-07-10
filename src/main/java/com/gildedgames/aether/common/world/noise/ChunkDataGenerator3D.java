package com.gildedgames.aether.common.world.noise;

public abstract class ChunkDataGenerator3D<T>
{
	// The scale of each sampled point in world coordinates.
	protected final double noiseScaleFactorXZ, noiseScaleFactorY;

	// The number of points that will be sampled, squared. This is always one greater than the noise scale factor, as we need
	// to generate samples at the top and right edges in order to interpolate cleanly into adjacent chunks.
	protected final int noiseSampleCountXZ, noiseSampleCountY;

	public ChunkDataGenerator3D(int noiseResolutionXZ, int noiseResolutionY)
	{
		// Sanity check. We can't handle very high resolutions where the scale factor would be a fraction of a world coordinate.
		if (noiseResolutionXZ > 16)
		{
			throw new IllegalArgumentException("Resolution must not be greater than 16");
		}

		this.noiseScaleFactorXZ = 16.0D / noiseResolutionXZ;
		this.noiseSampleCountXZ = noiseResolutionXZ + 1;

		this.noiseScaleFactorY = 256.0D / noiseResolutionY;
		this.noiseSampleCountY = noiseResolutionY + 1;
	}

	public final T generate(int chunkX, int chunkZ)
	{
		T data = this.prepare(chunkX, chunkZ);

		int posX = chunkX * 16;
		int posZ = chunkZ * 16;

		for (int x = 0; x < this.noiseSampleCountXZ; x++)
		{
			double worldX = posX + (x * this.noiseScaleFactorXZ);

			for (int z = 0; z < this.noiseSampleCountXZ; z++)
			{
				double worldZ = posZ + (z * this.noiseScaleFactorXZ);

				for (int y = 0; y < this.noiseSampleCountY; y++)
				{
					double worldY = y * this.noiseScaleFactorY;

					this.generate(data, x, y, z, worldX, worldY, worldZ);
				}
			}
		}

		return data;
	}

	protected abstract T prepare(int chunkX, int chunkZ);

	protected abstract void generate(T data, int x, int y, int z, double worldX, double worldY, double worldZ);
}
