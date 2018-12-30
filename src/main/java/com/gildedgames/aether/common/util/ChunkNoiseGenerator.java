package com.gildedgames.aether.common.util;

import com.gildedgames.aether.api.util.OpenSimplexNoise;

/**
 * Generates 2D noise using the provided noise generator and linearly interpolates between values.
 */
public abstract class ChunkNoiseGenerator
{
	protected final OpenSimplexNoise generator;

	private final double[] data;

	protected final int posX, posZ;

	private final double noiseScale;

	private final int noiseSamples;

	protected final int offsetX, offsetZ;

	protected final double scale;

	public ChunkNoiseGenerator(OpenSimplexNoise generator, int posX, int posZ, double noiseScale, int noiseSamples, int offsetX, int offsetZ, double scale)
	{
		this.generator = generator;

		this.posX = posX;
		this.posZ = posZ;

		this.noiseScale = noiseScale;
		this.noiseSamples = noiseSamples;

		this.offsetX = offsetX;
		this.offsetZ = offsetZ;

		this.scale = scale;

		this.data = new double[this.noiseSamples * this.noiseSamples];

		this.generateNoise();
	}

	private void generateNoise()
	{
		for (int x = 0; x < this.noiseSamples; x++)
		{
			// Creates world coordinate and normalized evalNormalised coordinate
			double worldX = this.posX - (x == 0 ? this.noiseScale - 1 : 0) + (x * (16D / this.noiseSamples));
			double nx = (worldX + this.offsetX) / this.scale;

			for (int z = 0; z < this.noiseSamples; z++)
			{
				// Creates world coordinate and normalized evalNormalised coordinate
				double worldZ = this.posZ - (z == 0 ? this.noiseScale - 1 : 0) + (z * (16.0D / this.noiseSamples));
				double nz = (worldZ + this.offsetZ) / this.scale;

				this.data[x + (z * this.noiseSamples)] = this.sample(nx, nz);
			}
		}
	}

	protected abstract double sample(double nx, double nz);

	public double interpolate(final int x, final int z)
	{
		final double x0 = (double) x / this.noiseScale;
		final double z0 = (double) z / this.noiseScale;

		final int integerX = (int) Math.floor(x0);
		final double fractionX = x0 - integerX;

		final int integerZ = (int) Math.floor(z0);
		final double fractionZ = z0 - integerZ;

		final double a = this.data[integerX + (integerZ * this.noiseSamples)];
		final double b = this.data[integerX + ((integerZ + 1) * this.noiseSamples)];
		final double c = this.data[integerX + 1 + (integerZ * this.noiseSamples)];
		final double d = this.data[integerX + 1 + ((integerZ + 1) * this.noiseSamples)];

		return (1.0 - fractionX) * ((1.0 - fractionZ) * a + fractionZ * b) +
				fractionX * ((1.0 - fractionZ) * c + fractionZ * d);
	}

}
