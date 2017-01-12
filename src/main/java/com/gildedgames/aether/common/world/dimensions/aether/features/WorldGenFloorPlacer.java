package com.gildedgames.aether.common.world.dimensions.aether.features;

import java.util.List;
import java.util.Random;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFloorPlacer extends WorldGenerator
{
	private final IBlockState[] states;

	private final int amount;

	private List<IBlockState> statesCanPlaceOn = Lists.newArrayList();

	public WorldGenFloorPlacer(IBlockState... states)
	{
		this(-1, states);
	}

	public WorldGenFloorPlacer(final int amount, final IBlockState... states)
	{
		this.states = states;
		this.amount = amount;
	}

	public void setStatesToPlaceOn(IBlockState... states)
	{
		this.statesCanPlaceOn = Lists.newArrayList(states);
	}

	@Override
	public boolean generate(final World world, final Random rand, BlockPos pos)
	{
		pos = world.getTopSolidOrLiquidBlock(pos);

		int count = 0;

		for (int attempts = 0; attempts < 128; attempts++)
		{
			final BlockPos randomPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (!world.isBlockLoaded(randomPos))
			{
				return false;
			}

			final IBlockState chosen = this.states[rand.nextInt(this.states.length)];

			final IBlockState below = world.getBlockState(randomPos.down());

			if (world.isAirBlock(randomPos) && ((this.statesCanPlaceOn.isEmpty() && below == BlocksAether.aether_grass.getDefaultState()) || this.statesCanPlaceOn.contains(below)))
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
