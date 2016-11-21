package com.gildedgames.aether.common.world.dimensions.aether.features;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenFloorPlacer extends WorldGenerator
{

	private final IBlockState[] states;

	private final int amount;

	public WorldGenFloorPlacer(IBlockState... states)
	{
		this(-1, states);
	}

	public WorldGenFloorPlacer(int amount, IBlockState... states)
	{
		this.states = states;
		this.amount = amount;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		IBlockState state;

		while (pos.getY() > 0)
		{
			state = world.getBlockState(pos);

			if (!state.getBlock().isAir(this.states[0], world, pos) && !state.getBlock().isLeaves(this.states[0], world, pos))
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

			IBlockState chosen = this.states[rand.nextInt(this.states.length)];

			if (world.isAirBlock(randomPos) && chosen.getBlock().canPlaceBlockAt(world, randomPos))
			{
				world.setBlockState(randomPos, chosen, 2);

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
