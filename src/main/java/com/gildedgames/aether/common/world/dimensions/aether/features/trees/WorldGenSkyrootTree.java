package com.gildedgames.aether.common.world.dimensions.aether.features.trees;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenSkyrootTree extends WorldGenAbstractTree
{
	private final IBlockState logState, leavesState;

	public WorldGenSkyrootTree(IBlockState logState, IBlockState leavesState)
	{
		super(true);

		this.logState = logState;
		this.leavesState = leavesState;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		int i = rand.nextInt(3) + 4;

		boolean canCreateTree = true;

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(position.getX(), position.getY(), position.getZ());

		if (position.getY() >= 1 && position.getY() + i + 1 <= 256)
		{
			byte b0;
			int l;

			for (int y = position.getY(); y <= position.getY() + 1 + i; ++y)
			{
				b0 = 1;

				if (y == position.getY())
				{
					b0 = 0;
				}

				if (y >= position.getY() + 1 + i - 2)
				{
					b0 = 2;
				}

				for (int k = position.getX() - b0; k <= position.getX() + b0 && canCreateTree; ++k)
				{
					for (l = position.getZ() - b0; l <= position.getZ() + b0 && canCreateTree; ++l)
					{
						if (y >= 0 && y < worldIn.getActualHeight())
						{
							pos.setPos(k, y, l);

							if (!worldIn.isAirBlock(pos))
							{
								canCreateTree = false;
							}
						}
						else
						{
							canCreateTree = false;
						}
					}
				}
			}

			if (canCreateTree)
			{
				BlockPos rootBlockPos = position.down();

				IBlockState rootState = worldIn.getBlockState(rootBlockPos);

				boolean isSoil = BlocksAether.aether_sapling.isSuitableSoilBlock(rootState);

				IBlockState state = worldIn.getBlockState(position.down());

				if (isSoil && position.getY() < worldIn.getActualHeight() - i - 1)
				{
					rootState.getBlock().onPlantGrow(rootState, worldIn, rootBlockPos, position);

					for (int i3 = position.getY() - 3 + i; i3 <= position.getY() + i; ++i3)
					{
						int i4 = i3 - (position.getY() + i);
						int j1 = 1 - i4 / 2;

						for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; ++k1)
						{
							int l1 = k1 - position.getX();

							for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; ++i2)
							{
								int j2 = i2 - position.getZ();

								if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0)
								{
									BlockPos blockpos = new BlockPos(k1, i3, i2);
									state = worldIn.getBlockState(blockpos);

									if (state.getBlock().isAir(state, worldIn, blockpos)
											|| state.getBlock().isLeaves(state, worldIn, blockpos) || state.getMaterial() == Material.VINE)
									{
										this.setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesState);
									}
								}
							}
						}
					}

					for (l = 0; l < i; ++l)
					{
						BlockPos upN = position.up(l);
						IBlockState state2 = worldIn.getBlockState(upN);

						if (state2.getBlock().isAir(state2, worldIn, upN) || state2.getBlock().isLeaves(state2, worldIn, upN))
						{
							this.setBlockAndNotifyAdequately(worldIn, position.up(l), this.logState);
						}
					}

					return true;
				}
			}
		}

		return false;
	}

	@Override
	protected boolean canGrowInto(Block block)
	{
		return super.canGrowInto(block) || block == BlocksAether.aether_sapling;
	}
}
