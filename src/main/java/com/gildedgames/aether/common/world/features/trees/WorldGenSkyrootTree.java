package com.gildedgames.aether.common.world.features.trees;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
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
						if (y >= 0 && y < 256)
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

				if (isSoil && position.getY() < 256 - i - 1)
				{
					rootState.getBlock().onPlantGrow(rootState, worldIn, rootBlockPos, position);

					b0 = 3;
					byte b1 = 0;
					int i1;
					int j1;
					int k1;
					int l1;

					pos = new BlockPos.MutableBlockPos(position.getX(), position.getY(), position.getZ());

					for (l = position.getY() - b0 + i; l <= position.getY() + i; ++l)
					{
						i1 = l - (position.getY() + i);
						j1 = b1 + 1 - i1 / 2;

						for (k1 = position.getX() - j1; k1 <= position.getX() + j1; ++k1)
						{
							l1 = k1 - position.getX();

							for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; ++i2)
							{
								int j2 = i2 - position.getZ();

								if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i1 != 0)
								{
									pos.setPos(k1, l, i2);

									IBlockState state = worldIn.getBlockState(pos);

									if (state.getBlock().isAir(state, worldIn, pos) || state.getBlock().isLeaves(state, worldIn, pos))
									{
										this.setBlockAndNotifyAdequately(worldIn, pos, this.leavesState);
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
