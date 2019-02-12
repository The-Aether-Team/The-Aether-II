package com.gildedgames.aether.common.world.aether.chunk;

import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer;

public class ChunkNoiseBuffer implements IChunkNoiseBuffer
{
	private final double[] interpolatedSamples;

	public ChunkNoiseBuffer(double[] samples, final double noiseScaleFactor, final int sampleCount)
	{
		this.interpolatedSamples = new double[16 * 16];

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				this.set(x, z, interpolate(samples, x, z, noiseScaleFactor, sampleCount));
			}
		}
	}

	private static double interpolate(double[] source, final int x, final int z, final double noiseScaleFactor, final int sampleCount)
	{
		final double x0 = x / noiseScaleFactor;
		final double z0 = z / noiseScaleFactor;

		final int integerX = (int) x0;
		final int integerZ = (int) z0;

		final double fractionX = x0 - integerX;
		final double fractionZ = z0 - integerZ;

		final double a = source[(integerX * sampleCount) + (integerZ)];
		final double b = source[(integerX * sampleCount) + (integerZ + 1)];

		final double c = source[((integerX + 1) * sampleCount) + (integerZ)];
		final double d = source[((integerX + 1) * sampleCount) + (integerZ + 1)];

		return (1.0 - fractionX) * ((1.0 - fractionZ) * a + fractionZ * b) +
				fractionX * ((1.0 - fractionZ) * c + fractionZ * d);
	}

	@Override
	public double get(int x, int z)
	{
		return this.interpolatedSamples[x << 4 | z];
	}

	@Override
	public void set(int x, int z, double val)
	{
		this.interpolatedSamples[x << 4 | z] = val;
	}
}
