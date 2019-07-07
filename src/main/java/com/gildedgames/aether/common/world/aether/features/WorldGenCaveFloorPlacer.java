package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.generation.WorldDecorationGenerator;
import com.gildedgames.orbis.lib.util.ArrayHelper;
import com.gildedgames.orbis.lib.world.WorldSlice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.function.Function;

public class WorldGenCaveFloorPlacer implements WorldDecorationGenerator
{

	private final Function<Random, IBlockState> statesToPlace;

	private final int max;

	private IBlockState[] statesCanPlaceOn = new IBlockState[] { BlocksAether.holystone.getDefaultState() };

	public WorldGenCaveFloorPlacer(Function<Random, IBlockState> statesToPlace, final int max)
	{
		this.statesToPlace = statesToPlace;
		this.max = max;
	}

	public void setStatesToPlaceOn(final IBlockState... states)
	{
		this.statesCanPlaceOn = states;
	}

	@Override
	public boolean generate(WorldSlice slice, Random rand, BlockPos pos)
	{
		int count = 0;

		BlockPos.MutableBlockPos randomPos = new BlockPos.MutableBlockPos();

		for (int attempts = 0; attempts < 128; attempts++)
		{
			randomPos.setPos(
					pos.getX() + (rand.nextInt(16) - 8),
					pos.getY() + (rand.nextInt(8) - 4),
					pos.getZ() + (rand.nextInt(16) - 8)
			);

			if (slice.isAirBlock(randomPos))
			{
				final IBlockState below = slice.getBlockState(randomPos.down());

				if (ArrayHelper.contains(this.statesCanPlaceOn, below))
				{
					final IBlockState toPlace = this.statesToPlace.apply(rand);

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
