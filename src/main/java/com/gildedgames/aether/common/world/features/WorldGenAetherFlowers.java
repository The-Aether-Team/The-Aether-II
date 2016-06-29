package com.gildedgames.aether.common.world.features;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenAetherFlowers extends WorldGenerator
{
	private final IBlockState state;

	private final int max;

	public WorldGenAetherFlowers(IBlockState state, int max)
	{
		this.state = state;
		this.max = max;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		for (int i = 0; i < this.max; ++i)
		{
			BlockPos randomPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (world.isAirBlock(randomPos) && (randomPos.getY() < 255) && this.state.getBlock().canPlaceBlockAt(world, randomPos))
			{
				world.setBlockState(randomPos, this.state, 2);
			}
		}

		return true;
	}
}
