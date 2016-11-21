package com.gildedgames.aether.common.world.dimensions.aether.features;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenFloorPlacer extends WorldGenerator
{

	private final IBlockState state;

	private final int amount;

	public WorldGenFloorPlacer(IBlockState state)
	{
		this(state, -1);
	}

	public WorldGenFloorPlacer(IBlockState state, int amount)
	{
		this.state = state;
		this.amount = amount;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		IBlockState state;

		while (pos.getY() > 0)
		{
			state = world.getBlockState(pos);

			if (!state.getBlock().isAir(this.state, world, pos) && !state.getBlock().isLeaves(this.state, world, pos))
			{
				break;
			}

			pos = pos.down();
		}

		int i = 0;
		int count = 0;

		while (i < 128)
		{
			BlockPos randomPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			i++;

			if (!world.isAreaLoaded(pos, randomPos))
			{
				return false;
			}

			if (world.isAirBlock(randomPos) && this.state.getBlock().canPlaceBlockAt(world, randomPos))
			{
				world.setBlockState(randomPos, this.state, 2);

				if (this.amount > 0)
				{
					if (count < this.amount)
					{
						count++;
					}
					else
					{
						return true;
					}
				}
			}
		}

		return true;
	}
}
