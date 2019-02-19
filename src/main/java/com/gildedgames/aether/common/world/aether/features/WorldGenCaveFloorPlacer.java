package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.api.world.generation.WorldDecorationGenerator;
import com.gildedgames.orbis_api.world.WorldSlice;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class WorldGenCaveFloorPlacer implements WorldDecorationGenerator
{

	private final Function<Random, IBlockState> statesToPlace;

	private final int max;

	private List<IBlockState> statesCanPlaceOn = Lists.newArrayList();

	public WorldGenCaveFloorPlacer(Function<Random, IBlockState> statesToPlace, final int max)
	{
		this.statesToPlace = statesToPlace;
		this.max = max;
	}

	public void setStatesToPlaceOn(final IBlockState... states)
	{
		this.statesCanPlaceOn = Lists.newArrayList(states);
	}

	@Override
	public boolean generate(WorldSlice slice, Random rand, BlockPos pos)
	{
		int count = 0;
		for (int attempts = 0; attempts < 128; attempts++)
		{
			final BlockPos randomPos =
					pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			final IBlockState below = slice.getBlockState(randomPos.down());

			if (slice.isAirBlock(randomPos))
			{
				IBlockState toPlace = this.statesToPlace.apply(rand);

				if ((this.statesCanPlaceOn.isEmpty() && toPlace.getBlock().canPlaceBlockAt(slice.getWorld(), randomPos)) || this.statesCanPlaceOn.contains(below))
				{
					slice.setBlockState(randomPos, toPlace);

					if (this.max > 0)
					{
						if (count < this.max)
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
		}

		return true;
	}
}
