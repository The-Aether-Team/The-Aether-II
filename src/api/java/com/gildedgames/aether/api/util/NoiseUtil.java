package com.gildedgames.aether.api.util;

public class NoiseUtil
{

	public static float lerpReverse(final float b, final float a, final float f)
	{
		return (a * (1.0F - f)) + (b * f);
	}

	public static float lerp(final float a, final float b, final float f)
	{
		return (a * (1.0F - f)) + (b * f);
	}

	public static double lerp(final double a, final double b, final double f)
	{
		return (a * (1.0 - f)) + (b * f);
	}

	public static double genNoise(final OpenSimplexNoise noise, final double nx, final double nz)
	{
		// Generate evalNormalised for X/Z coordinate
		final double noise1 = noise.eval(nx, nz);
		final double noise2 = 0.5D * noise.eval(nx * 8D, nz * 8D);
		final double noise3 = 0.25D * noise.eval(nx * 16D, nz * 16D);
		final double noise4 = 0.1D * noise.eval(nx * 32D, nz * 32D);

		// Averages evalNormalised samples linearly
		final double sample = (noise1 + noise2 + noise3 + noise4) / 4.0D;

		return sample;
	}

	public static double genNoise(final OpenSimplexNoise noise, final double nx, final double ny, final double nz)
	{
		// Generate evalNormalised for X/Z coordinate
		final double noise1 = noise.eval(nx, ny, nz);
		final double noise2 = 0.5D * noise.eval(nx * 8D, ny * 8D, nz * 8D);
		final double noise3 = 0.25D * noise.eval(nx * 16D, ny * 16D, nz * 16D);
		final double noise4 = 0.1D * noise.eval(nx * 32D, ny * 32D, nz * 32D);

		// Averages evalNormalised samples linearly
		final double sample = (noise1 + noise2 + noise3 + noise4) / 4.0D;

		return sample;
	}

	public static double normalise(final double value)
	{
		if (value >= 0)
		{
			return Math.min(1.0, 0.5 + (value / 2.0));
		}

		return Math.max(0.0, 0.5 - (Math.abs(value) / 2.0));
	}

	public static double something(final OpenSimplexNoise noise, final double x, final double z)
	{
		double frequency = 0.5;
		double amplitude = 1.5;
		final double lacunarity = 3.0;
		final double gain = 0.25;
		final int octaves = 3;

		double total = 0.0;

		for (int i = 0; i < octaves; ++i)
		{
			total += noise.eval(x * frequency, z * frequency) * amplitude;
			frequency *= lacunarity;
			amplitude *= gain;
		}

		return total;
	}

}
