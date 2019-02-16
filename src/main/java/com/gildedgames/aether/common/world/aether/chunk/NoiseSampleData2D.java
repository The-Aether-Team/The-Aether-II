package com.gildedgames.aether.common.world.aether.chunk;

import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer2D;

public class NoiseSampleData2D
{
	private final double[] data;

	private final double noiseScaleFactor;

	private final int sampleCount;

	public NoiseSampleData2D(double noiseScaleFactor, int sampleCount)
	{
		this.noiseScaleFactor = noiseScaleFactor;
		this.sampleCount = sampleCount;

		this.data = new double[this.sampleCount * this.sampleCount];
	}

	public void set(int x, int z, double i)
	{
		this.data[(x * this.sampleCount) + z] = i;
	}

	public double get(int x, int z)
	{
		return this.data[(x * this.sampleCount) + z];
	}

	public IChunkNoiseBuffer2D createInterpolatedNoiseBuffer()
	{
		return new InterpolatedChunkNoiseBuffer2D(this.data,  this.noiseScaleFactor, this.sampleCount);
	}

}
