package com.gildedgames.aether.common.util;

import com.gildedgames.aether.api.world.islands.INoiseProvider;
import net.minecraft.util.math.MathHelper;

/**
 * Generates 2D noise using the provided noise generator and linearly interpolates between values.
 */
public abstract class ChunkNoiseGenerator implements INoiseProvider
{
	private final int posX, posZ;

	private final double noiseScale;

	private final int noiseSamples;

	private final double[] interpolatedSamples;

	private final int offsetX, offsetZ;

	private final double scale;

	public ChunkNoiseGenerator(int posX, int posZ, double noiseScale, int noiseSamples, int offsetX, int offsetZ, double scale)
	{
		this.posX = posX;
		this.posZ = posZ;

		this.noiseScale = noiseScale;
		this.noiseSamples = noiseSamples;

		this.offsetX = offsetX;
		this.offsetZ = offsetZ;

		this.scale = scale;

		this.interpolatedSamples = this.generateNoise();
	}

	private double[] generateNoise()
	{
		double[] samples = new double[this.noiseSamples * this.noiseSamples];

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

				samples[x + (z * this.noiseSamples)] = this.getSample(nx, nz);
			}
		}

		return this.interpolateSamples(samples);
	}

	private double[] interpolateSamples(double[] samples)
	{
		double[] interpolatedSamples = new double[16 * 16];

		for (int x = 0; x < 16; x++)
		{
			for (int z = 0; z < 16; z++)
			{
				interpolatedSamples[x << 4 | z] = this.interpolate(samples, x, z);
			}
		}

		return interpolatedSamples;
	}

	private double interpolate(final double[] samples, final int x, final int z)
	{
		final double x0 = (double) x / this.noiseScale;
		final double z0 = (double) z / this.noiseScale;

		final int integerX = MathHelper.floor(x0);
		final double fractionX = x0 - integerX;

		final int integerZ = MathHelper.floor(z0);
		final double fractionZ = z0 - integerZ;

		final double a = samples[integerX + (integerZ * this.noiseSamples)];
		final double b = samples[integerX + ((integerZ + 1) * this.noiseSamples)];
		final double c = samples[integerX + 1 + (integerZ * this.noiseSamples)];
		final double d = samples[integerX + 1 + ((integerZ + 1) * this.noiseSamples)];

		return (1.0 - fractionX) * ((1.0 - fractionZ) * a + fractionZ * b) +
				fractionX * ((1.0 - fractionZ) * c + fractionZ * d);
	}

	protected abstract double getSample(double nx, double nz);

	@Override
	public double getNoiseValue(int x, int z)
	{
		return this.interpolatedSamples[x << 4 | z];
	}

}
