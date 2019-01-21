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

public class WorldGenFloorPlacer extends WorldGenerator implements IWorldGen
{
	private final StateFetcher stateFetcher;

	private final Function<Random, List<IBlockState>> stateDefiner;

	private final int amount;

	private List<IBlockState> statesCanPlaceOn = Lists.newArrayList();

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
		this.statesCanPlaceOn = Lists.newArrayList(states);
	}

	@Override
	public boolean generate(final World world, final Random rand, final BlockPos pos)
	{
		return this.generate(new BlockAccessExtendedWrapper(world), world, rand, pos, false);
	}

	@Override
	public boolean generate(final IBlockAccessExtended blockAccess, final World world, final Random rand, BlockPos pos, final boolean centered)
	{
		if (!blockAccess.canAccess(pos))
		{
			return false;
		}

		pos = blockAccess.getTopPos(pos);

		int count = 0;

		final List<IBlockState> states = this.stateDefiner.apply(rand);

		for (int attempts = 0; attempts < 128; attempts++)
		{
			final BlockPos randomPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (!blockAccess.canAccess(randomPos))
			{
				return false;
			}

			final IBlockState chosen = this.stateFetcher.fetch(rand, states);

			if (!blockAccess.canAccess(randomPos.down()))
			{
				return false;
			}

			final IBlockState below = blockAccess.getBlockState(randomPos.down());

			if (blockAccess.isAirBlock(randomPos) && ((this.statesCanPlaceOn.isEmpty() && chosen.getBlock().canPlaceBlockAt(world, randomPos))
					|| this.statesCanPlaceOn.contains(below)))
			{
				blockAccess.setBlockState(randomPos, chosen, 2);

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

	@Override
	public boolean generate(final IBlockAccessExtended blockAccess, final World world, final Random rand, final BlockPos position)
	{
		return this.generate(blockAccess, world, rand, position, false);
	}

	public interface StateFetcher
	{
		IBlockState fetch(Random rand, List<IBlockState> states);
	}
}
