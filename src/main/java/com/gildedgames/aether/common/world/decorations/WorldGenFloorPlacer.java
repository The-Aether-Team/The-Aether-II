package com.gildedgames.aether.common.world.decorations;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.decoration.WorldDecorationGenerator;
import com.gildedgames.orbis.lib.util.ArrayHelper;
import com.gildedgames.orbis.lib.world.WorldSlice;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class WorldGenFloorPlacer implements WorldDecorationGenerator
{
	private final StateFetcher stateFetcher;

	private final Function<Random, List<BlockState>> stateDefiner;

	private final int amount;

	private BlockState[] statesCanPlaceOn = new BlockState[] { BlocksAether.aether_grass.getDefaultState() };

	public WorldGenFloorPlacer(StateFetcher stateFetcher, Function<Random, List<BlockState>> stateDefiner)
	{
		this(-1, stateFetcher, stateDefiner);
	}

	public WorldGenFloorPlacer(final int amount, final StateFetcher stateFetcher, Function<Random, List<BlockState>> stateDefiner)
	{
		this.stateFetcher = stateFetcher;
		this.stateDefiner = stateDefiner;
		this.amount = amount;
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

		final List<BlockState> states = this.stateDefiner.apply(rand);

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

			final BlockState below = slice.getBlockState(randomPos.down());

            if (ArrayHelper.contains(this.statesCanPlaceOn, below))
            {
                final BlockState chosen = this.stateFetcher.fetch(rand, states);

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
		BlockState fetch(Random rand, List<BlockState> states);
	}
}
