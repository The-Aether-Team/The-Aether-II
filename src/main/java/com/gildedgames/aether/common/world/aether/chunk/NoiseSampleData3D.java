package com.gildedgames.aether.common.world.aether.chunk;

import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer3D;

public class NoiseSampleData3D
{
	private final float[][] data;

	private final double noiseScaleFactorXZ, noiseScaleFactorY;

	private final int sampleCountXZ;

	private final int sampleCountY;

	public NoiseSampleData3D(double noiseScaleFactorXZ, double noiseScaleFactorY, int sampleCountXZ, int sampleCountY)
	{
		this.noiseScaleFactorXZ = noiseScaleFactorXZ;
		this.noiseScaleFactorY = noiseScaleFactorY;
		this.sampleCountXZ = sampleCountXZ;
		this.sampleCountY = sampleCountY;

		this.data = new float[this.sampleCountY][this.sampleCountXZ * this.sampleCountXZ];
	}

	public void set(int x, int y, int z, float i)
	{
		this.data[y][(x * this.sampleCountXZ) + z] = i;
	}

	public IChunkNoiseBuffer3D createInterpolatedNoiseBuffer()
	{
		return new InterpolatedChunkNoiseBuffer3D(this.data, this.noiseScaleFactorXZ, this.sampleCountXZ);
	}
}
