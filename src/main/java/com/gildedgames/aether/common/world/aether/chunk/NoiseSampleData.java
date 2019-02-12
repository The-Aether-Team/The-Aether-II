package com.gildedgames.aether.common.world.aether.chunk;

import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer;

public class NoiseSampleData
{
	private final double[] data;

	private final double noiseScaleFactor;

	private final int sampleCount;

	public NoiseSampleData(double noiseScaleFactor, int sampleCount)
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

	public IChunkNoiseBuffer createChunkBuffer()
	{
		return new ChunkNoiseBuffer(this.data,  this.noiseScaleFactor, this.sampleCount);
	}

}
