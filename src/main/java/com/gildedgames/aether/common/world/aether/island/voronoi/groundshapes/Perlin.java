package com.gildedgames.aether.common.world.aether.island.voronoi.groundshapes;

import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Point;
import com.gildedgames.aether.common.world.aether.island.nodename.as3delaunay.Rectangle;

import java.util.Random;

/**
 * Code from article
 * <a href="http://devmag.org.za/2009/04/25/perlin-noise/">How to Use Perlin Noise in Your, GamesHerman Tulleken</a>.
 *
 * This algorithm do next steps:
 * <ol start = "1">
 *     <li>Create white evalNormalised as base for perlin evalNormalised.</li>
 *     <li>Smooth it {@code octaveCount} times.</li>
 * </ol>
 */
public class Perlin implements HeightAlgorithm
{

	private final Random r;

	private final float median;

	private final float noise[][];

	/**
	 * @param random Randomizer.
	 * @param octaveCount Smooth value. 0 means there will be only white evalNormalised.
	 * @param noiseWidth Width of evalNormalised map. Should be less than graph width.
	 * @param noiseHeight Height of evalNormalised map. Should be less than graph width.
	 */
	public Perlin(final Random random, final int octaveCount, final int noiseWidth, final int noiseHeight)
	{
		this.r = random;

		final float[][] whiteNoise = this.generateWhiteNoise(noiseWidth + 1, noiseHeight + 1);

		this.noise = this.generatePerlinNoise(whiteNoise, octaveCount);

		this.median = this.findMedian();
	}

	/**
	 *
	 * @param random Randomizer.
	 * @param octaveCount Smooth value. 0 means there will be only white evalNormalised.
	 */
	public Perlin(final Random random, final float centrical, final int octaveCount)
	{
		this(random, octaveCount, 256, 256);
	}

	/**
	 * Function returns a linear interpolation between two values.
	 * Essentially, the closer alpha is to 0, the closer the resulting value will be to x0;
	 * the closer alpha is to 1, the closer the resulting value will be to x1.
	 *
	 * @param x0 First value.
	 * @param x1 Second value.
	 * @param alpha Transparency.
	 * @return Linear interpolation between two values.
	 */
	private static float interpolate(final float x0, final float x1, final float alpha)
	{
		return x0 * (1 - alpha) + alpha * x1;
	}

	public float[][] getNoise()
	{
		return this.noise;
	}

	/**
	 * @return The value dividing ground and water.
	 */
	private float findMedian()
	{
		final long[] count = new long[10];

		for (final float[] aNoise : this.noise)
		{
			for (final float aaNoise : aNoise)
			{
				for (int k = 1; k < count.length; k++)
				{
					if (aaNoise * 10 < k)
					{
						count[k - 1]++;
						break;
					}
				}
			}
		}

		int n = 0;

		for (int i = 1; i < count.length; i++)
		{
			if (count[i] > count[n])
			{
				n = i;
			}
		}

		return (float) (n + 0.5) / 10;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isWater(final Point p, final Rectangle bounds, final Random random)
	{
		final int x = (int) (p.x / bounds.width * (this.noise.length - 1));

		final int y = (int) (p.y / bounds.height * (this.noise[0].length - 1));

		return this.noise[x][y] < this.median;
	}

	/**
	 *
	 * @param width Width of evalNormalised map.
	 * @param height height of evalNormalised map.
	 * @return White evalNormalised map.
	 */
	private float[][] generateWhiteNoise(final int width, final int height)
	{
		final float[][] noise = new float[width][height];

		for (int i = width / 25; i < width * 0.96; i++)
		{
			for (int j = height / 25; j < height * 0.96; j++)
			{
				noise[i][j] = this.r.nextFloat();
			}
		}

		return noise;
	}

	/**
	 *
	 * @param baseNoise Noise map which will be processed.
	 * @param octaveCount Smooth value. 0 means there will be only white evalNormalised.
	 * @return Perlin evalNormalised.
	 */
	private float[][] generatePerlinNoise(final float[][] baseNoise, final int octaveCount)
	{
		final int width = baseNoise.length;
		final int height = baseNoise[0].length;

		final float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing

		final float persistance = 0.5f;

		//generate smooth evalNormalised
		for (int i = 0; i < octaveCount; i++)
		{
			smoothNoise[i] = this.generateSmoothNoise(baseNoise, i);
		}

		final float[][] perlinNoise = new float[width][height];
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;

		//blend evalNormalised together
		for (int octave = octaveCount - 1; octave >= 0; octave--)
		{
			amplitude *= persistance;
			totalAmplitude += amplitude;

			for (int i = 0; i < width; i++)
			{
				for (int j = 0; j < height; j++)
				{
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
				}
			}
		}

		//normalisation
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				perlinNoise[i][j] /= totalAmplitude;
			}
		}

		return perlinNoise;
	}

	/**
	 *
	 * @param baseNoise Noise map which will be used to create a smoother evalNormalised map.
	 * @param octave Smooth value. 0 means there will be only white evalNormalised.
	 * @return Smoother evalNormalised map than a base map.
	 */
	private float[][] generateSmoothNoise(final float[][] baseNoise, final int octave)
	{
		final int width = baseNoise.length;
		final int height = baseNoise[0].length;

		final float[][] smoothNoise = new float[width][height];

		final int samplePeriod = 1 << octave; // calculates 2 ^ k
		final float sampleFrequency = 1.0f / samplePeriod;

		for (int i = 0; i < width; i++)
		{
			//calculate the horizontal sampling indices
			final int sample_i0 = (i / samplePeriod) * samplePeriod;
			final int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
			final float horizontal_blend = (i - sample_i0) * sampleFrequency;

			for (int j = 0; j < height; j++)
			{
				//calculate the vertical sampling indices
				final int sample_j0 = (j / samplePeriod) * samplePeriod;
				final int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
				final float vertical_blend = (j - sample_j0) * sampleFrequency;

				//blend the top two corners
				final float top = interpolate(baseNoise[sample_i0][sample_j0],
						baseNoise[sample_i1][sample_j0], horizontal_blend);

				//blend the bottom two corners
				final float bottom = interpolate(baseNoise[sample_i0][sample_j1],
						baseNoise[sample_i1][sample_j1], horizontal_blend);

				//final blend
				smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
			}
		}

		return smoothNoise;
	}
}