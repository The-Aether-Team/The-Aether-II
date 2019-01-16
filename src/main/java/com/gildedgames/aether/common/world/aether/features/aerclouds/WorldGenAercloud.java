package com.gildedgames.aether.common.world.aether.features.aerclouds;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenAercloud extends WorldGenerator
{
	protected final IBlockState state;

	protected final int numberOfBlocks;

	protected final boolean isFlat;

	public WorldGenAercloud(final IBlockState state, final int numberOfBlocks, final boolean isFlat)
	{
		this.state = state;
		this.numberOfBlocks = numberOfBlocks;
		this.isFlat = isFlat;
	}

	public IBlockState getAercloudState(final Random random)
	{
		return this.state;
	}

	@Override
	public boolean generate(final World world, final Random rand, final BlockPos position)
	{
		final IBlockState state = this.getAercloudState(rand);

		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.getX(), position.getY(), position.getZ());

		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();

		final int xTendency = rand.nextInt(3) - 1;
		final int zTendency = rand.nextInt(3) - 1;

		final ArrayList<BlockPos> transaction = new ArrayList<>(this.numberOfBlocks);

		for (int n = 0; n < this.numberOfBlocks; n++)
		{
			x += (rand.nextInt(3) - 1 + xTendency);

			if ((rand.nextBoolean() && !this.isFlat) || (this.isFlat && rand.nextInt(10) == 0))
			{
				y += (rand.nextInt(3) - 1);
			}

			z += (rand.nextInt(3) - 1 + zTendency);

			for (int x1 = x; x1 < x + rand.nextInt(4) + 3 * (this.isFlat ? 3 : 1); x1++)
			{
				for (int y1 = y; y1 < y + rand.nextInt(1) + 2; y1++)
				{
					for (int z1 = z; z1 < z + rand.nextInt(4) + 3 * (this.isFlat ? 3 : 1); z1++)
					{
						pos.setPos(x1, y1, z1);

						if (!world.isAreaLoaded(pos, 2))
						{
							return false;
						}

						if (world.getBlockState(pos) == Blocks.AIR.getDefaultState() &&
								Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 * (this.isFlat ? 3 : 1) + rand.nextInt(2))
						{
							transaction.add(pos.toImmutable());
						}
					}
				}
			}
		}

		for (final BlockPos block : transaction)
		{
			world.setBlockState(block, state, 2 | 16);
		}

		return true;
	}
}
