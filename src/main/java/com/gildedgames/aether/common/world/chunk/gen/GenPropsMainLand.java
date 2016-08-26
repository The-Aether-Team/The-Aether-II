package com.gildedgames.aether.common.world.chunk.gen;

import com.gildedgames.aether.common.world.GenUtil;
import com.gildedgames.aether.common.world.chunk.gen.ChunkGenFloatingIsland.GenProps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.NoiseGeneratorSimplex;

public class GenPropsMainLand implements GenProps
{

	private IBlockState fillWith;

	private final double FEATURE_SIZE;

	private final int yLevel;

	public GenPropsMainLand(IBlockState fillWith, double featureSize, int yLevel)
	{
		this.fillWith = fillWith;
		this.FEATURE_SIZE = featureSize;
		this.yLevel = yLevel;
	}

	@Override
	public int yLevel()
	{
		return this.yLevel;
	}

	@Override
	public double noiseValue(NoiseGeneratorSimplex noise, double x, double z)
	{
		double nx = x / this.FEATURE_SIZE;
		double nz = z / this.FEATURE_SIZE;

		return GenUtil.octavedNoise(noise, 10, 120, 1, nx, nz);
	}

	@Override
	public IBlockState state(double noiseValue, double x, double z)
	{
		return this.fillWith;
	}

	@Override
	public boolean validForGen(double noiseValue, double x, double z)
	{
		return noiseValue > -0.3D;
	}

	@Override
	public double noiseOffset()
	{
		return -0.3D;
	}

	@Override
	public int topHeightFactor()
	{
		return 15;
	}

	@Override
	public int bottomHeightFactor()
	{
		return 30;
	}

}
