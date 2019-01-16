package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.orbis_api.world.WorldSlice;
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

		BlockPos.PooledMutableBlockPos randomPos = BlockPos.PooledMutableBlockPos.retain();
		BlockPos.PooledMutableBlockPos randomPosDown = BlockPos.PooledMutableBlockPos.retain();

		while (i < this.max)
		{
			randomPos.setPos(
					pos.getX() + (rand.nextInt(16) - 8),
					pos.getY() + (rand.nextInt(8) - 4),
					pos.getZ() + (rand.nextInt(16) - 8)
			);

			randomPosDown.setPos(randomPos);
			randomPosDown.setY(randomPosDown.getY() - 1);

			i++;

			if ((this.canPlaceOn == null || this.canPlaceOn.apply(slice.getBlockState(randomPosDown)))
					&& slice.isAirBlock(randomPos) && (randomPos.getY() < world.getActualHeight()) && this.state.getBlock().canPlaceBlockAt(world, randomPos))
			{
				world.setBlockState(randomPos, this.state, 2 | 16);
			}
		}

		randomPos.release();
		randomPosDown.release();


		return true;
	}
}
