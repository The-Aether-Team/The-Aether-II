package com.gildedgames.aether.common.world.features;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.gildedgames.aether.common.world.ChunkProviderAether;

public class WorldGenClouds extends WorldGenerator
{

	private IBlockState blockState;

	private int numberOfBlocks;

	private boolean flat;

	public WorldGenClouds(IBlockState blockState, int numberOfBlocks, boolean flat)
	{
		this.blockState = blockState;
		this.numberOfBlocks = numberOfBlocks;
		this.flat = flat;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();

		int xTendency = rand.nextInt(3) - 1;
		int zTendency = rand.nextInt(3) - 1;

		for (int n = 0; n < this.numberOfBlocks; n++)
		{
			x += (rand.nextInt(3) - 1 + xTendency);
			if ((rand.nextBoolean() && !this.flat) || (this.flat && rand.nextInt(10) == 0))
			{
				y += (rand.nextInt(3) - 1);
			}
			z += (rand.nextInt(3) - 1 + zTendency);

			for (int x1 = x; x1 < x + rand.nextInt(4) + 3 * (this.flat ? 3 : 1); x1++)
			{
				for (int y1 = y; y1 < y + rand.nextInt(1) + 2; y1++)
				{

					for (int z1 = z; z1 < z + rand.nextInt(4) + 3 * (this.flat ? 3 : 1); z1++)
					{
						BlockPos pos = new BlockPos(x1, y1, z1);

						if (worldIn.isAirBlock(pos) && Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 * (this.flat ? 3 : 1) + rand.nextInt(2))
						{
							worldIn.setBlockState(pos, this.blockState, ChunkProviderAether.PLACEMENT_FLAG_TYPE);
						}
					}
				}
			}
		}

		return true;
	}

}
