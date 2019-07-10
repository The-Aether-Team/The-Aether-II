package com.gildedgames.aether.common.world.noise;

import com.gildedgames.aether.api.world.noise.IChunkNoiseBuffer3D;

public class InterpolatedChunkNoiseBuffer3D implements IChunkNoiseBuffer3D
{
	private final float[][] samples;

	private final float noiseScaleFactorXZ;

	private final int sampleCountXZ;

	public InterpolatedChunkNoiseBuffer3D(float[][] samples, final double noiseScaleFactorXZ, final int sampleCountXZ)
	{
		this.samples = samples;

		this.noiseScaleFactorXZ = 1.0f / (float) noiseScaleFactorXZ;
		this.sampleCountXZ = sampleCountXZ;
	}

	@Override
	public float get(int x, int y, int z)
	{
		final float x0 = x * this.noiseScaleFactorXZ;
		final float z0 = z * this.noiseScaleFactorXZ;

		final int integerX = (int) x0;
		final int integerZ = (int) z0;

		final float[] row = this.samples[y];

		final float a = row[(integerX * this.sampleCountXZ) + integerZ];
		final float b = row[(integerX * this.sampleCountXZ) + integerZ + 1];

		final float c = row[((integerX + 1) * this.sampleCountXZ) + integerZ];
		final float d = row[((integerX + 1) * this.sampleCountXZ) + integerZ + 1];

		final float r1 = a + (b - a) * (z0 - integerZ);
		final float r2 = c + (d - c) * (z0 - integerZ);

		return r1 + (r2 - r1) * (x0 - integerX);
	}

}
