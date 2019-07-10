package com.gildedgames.aether.common.world.noise.impl;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.islands.IIslandBounds;
import com.gildedgames.aether.api.world.noise.INoiseGenerator2D;

public class NoiseGeneratorIslandTerrain implements INoiseGenerator2D
{
	private static final double NOISE_SCALE = 300.0D;

	private final OpenSimplexNoise noise;

	private final double offsetX, offsetZ;

	public NoiseGeneratorIslandTerrain(OpenSimplexNoise noise, IIslandBounds bounds)
	{
		this(noise, bounds.getMinX(), bounds.getMinZ());
	}

	public NoiseGeneratorIslandTerrain(OpenSimplexNoise noise, IIslandBounds bounds, int offset)
	{
		this(noise, bounds.getMinX() + offset, bounds.getMinZ() + offset);
	}

	public NoiseGeneratorIslandTerrain(OpenSimplexNoise noise, int offsetX, int offsetZ)
	{
		this.noise = noise;

		this.offsetX = offsetX;
		this.offsetZ = offsetZ;
	}

	@Override
	public double generate(double worldX, double worldZ)
	{
		double noiseX = (worldX + this.offsetX) / NOISE_SCALE;
		double noiseZ = (worldZ + this.offsetZ) / NOISE_SCALE;

		// Generate evalNormalised for X/Z coordinate
		final double noise1 = this.noise.eval(noiseX, noiseZ);
		final double noise2 = 0.5D * this.noise.eval(noiseX * 8D, noiseZ * 8D);
		final double noise3 = 0.25D * this.noise.eval(noiseX * 16D, noiseZ * 16D);
		final double noise4 = 0.1D * this.noise.eval(noiseX * 32D, noiseZ * 32D);

		// Averages evalNormalised samples linearly
		return (noise1 + noise2 + noise3 + noise4) / 4.0D;
	}
}
