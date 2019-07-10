package com.gildedgames.aether.common.world.noise.impl;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.noise.INoiseGenerator2D;
import net.minecraft.util.math.MathHelper;

public class NoiseGeneratorSoilDepth implements INoiseGenerator2D
{
	private static final double NOISE_SCALE = 0.0625D;

	private final OpenSimplexNoise noise;

	public NoiseGeneratorSoilDepth(OpenSimplexNoise noise)
	{
		this.noise = noise;
	}

	@Override
	public double generate(double worldX, double worldZ)
	{
		double nx = worldX / NOISE_SCALE;
		double nz = worldZ / NOISE_SCALE;

		return MathHelper.ceil(((this.noise.eval(nx, nz) - 0.5D) * 2.0D)) + 3.0D;
	}
}
