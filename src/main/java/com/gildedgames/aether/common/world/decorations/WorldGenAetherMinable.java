package com.gildedgames.aether.common.world.decorations;

import com.gildedgames.orbis.lib.util.ArrayHelper;
import com.gildedgames.orbis.lib.world.WorldSlice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class WorldGenAetherMinable
{
	private final IBlockState oreBlock;

	/** The number of blocks to generate. */
	private final int numberOfBlocks;

	private final IBlockState[] predicate;

	private boolean emitsLight, isFloating;

	public WorldGenAetherMinable(final IBlockState state, final int blockCount, final IBlockState[] replaceableStates)
	{
		this.oreBlock = state;
		this.numberOfBlocks = blockCount;
		this.predicate = replaceableStates;
	}

	public void setFloating(boolean val)
	{
		this.isFloating = val;
	}

	public void setEmitsLight(boolean val)
	{
		this.emitsLight = val;
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

		BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos();

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

			float xStepFactor = (minX + 0.5f - d6) / (d10 / 2.0f);
			float xStep = ((minX + 1 + 0.5f - d6) / (d10 / 2.0f)) - xStepFactor;

			float yStepFactor = (minY + 0.5f - d7) / (d11 / 2.0f);
			float yStep = ((minY + 1 + 0.5f - d7) / (d11 / 2.0f)) - yStepFactor;

			float zStepFactor = (minZ + 0.5f - d8) / (d10 / 2.0f);
			float zStep = ((minZ + 1 + 0.5f - d8) / (d10 / 2.0f)) - zStepFactor;

			float xDist = xStepFactor;

			for (int x = minX; x <= maxX; ++x)
			{
				float xDistSq = xDist * xDist;

				if (xDistSq < 1.0f)
				{
					float yDist = yStepFactor;

					for (int y = minY; y <= maxY; ++y)
					{
						float yDistSq = yDist * yDist;

						if (xDistSq + yDistSq < 1.0f)
						{
							float zDist = zStepFactor;

							for (int z = minZ; z <= maxZ; ++z)
							{
								float zDistSq = zDist * zDist;

								if (xDistSq + yDistSq + zDistSq < 1.0f)
								{
									final IBlockState state = slice.getBlockState(x, y, z);

									if (ArrayHelper.contains(this.predicate, state))
									{
										nextPos.setPos(x, y, z);

										slice.setBlockState(nextPos, this.oreBlock);

										if (this.emitsLight)
										{
											slice.getWorld().checkLight(nextPos);
										}

										if (this.isFloating)
										{
											slice.getWorld().scheduleUpdate(nextPos, this.oreBlock.getBlock(), 0);
										}
									}
								}

								zDist += zStep;
							}
						}

						yDist += yStep;
					}
				}

				xDist += xStep;
			}

			attempts++;
		}

		return true;
	}
}
