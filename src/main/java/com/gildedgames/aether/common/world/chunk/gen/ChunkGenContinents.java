package com.gildedgames.aether.common.world.chunk.gen;

import com.gildedgames.aether.common.util.XORShiftRandom;
import com.gildedgames.aether.common.world.chunk.gen.ChunkGenFloatingIsland.GenProps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorSimplex;

import java.util.Random;

public class ChunkGenContinents implements ChunkGen
{

	private NoiseGeneratorSimplex noise;

	private Random rand;

	private double[] noiseBuffer = new double[256];

	private final GenProps GEN_PROPS;

	public ChunkGenContinents(World world, long seed, GenProps genProps)
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

				double value = this.GEN_PROPS.noiseValue(this.noise, globalX, globalZ);

				this.noiseBuffer[x + z * 16] = value;
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
					//System.out.println(noiseValue)

					double nx = posX + x;
					double nz = posZ + z;

					double d = 2 * Math.max(Math.abs(nx), Math.abs(nz));

					double a = 0.05D;
					double b = 1.07D;
					double c = 2.00D;

					double evalHeight = MathHelper.floor_double((noiseValue) * this.GEN_PROPS.topHeightFactor());

					evalHeight = (evalHeight + a) * (1 - b * Math.pow(d, c));

					for (int y = 0; y < Math.min(200, evalHeight); y++)
					{
						IBlockState state = this.GEN_PROPS.state(noiseValue, posX + x, posZ + z);

						primer.setBlockState(x, this.GEN_PROPS.yLevel() + y, z, state);
					}

					evalHeight = MathHelper.floor_double((noiseValue) * this.GEN_PROPS.bottomHeightFactor());

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
