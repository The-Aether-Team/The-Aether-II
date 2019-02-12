package com.gildedgames.aether.common.world.aether.noise;

import com.gildedgames.aether.api.util.OpenSimplexNoise;
import com.gildedgames.aether.api.world.noise.INoiseGenerator;
import net.minecraft.util.math.MathHelper;

public class NoiseGeneratorSoilDepth implements INoiseGenerator
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
