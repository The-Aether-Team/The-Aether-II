package com.gildedgames.aether.common.world.decorations.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.orbis.lib.util.ArrayHelper;
import com.gildedgames.orbis.lib.world.WorldSlice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class WorldGenFoliage
{
	private final IBlockState[] states;

	private final int max;

	private IBlockState[] statesCanPlaceOn = new IBlockState[] { BlocksAether.aether_grass.getDefaultState() };

	public WorldGenFoliage(final int max, final IBlockState... states)
	{
		if (states.length == 0)
		{
			throw new IllegalArgumentException("At least one state must be specified");
		}

		this.max = max;
		this.states = states;
	}

	public void setStatesToPlaceOn(final IBlockState... states)
	{
		this.statesCanPlaceOn = states;
	}

	public boolean generate(final WorldSlice slice, final Random rand, int x, int z)
	{
		BlockPos pos = slice.getHighestBlockPos(x, z);

		BlockPos.MutableBlockPos randomPos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos randomPosDown = new BlockPos.MutableBlockPos();

		for (int attempts = 0; attempts < this.max; attempts++)
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

			randomPosDown.setPos(randomPos.getX(), randomPos.getY() - 1, randomPos.getZ());

			final IBlockState below = slice.getBlockState(randomPosDown);

			if (ArrayHelper.contains(this.statesCanPlaceOn, below))
			{
				final IBlockState chosen;

				if (this.states.length == 1)
				{
					chosen = this.states[0];
				}
				else
				{
					chosen = this.states[rand.nextInt(this.states.length)];
				}

				slice.setBlockState(randomPos, chosen);
			}
		}

		return true;
	}
}
