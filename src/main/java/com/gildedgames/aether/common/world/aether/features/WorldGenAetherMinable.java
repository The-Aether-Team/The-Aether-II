package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.aether.common.world.util.WorldSlice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenAetherMinable
{
	private final IBlockState oreBlock;

	/** The number of blocks to generate. */
	private final int numberOfBlocks;

	private final BlockMatcher predicate;

	public WorldGenAetherMinable(final IBlockState state, final int blockCount)
	{
		this(state, blockCount, BlockMatcher.forBlock(Blocks.STONE));
	}

	public WorldGenAetherMinable(final IBlockState state, final int blockCount, final BlockMatcher p_i45631_3_)
	{
		this.oreBlock = state;
		this.numberOfBlocks = blockCount;
		this.predicate = p_i45631_3_;
	}

	public boolean generate(final WorldSlice slice, final Random rand, final BlockPos position)
	{
		World world = slice.getWorld();

		final double f = rand.nextDouble() * Math.PI;
		final double fSin = Math.sin(f);

		final double d0 = ((position.getX() + 8) + fSin * this.numberOfBlocks / 8.0D);
		final double d1 = ((position.getX() + 8) - fSin * this.numberOfBlocks / 8.0D);

		final double d2 = ((position.getZ() + 8) + fSin * this.numberOfBlocks / 8.0D);
		final double d3 = ((position.getZ() + 8) - fSin * this.numberOfBlocks / 8.0D);

		final double d4 = (position.getY() + rand.nextInt(3) - 2.0D);
		final double d5 = (position.getY() + rand.nextInt(3) - 2.0D);

		BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos();

		int attempts = 0;

		while (attempts < this.numberOfBlocks)
		{
			final double radius = attempts / (double) this.numberOfBlocks;
			final double radiusSin = (double) MathHelper.sin((float) (Math.PI * radius));

			final double d6 = d0 + (d1 - d0) * radius;
			final double d7 = d4 + (d5 - d4) * radius;
			final double d8 = d2 + (d3 - d2) * radius;

			final double d9 = rand.nextDouble() * (double) this.numberOfBlocks / 16.0D;

			final double d10 = (radiusSin + 1.0D) * d9 + 1.0D;
			final double d11 = (radiusSin + 1.0D) * d9 + 1.0D;

			final int minX = MathHelper.floor(d6 - d10 / 2.0D);
			final int minY = MathHelper.floor(d7 - d11 / 2.0D);
			final int minZ = MathHelper.floor(d8 - d10 / 2.0D);

			final int maxX = MathHelper.floor(d6 + d10 / 2.0D);
			final int maxY = MathHelper.floor(d7 + d11 / 2.0D);
			final int maxZ = MathHelper.floor(d8 + d10 / 2.0D);

			for (int x = minX; x <= maxX; ++x)
			{
				double xDist = (x + 0.5D - d6) / (d10 / 2.0D);
				xDist *= xDist;

				if (xDist < 1.0D)
				{
					for (int y = minY; y <= maxY; ++y)
					{
						double yDist = (y + 0.5D - d7) / (d11 / 2.0D);
						yDist *= yDist;

						if (xDist + yDist < 1.0D)
						{
							for (int z = minZ; z <= maxZ; ++z)
							{
								double zDist = (z + 0.5D - d8) / (d10 / 2.0D);
								zDist *= zDist;

								if (xDist + yDist + zDist < 1.0D)
								{
									nextPos.setPos(x, y, z);

									final IBlockState state = slice.getBlockState(nextPos);

									if (state.getBlock().isReplaceableOreGen(state, world, nextPos, this.predicate))
									{
										world.setBlockState(nextPos, this.oreBlock, 2 | 16);
									}
								}
							}
						}
					}
				}
			}

			attempts++;
		}

		return true;
	}
}
