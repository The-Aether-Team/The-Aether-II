package com.gildedgames.aether.common.world.decorations;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.decoration.WorldDecorationGenerator;
import com.gildedgames.orbis.lib.util.ArrayHelper;
import com.gildedgames.orbis.lib.world.WorldSlice;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.function.Function;

public class WorldGenCaveFloorPlacer implements WorldDecorationGenerator
{

	private final Function<Random, BlockState> statesToPlace;

	private final int max;

	private BlockState[] statesCanPlaceOn = new BlockState[] { BlocksAether.holystone.getDefaultState() };

	public WorldGenCaveFloorPlacer(Function<Random, BlockState> statesToPlace, final int max)
	{
		this.statesToPlace = statesToPlace;
		this.max = max;
	}

	public void setStatesToPlaceOn(final BlockState... states)
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
				final BlockState below = slice.getBlockState(randomPos.down());

				if (ArrayHelper.contains(this.statesCanPlaceOn, below))
				{
					final BlockState toPlace = this.statesToPlace.apply(rand);

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
