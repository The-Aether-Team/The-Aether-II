package com.gildedgames.aether.common.world.aether.features;

import com.gildedgames.orbis_api.world.WorldSlice;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class WorldGenAetherMinable
{
	private final IBlockState oreBlock;

	/** The number of blocks to generate. */
	private final int numberOfBlocks;

	private final IBlockState[] predicate;

	private final boolean emitsLight;

	public WorldGenAetherMinable(final IBlockState state, final int blockCount, final IBlockState[] replaceableStates)
	{
		this.oreBlock = state;
		this.numberOfBlocks = blockCount;
		this.predicate = replaceableStates;
		this.emitsLight = this.oreBlock.getLightValue() > 0;
	}

	public boolean generate(final WorldSlice slice, final Random rand, final BlockPos position)
	{
		final float f = rand.nextFloat() * (float) Math.PI;
		final float fSin = MathHelper.sin(f);

		final float d0 = ((position.getX() + 8) + fSin * this.numberOfBlocks / 8.0f);
		final float d1 = ((position.getX() + 8) - fSin * this.numberOfBlocks / 8.0f);

		final float d2 = ((position.getZ() + 8) + fSin * this.numberOfBlocks / 8.0f);
		final float d3 = ((position.getZ() + 8) - fSin * this.numberOfBlocks / 8.0f);

		final float d4 = (position.getY() + rand.nextInt(3) - 2.0f);
		final float d5 = (position.getY() + rand.nextInt(3) - 2.0f);

		BlockPos.PooledMutableBlockPos nextPos = BlockPos.PooledMutableBlockPos.retain();

		int attempts = 0;

		while (attempts < this.numberOfBlocks)
		{
			final float radius = attempts / (float) this.numberOfBlocks;
			final float radiusSin = MathHelper.sin((float) Math.PI * radius);

			final float d6 = d0 + (d1 - d0) * radius;
			final float d7 = d4 + (d5 - d4) * radius;
			final float d8 = d2 + (d3 - d2) * radius;

			final float d9 = rand.nextFloat() * (float) this.numberOfBlocks / 16.0f;

			final float d10 = (radiusSin + 1.0f) * d9 + 1.0f;
			final float d11 = (radiusSin + 1.0f) * d9 + 1.0f;

			final int minX = MathHelper.floor(d6 - d10 / 2.0f);
			final int minY = MathHelper.floor(d7 - d11 / 2.0f);
			final int minZ = MathHelper.floor(d8 - d10 / 2.0f);

			final int maxX = MathHelper.floor(d6 + d10 / 2.0f);
			final int maxY = MathHelper.floor(d7 + d11 / 2.0f);
			final int maxZ = MathHelper.floor(d8 + d10 / 2.0f);

			for (int x = minX; x <= maxX; ++x)
			{
				float xDist = (x + 0.5f - d6) / (d10 / 2.0f);
				xDist *= xDist;

				if (xDist < 1.0f)
				{
					for (int y = minY; y <= maxY; ++y)
					{
						float yDist = (y + 0.5f - d7) / (d11 / 2.0f);
						yDist *= yDist;

						if (xDist + yDist < 1.0f)
						{
							for (int z = minZ; z <= maxZ; ++z)
							{
								float zDist = (z + 0.5f - d8) / (d10 / 2.0f);
								zDist *= zDist;

								if (xDist + yDist + zDist < 1.0f)
								{
									nextPos.setPos(x, y, z);

									final IBlockState state = slice.getBlockState(nextPos);

									if (state.getMaterial() != Material.AIR && ArrayUtils.contains(this.predicate, state))
									{
										slice.replaceBlockState(nextPos, this.oreBlock, this.emitsLight);
									}
								}
							}
						}
					}
				}
			}

			attempts++;
		}

		nextPos.release();

		return true;
	}
}
