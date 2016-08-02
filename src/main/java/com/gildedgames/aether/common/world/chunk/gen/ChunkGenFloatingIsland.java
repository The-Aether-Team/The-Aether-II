package com.gildedgames.aether.common.world.chunk.gen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorSimplex;

import com.gildedgames.aether.common.util.XORShiftRandom;

public class ChunkGenFloatingIsland implements ChunkGen
{

	private NoiseGeneratorSimplex noise;

	private Random rand;

	private double[] noiseBuffer = new double[256];

	private final GenProps GEN_PROPS;

	public static interface GenProps
	{

		int yLevel();

		double noiseValue(NoiseGeneratorSimplex noise, double x, double z);

		IBlockState state(double noiseValue, double x, double z);

		boolean validForGen(double noiseValue, double x, double z);

		double noiseOffset();

		int topHeightFactor();

		int bottomHeightFactor();

	}

	public ChunkGenFloatingIsland(World world, long seed, GenProps genProps)
	{
		this.rand = new XORShiftRandom(seed);
		this.noise = new NoiseGeneratorSimplex(this.rand);

		this.GEN_PROPS = genProps;
	}

	@Override
	public void prepare(int chunkX, int chunkZ, double posX, double posZ)
	{
		for (int z = 0; z < 16; z++)
		{
			for (int x = 0; x < 16; x++)
			{
				double globalX = posX + x;
				double globalZ = posZ + z;

				this.noiseBuffer[x + z * 16] = this.GEN_PROPS.noiseValue(this.noise, globalX, globalZ);
			}
		}
	}

	@Override
	public void build(ChunkPrimer primer, int chunkX, int chunkZ, double posX, double posZ)
	{
		for (int z = 0; z < 16; z++)
		{
			for (int x = 0; x < 16; x++)
			{
				double noiseValue = this.noiseBuffer[x + z * 16];

				if (this.GEN_PROPS.validForGen(noiseValue, posX + x, posZ + z))
				{
					int evalHeight = MathHelper.floor_double((noiseValue - this.GEN_PROPS.noiseOffset()) * this.GEN_PROPS.topHeightFactor());

					for (int y = 0; y < evalHeight; y++)
					{
						IBlockState state = this.GEN_PROPS.state(noiseValue, posX + x, posZ + z);

						primer.setBlockState(x, this.GEN_PROPS.yLevel() + (int) (y), z, state);
					}

					evalHeight = MathHelper.floor_double((noiseValue - this.GEN_PROPS.noiseOffset()) * this.GEN_PROPS.bottomHeightFactor());

					for (int y = 0; y < evalHeight; y++)
					{
						IBlockState state = this.GEN_PROPS.state(noiseValue, posX + x, posZ + z);

						//primer.setBlockState(x, this.GEN_PROPS.yLevel() - y, z, state);
					}
				}
			}
		}
	}

}
