package com.gildedgames.aether.common.world.features.trees;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherPlant;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
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
							if (!worldIn.isAirBlock(new BlockPos(k, y, l)))
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

				Block rootBlock = worldIn.getBlockState(rootBlockPos).getBlock();

				boolean isSoil = ((BlockAetherPlant) BlocksAether.aether_sapling).isSuitableSoilBlock(rootBlock);

				if (isSoil && position.getY() < 256 - i - 1)
				{
					rootBlock.onPlantGrow(worldIn, rootBlockPos, position);

					b0 = 3;
					byte b1 = 0;
					int i1;
					int j1;
					int k1;
					int l1;
					BlockPos pos;

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
									pos = new BlockPos(k1, l, i2);
									Block block = worldIn.getBlockState(pos).getBlock();

									if (block.isAir(worldIn, pos) || block.isLeaves(worldIn, pos))
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
						Block block2 = worldIn.getBlockState(upN).getBlock();

						if (block2.isAir(worldIn, upN) || block2.isLeaves(worldIn, upN))
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
	protected boolean func_150523_a(Block block)
	{
		return super.func_150523_a(block) || block == BlocksAether.aether_sapling;
	}
}
