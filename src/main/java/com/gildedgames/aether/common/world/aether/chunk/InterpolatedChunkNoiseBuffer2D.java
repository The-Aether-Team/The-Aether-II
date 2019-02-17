package com.gildedgames.aether.common.world.aether.chunk;

import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer2D;

public class InterpolatedChunkNoiseBuffer2D implements IChunkNoiseBuffer2D
{
	private final float[] samples;

	private final int sampleCount;

	private final float noiseScaleFactor;

	public InterpolatedChunkNoiseBuffer2D(final float[] samples, final int sampleCount, final double noiseScaleFactor)
	{
		this.samples = samples;
		this.sampleCount = sampleCount;

		this.noiseScaleFactor = 1.0f / (float) noiseScaleFactor;
	}

	@Override
	public float get(int x, int z)
	{
		final float x0 = x * this.noiseScaleFactor;
		final float z0 = z * this.noiseScaleFactor;

		final int integerX = (int) x0;
		final int integerZ = (int) z0;

		final float fractionX = x0 - integerX;
		final float fractionZ = z0 - integerZ;

		final float a = this.samples[(integerX * this.sampleCount) + integerZ];
		final float b = this.samples[(integerX * this.sampleCount) + integerZ + 1];

		final float c = this.samples[((integerX + 1) * this.sampleCount) + integerZ];
		final float d = this.samples[((integerX + 1) * this.sampleCount) + integerZ + 1];

		final float r1 = a + (b - a) * fractionZ;
		final float r2 = c + (d - c) * fractionZ;

		return r1 + (r2 - r1) * fractionX;
	}

}
