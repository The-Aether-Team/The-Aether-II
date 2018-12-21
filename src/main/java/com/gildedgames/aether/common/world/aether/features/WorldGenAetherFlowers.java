package com.gildedgames.aether.common.world.aether.features;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;
import java.util.function.Function;

public class WorldGenAetherFlowers extends WorldGenerator
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

	@Override
	public boolean generate(final World world, final Random rand, final BlockPos pos)
	{
		int i = 0;

		while (i < this.max)
		{
			final BlockPos randomPos = pos.add(
					rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			i++;

			if (!world.isBlockLoaded(randomPos))
			{
				continue;
			}

			if ((this.canPlaceOn == null || this.canPlaceOn.apply(world.getBlockState(randomPos.down()))) && world.isAirBlock(randomPos) && (randomPos.getY()
					< world
					.getActualHeight()) && this.state.getBlock().canPlaceBlockAt(world, randomPos))
			{
				world.setBlockState(randomPos, this.state, 2);
			}
		}

		return true;
	}
}
