package com.gildedgames.aether.common.world.chunk.gen;

import com.gildedgames.aether.common.world.chunk.gen.ChunkGenFloatingIsland.GenProps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.NoiseGeneratorSimplex;

public class GenPropsSmallIslands implements GenProps
{

	private IBlockState fillWith;

	private final double FEATURE_SIZE;

	private final int yLevel;

	public GenPropsSmallIslands(IBlockState fillWith, double featureSize, int yLevel)
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

		double noise1 = 1.0D * noise.getValue(nx, nz);
		double noise2 = 0.5D * noise.getValue(2.0D * nx, 2.0D * nz);
		double noise3 = 0.25D * noise.getValue(4.0D * nx, 2.0D * nz);
		double noise4 = 0.1D * noise.getValue(4.0D * nx, 4.0D * nz);

		return noise1 + noise2 + noise3 + noise4;
	}

	@Override
	public IBlockState state(double noiseValue, double x, double z)
	{
		return this.fillWith;
	}

	@Override
	public boolean validForGen(double noiseValue, double x, double z)
	{
		return noiseValue > 0.8D;
	}

	@Override
	public double noiseOffset()
	{
		return 0.8D;
	}

	@Override
	public int topHeightFactor()
	{
		return 10;
	}

	@Override
	public int bottomHeightFactor()
	{
		return 10;
	}

}
