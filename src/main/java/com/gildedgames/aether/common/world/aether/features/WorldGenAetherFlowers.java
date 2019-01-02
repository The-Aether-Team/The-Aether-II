package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.world.util.WorldSlice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Function;

public class WorldGenAetherFlowers
{
	private final IBlockState state;

	private final int max;

	private final Function<IBlockState, Boolean> canPlaceOn;

	public WorldGenAetherFlowers(final IBlockState state, final int max, Function<IBlockState, Boolean> canPlaceOn)
	{
		this.state = state;
		this.max = max;

		this.canPlaceOn = canPlaceOn;
	}

	public WorldGenAetherFlowers(final IBlockState state, final int max)
	{
		this(state, max, null);
	}

	public boolean generate(final WorldSlice slice, final Random rand, final BlockPos pos)
	{
		World world = slice.getWorld();

		int i = 0;

		while (i < this.max)
		{
			final BlockPos randomPos = pos.add(
					rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			i++;

			if ((this.canPlaceOn == null || this.canPlaceOn.apply(slice.getBlockState(randomPos.down())))
					&& slice.isAirBlock(randomPos) && (randomPos.getY() < world.getActualHeight()) && this.state.getBlock().canPlaceBlockAt(world, randomPos))
			{
				world.setBlockState(randomPos, this.state, 2 | 16);
			}
		}

		return true;
	}
}
