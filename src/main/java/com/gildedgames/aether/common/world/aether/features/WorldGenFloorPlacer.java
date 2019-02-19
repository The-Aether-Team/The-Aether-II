package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.api.world.generation.WorldDecorationGenerator;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.orbis_api.util.ArrayHelper;
import com.gildedgames.orbis_api.world.WorldSlice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class WorldGenFloorPlacer implements WorldDecorationGenerator
{
	private final StateFetcher stateFetcher;

	private final Function<Random, List<IBlockState>> stateDefiner;

	private final int amount;

	private IBlockState[] statesCanPlaceOn = new IBlockState[] { BlocksAether.aether_grass.getDefaultState() };

	public WorldGenFloorPlacer(StateFetcher stateFetcher, Function<Random, List<IBlockState>> stateDefiner)
	{
		this(-1, stateFetcher, stateDefiner);
	}

	public WorldGenFloorPlacer(final int amount, final StateFetcher stateFetcher, Function<Random, List<IBlockState>> stateDefiner)
	{
		this.stateFetcher = stateFetcher;
		this.stateDefiner = stateDefiner;
		this.amount = amount;
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

		final List<IBlockState> states = this.stateDefiner.apply(rand);

		for (int attempts = 0; attempts < 128; attempts++)
		{
			randomPos.setPos(
					pos.getX() + (rand.nextInt(16) - 8),
					pos.getY() + (rand.nextInt(8) - 4),
					pos.getZ() + (rand.nextInt(16) - 8)
			);

			if (!slice.isAirBlock(randomPos))
			{
				continue;
			}

			final IBlockState below = slice.getBlockState(randomPos.down());

            if (ArrayHelper.contains(this.statesCanPlaceOn, below))
            {
                final IBlockState chosen = this.stateFetcher.fetch(rand, states);

                slice.setBlockState(randomPos, chosen);

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

	public interface StateFetcher
	{
		IBlockState fetch(Random rand, List<IBlockState> states);
	}
}
