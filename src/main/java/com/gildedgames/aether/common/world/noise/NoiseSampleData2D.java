package com.gildedgames.aether.common.world.noise;

import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer2D;

public class NoiseSampleData2D
{
	private final float[] data;

	private final double noiseScaleFactor;

	private final int sampleCount;

	public NoiseSampleData2D(double noiseScaleFactor, int sampleCount)
	{
		this.noiseScaleFactor = noiseScaleFactor;
		this.sampleCount = sampleCount;

		this.data = new float[this.sampleCount * this.sampleCount];
	}

	public void set(int x, int z, float i)
	{
		this.data[(x * this.sampleCount) + z] = i;
	}

	public float get(int x, int z)
	{
		return this.data[(x * this.sampleCount) + z];
	}

	public IChunkNoiseBuffer2D createInterpolatedNoiseBuffer()
	{
		return new InterpolatedChunkNoiseBuffer2D(this.data, this.sampleCount, this.noiseScaleFactor);
	}

}
