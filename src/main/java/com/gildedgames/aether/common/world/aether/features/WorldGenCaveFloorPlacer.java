package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.orbis_api.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis_api.processing.IBlockAccessExtended;
import com.gildedgames.orbis_api.world.IWorldGen;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class WorldGenCaveFloorPlacer extends WorldGenerator implements IWorldGen
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
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		return this.generate(new BlockAccessExtendedWrapper(worldIn), worldIn, rand, position, false);
	}

	@Override
	public boolean generate(IBlockAccessExtended blockAccess, World world, Random rand, BlockPos position, boolean centered)
	{
		if (!blockAccess.canAccess(position))
		{
			return false;
		}

		int count = 0;
		for (int attempts = 0; attempts < 128; attempts++)
		{
			final BlockPos randomPos =
					position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (!blockAccess.canAccess(randomPos))
			{
				return false;
			}

			if (!blockAccess.canAccess(randomPos.down()))
			{
				return false;
			}

			final IBlockState below = blockAccess.getBlockState(randomPos.down());

			if (blockAccess.isAirBlock(randomPos))
			{
				IBlockState toPlace = this.statesToPlace.apply(rand);

				if ((this.statesCanPlaceOn.isEmpty() && toPlace.getBlock().canPlaceBlockAt(world, randomPos)) || this.statesCanPlaceOn.contains(below))
				{
					blockAccess.setBlockState(randomPos, toPlace);

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

	@Override
	public boolean generate(IBlockAccessExtended blockAccess, World world, Random rand, BlockPos position)
	{
		return this.generate(blockAccess, world, rand, position, false);
	}
}
