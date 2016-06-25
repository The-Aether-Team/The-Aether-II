package com.gildedgames.aether.common.world.features.trees;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenMassiveSkyrootTree extends WorldGenAbstractTree
{
	private final IBlockState leaves;

	private final int randHeight;

	private final boolean branches;

	public WorldGenMassiveSkyrootTree(IBlockState leaves, int heightWeight, boolean branchFlag)
	{
		super(false);

		this.leaves = leaves;
		this.randHeight = heightWeight;
		this.branches = branchFlag;
	}

	private BlockPos.MutableBlockPos tmpBlockPos = new BlockPos.MutableBlockPos();

	public void setBlockAirCheck(World world, int x, int y, int z, IBlockState state)
	{
		tmpBlockPos.setPos(x, y, z);

		if (world.getBlockState(tmpBlockPos).getBlock() == Blocks.AIR)
		{
			world.setBlockState(tmpBlockPos, state);
		}
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position)
	{
		boolean cangen = true;

		int height = rand.nextInt(this.randHeight) + (this.branches ? 8 : 4);

		int x = position.getX(), y = position.getY(), z = position.getZ();

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		for (int x1 = x - 3; x1 < x + 3; x1++)
		{
			for (int y1 = y + 1; y1 < y + (height + 2); y1++)
			{
				for (int z1 = z - 3; z1 < z + 3; z1++)
				{
					if (world.getBlockState(pos.setPos(x1, y1, z1)).getBlock() != Blocks.AIR)
					{
						cangen = false;
					}
				}
			}
		}

		if (y + (height + 2) <= world.getHeight() && cangen)
		{
			Block y1 = world.getBlockState(pos.setPos(x, y - 1, z)).getBlock();

			if (y1 != BlocksAether.aether_grass && y1 != BlocksAether.aether_dirt)
			{
				return false;
			}

			IBlockState wall = BlocksAether.skyroot_log_wall.getDefaultState(),
					grass = BlocksAether.aether_grass.getDefaultState(),
					dirt = BlocksAether.aether_dirt.getDefaultState();

			world.setBlockState(pos.setPos(x, y - 1, z), BlocksAether.aether_dirt.getDefaultState());

			for (int y2 = y; y2 <= y + height; y2++)
			{
				world.setBlockState(pos.setPos(x, y2, z), BlocksAether.skyroot_log.getDefaultState());
			}

			if (this.branches)
			{
				world.setBlockState(pos.setPos(x + 1, y, z), wall);
				world.setBlockState(pos.setPos(x + 1, y + 1, z), wall);
				world.setBlockState(pos.setPos(x + 2, y, z), wall);

				world.setBlockState(pos.setPos(x - 1, y, z), wall);
				world.setBlockState(pos.setPos(x - 1, y + 1, z), wall);
				world.setBlockState(pos.setPos(x - 2, y, z), wall);

				world.setBlockState(pos.setPos(x, y, z + 1), wall);
				world.setBlockState(pos.setPos(x, y + 1, z + 1), wall);
				world.setBlockState(pos.setPos(x, y, z + 2), wall);

				world.setBlockState(pos.setPos(x, y, z - 1), wall);
				world.setBlockState(pos.setPos(x, y + 1, z - 1), wall);
				world.setBlockState(pos.setPos(x, y, z - 2), wall);

				world.setBlockState(pos.setPos(x + 1, y - 1, z), grass);
				world.setBlockState(pos.setPos(x + 2, y - 1, z), grass);
				world.setBlockState(pos.setPos(x - 1, y - 1, z), grass);
				world.setBlockState(pos.setPos(x - 2, y - 1, z), grass);
				world.setBlockState(pos.setPos(x, y - 1, z + 1), grass);
				world.setBlockState(pos.setPos(x, y - 1, z + 2), grass);
				world.setBlockState(pos.setPos(x, y - 1, z - 1), grass);
				world.setBlockState(pos.setPos(x, y - 1, z - 2), grass);

				world.setBlockState(pos.setPos(x + 1, y - 2, z), dirt);
				world.setBlockState(pos.setPos(x + 2, y - 2, z), dirt);
				world.setBlockState(pos.setPos(x - 1, y - 2, z), dirt);
				world.setBlockState(pos.setPos(x - 2, y - 2, z), dirt);
				world.setBlockState(pos.setPos(x, y - 2, z + 1), dirt);
				world.setBlockState(pos.setPos(x, y - 2, z + 2), dirt);
				world.setBlockState(pos.setPos(x, y - 2, z - 1), dirt);
				world.setBlockState(pos.setPos(x, y - 2, z - 2), dirt);
			}

			this.setBlockAirCheck(world, x, y + (height + 1), z, this.leaves);
			this.setBlockAirCheck(world, x, y + (height + 2), z, this.leaves);

			for (int y2 = y + 2; y2 <= y + (height + 1); y2++)
			{
				this.setBlockAirCheck(world, x + 1, y2, z, this.leaves);
				this.setBlockAirCheck(world, x - 1, y2, z, this.leaves);
				this.setBlockAirCheck(world, x, y2, z + 1, this.leaves);
				this.setBlockAirCheck(world, x, y2, z - 1, this.leaves);
			}

			for (int y2 = y + 3; y2 <= y + height; y2 += 2)
			{
				this.setBlockAirCheck(world, x + 1, y2, z + 1, this.leaves);
				this.setBlockAirCheck(world, x - 1, y2, z - 1, this.leaves);
				this.setBlockAirCheck(world, x + 1, y2, z - 1, this.leaves);
				this.setBlockAirCheck(world, x - 1, y2, z + 1, this.leaves);

				this.setBlockAirCheck(world, x + 2, y2, z, this.leaves);
				this.setBlockAirCheck(world, x - 2, y2, z, this.leaves);
				this.setBlockAirCheck(world, x, y2, z + 2, this.leaves);
				this.setBlockAirCheck(world, x, y2, z - 2, this.leaves);
			}

			if (this.branches)
			{
				IBlockState log = BlocksAether.skyroot_log.getDefaultState();

				int side = rand.nextInt(3);

				for (int y2 = y + (rand.nextInt(2) + 3); y2 <= y + height - 2; y2 += 1 + rand.nextInt(3))
				{
					int branchLength = rand.nextInt(4) + 1;

					switch (side)
					{
					case (0):
						for (int x2 = x; x2 <= x + branchLength; x2++)
						{
							world.setBlockState(pos.setPos(x2, y2, z), log);

							this.setBlockAirCheck(world, x2, y2 + 1, z, this.leaves);
							this.setBlockAirCheck(world, x2, y2 - 1, z, this.leaves);
							this.setBlockAirCheck(world, x2 + 1, y2, z + 1, this.leaves);
							this.setBlockAirCheck(world, x2 - 1, y2, z - 1, this.leaves);
						}

						world.setBlockState(pos.setPos(x + (branchLength + 1), y2 + 1, z), log);
						world.setBlockState(pos.setPos(x + (branchLength + 2), y2 + 2, z), log);
						world.setBlockState(pos.setPos(x + (branchLength + 2), y2 + 3, z), log);

						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 2, z, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 3, z, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 4, z, this.leaves);

						this.setBlockAirCheck(world, x + (branchLength + 3), y2 + 2, z, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength + 3), y2 + 3, z, this.leaves);

						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 2, z - 1, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 3, z - 1, this.leaves);

						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 2, z + 1, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 3, z + 1, this.leaves);

						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 1, z - 1, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 2, z - 1, this.leaves);

						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 1, z + 1, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 2, z + 1, this.leaves);

						this.setBlockAirCheck(world, x + branchLength, y2, z - 1, this.leaves);
						this.setBlockAirCheck(world, x + branchLength, y2 + 1, z - 1, this.leaves);

						this.setBlockAirCheck(world, x + branchLength, y2, z + 1, this.leaves);
						this.setBlockAirCheck(world, x + branchLength, y2 + 1, z + 1, this.leaves);

						this.setBlockAirCheck(world, x + (branchLength - 1), y2, z - 1, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength - 1), y2, z + 1, this.leaves);

						this.setBlockAirCheck(world, x + (branchLength - 2), y2 - 1, z, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength - 1), y2 - 1, z, this.leaves);
						this.setBlockAirCheck(world, x + branchLength, y2 - 1, z, this.leaves);

						this.setBlockAirCheck(world, x + (branchLength + 1), y2, z, this.leaves);
						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 1, z, this.leaves);

						break;

					case (1):
						for (int x3 = x; x3 >= x - branchLength; x3--)
						{
							world.setBlockState(pos.setPos(x3, y2, z), log);
							this.setBlockAirCheck(world, x3, y2 + 1, z, this.leaves);
							this.setBlockAirCheck(world, x3, y2 - 1, z, this.leaves);
							this.setBlockAirCheck(world, x3 + 1, y2, z + 1, this.leaves);
							this.setBlockAirCheck(world, x3 - 1, y2, z - 1, this.leaves);
						}

						world.setBlockState(pos.setPos(x - (branchLength + 1), y2 + 1, z), log);
						world.setBlockState(pos.setPos(x - (branchLength + 2), y2 + 2, z), log);
						world.setBlockState(pos.setPos(x - (branchLength + 2), y2 + 3, z), log);

						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 2, z, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 3, z, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 4, z, this.leaves);

						this.setBlockAirCheck(world, x - (branchLength + 3), y2 + 2, z, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength + 3), y2 + 3, z, this.leaves);

						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 2, z - 1, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 3, z - 1, this.leaves);

						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 2, z + 1, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 3, z + 1, this.leaves);

						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 1, z - 1, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 2, z - 1, this.leaves);

						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 1, z + 1, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 2, z + 1, this.leaves);

						this.setBlockAirCheck(world, x - branchLength, y2, z - 1, this.leaves);
						this.setBlockAirCheck(world, x - branchLength, y2 + 1, z - 1, this.leaves);

						this.setBlockAirCheck(world, x - branchLength, y2, z + 1, this.leaves);
						this.setBlockAirCheck(world, x - branchLength, y2 + 1, z + 1, this.leaves);

						this.setBlockAirCheck(world, x - (branchLength - 1), y2, z - 1, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength - 1), y2, z + 1, this.leaves);

						this.setBlockAirCheck(world, x - (branchLength - 2), y2 - 1, z, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength - 1), y2 - 1, z, this.leaves);
						this.setBlockAirCheck(world, x - branchLength, y2 - 1, z, this.leaves);

						this.setBlockAirCheck(world, x - (branchLength + 1), y2, z, this.leaves);
						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 1, z, this.leaves);

						break;

					case (2):
						for (int z2 = z; z2 <= z + branchLength; z2++)
						{
							world.setBlockState(pos.setPos(x, y2, z2), log);
							this.setBlockAirCheck(world, x, y2 + 1, z2, this.leaves);
							this.setBlockAirCheck(world, x, y2 - 1, z2, this.leaves);
							this.setBlockAirCheck(world, x + 1, y2, z2, this.leaves);
							this.setBlockAirCheck(world, x - 1, y2, z2, this.leaves);
						}

						world.setBlockState(pos.setPos(x, y2 + 1, z + (branchLength + 1)), log);
						world.setBlockState(pos.setPos(x, y2 + 2, z + (branchLength + 2)), log);
						world.setBlockState(pos.setPos(x, y2 + 3, z + (branchLength + 2)), log);

						this.setBlockAirCheck(world, x, y2 + 2, z + (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x, y2 + 3, z + (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x, y2 + 4, z + (branchLength + 2), this.leaves);

						this.setBlockAirCheck(world, x, y2 + 2, z + (branchLength + 3), this.leaves);
						this.setBlockAirCheck(world, x, y2 + 3, z + (branchLength + 3), this.leaves);

						this.setBlockAirCheck(world, x - 1, y2 + 2, z + (branchLength + 2), this.leaves);
						this.setBlockAirCheck(world, x - 1, y2 + 3, z + (branchLength + 2), this.leaves);

						this.setBlockAirCheck(world, x + 1, y2 + 2, z + (branchLength + 2), this.leaves);
						this.setBlockAirCheck(world, x + 1, y2 + 3, z + (branchLength + 2), this.leaves);

						this.setBlockAirCheck(world, x - 1, y2 + 1, z + (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x - 1, y2 + 2, z + (branchLength + 1), this.leaves);

						this.setBlockAirCheck(world, x + 1, y2 + 1, z + (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x + 1, y2 + 2, z + (branchLength + 1), this.leaves);

						this.setBlockAirCheck(world, x - 1, y2, z + branchLength, this.leaves);
						this.setBlockAirCheck(world, x - 1, y2 + 1, z + branchLength, this.leaves);

						this.setBlockAirCheck(world, x + 1, y2, z + branchLength, this.leaves);
						this.setBlockAirCheck(world, x + 1, y2 + 1, z + branchLength, this.leaves);

						this.setBlockAirCheck(world, x - 1, y2, z + (branchLength - 1), this.leaves);
						this.setBlockAirCheck(world, x + 1, y2, z + (branchLength - 1), this.leaves);

						this.setBlockAirCheck(world, x, y2 - 1, z + (branchLength - 2), this.leaves);
						this.setBlockAirCheck(world, x, y2 - 1, z + (branchLength - 1), this.leaves);
						this.setBlockAirCheck(world, x, y2 - 1, z + branchLength, this.leaves);

						this.setBlockAirCheck(world, x, y2, z + (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x, y2 + 1, z + (branchLength + 2), this.leaves);

						break;

					case (3):
						for (int z3 = z; z3 >= z - branchLength; z3--)
						{
							world.setBlockState(pos.setPos(x, y2, z3), log);
							this.setBlockAirCheck(world, x, y2 + 1, z3, this.leaves);
							this.setBlockAirCheck(world, x, y2 - 1, z3, this.leaves);
							this.setBlockAirCheck(world, x + 1, y2, z3, this.leaves);
							this.setBlockAirCheck(world, x - 1, y2, z3, this.leaves);
						}

						world.setBlockState(pos.setPos(x, y2 + 1, z - (branchLength + 1)), log);
						world.setBlockState(pos.setPos(x, y2 + 2, z - (branchLength + 2)), log);
						world.setBlockState(pos.setPos(x, y2 + 3, z - (branchLength + 2)), log);

						this.setBlockAirCheck(world, x, y2 + 2, z - (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x, y2 + 3, z - (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x, y2 + 4, z - (branchLength + 2), this.leaves);

						this.setBlockAirCheck(world, x, y2 + 2, z - (branchLength + 3), this.leaves);
						this.setBlockAirCheck(world, x, y2 + 3, z - (branchLength + 3), this.leaves);

						this.setBlockAirCheck(world, x - 1, y2 + 2, z - (branchLength + 2), this.leaves);
						this.setBlockAirCheck(world, x - 1, y2 + 3, z - (branchLength + 2), this.leaves);

						this.setBlockAirCheck(world, x + 1, y2 + 2, z - (branchLength + 2), this.leaves);
						this.setBlockAirCheck(world, x + 1, y2 + 3, z - (branchLength + 2), this.leaves);

						this.setBlockAirCheck(world, x - 1, y2 + 1, z - (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x - 1, y2 + 2, z - (branchLength + 1), this.leaves);

						this.setBlockAirCheck(world, x + 1, y2 + 1, z - (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x + 1, y2 + 2, z - (branchLength + 1), this.leaves);

						this.setBlockAirCheck(world, x - 1, y2, z - branchLength, this.leaves);
						this.setBlockAirCheck(world, x - 1, y2 + 1, z - branchLength, this.leaves);

						this.setBlockAirCheck(world, x + 1, y2, z - branchLength, this.leaves);
						this.setBlockAirCheck(world, x + 1, y2 + 1, z - branchLength, this.leaves);

						this.setBlockAirCheck(world, x - 1, y2, z - (branchLength - 1), this.leaves);
						this.setBlockAirCheck(world, x + 1, y2, z - (branchLength - 1), this.leaves);

						this.setBlockAirCheck(world, x, y2 - 1, z - (branchLength - 2), this.leaves);
						this.setBlockAirCheck(world, x, y2 - 1, z - (branchLength - 1), this.leaves);
						this.setBlockAirCheck(world, x, y2 - 1, z - branchLength, this.leaves);

						this.setBlockAirCheck(world, x, y2, z - (branchLength + 1), this.leaves);
						this.setBlockAirCheck(world, x, y2 + 1, z - (branchLength + 2), this.leaves);

						break;
					}

					side++;

					if (side > 3)
					{
						side = 0;
					}
				}
			}

			return true;
		}

		return false;
	}
}
