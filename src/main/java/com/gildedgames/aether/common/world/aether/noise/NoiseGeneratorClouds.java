package com.gildedgames.aether.common.world.aether.noise;

import com.gildedgames.aether.api.util.NoiseUtil;
import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.noise.INoiseGenerator2D;

public class NoiseGeneratorClouds implements INoiseGenerator2D
{
	private static final double SCALE_FACTOR = 100.0D;

	private final OpenSimplexNoise noise;

	public NoiseGeneratorClouds(OpenSimplexNoise noise)
	{
		this.noise = noise;
	}

	@Override
	public double generate(double worldX, double worldZ)
	{
		double sample = NoiseUtil.something(this.noise, worldX / SCALE_FACTOR, worldZ / SCALE_FACTOR);

		return NoiseUtil.normalise(sample);
	}
}
