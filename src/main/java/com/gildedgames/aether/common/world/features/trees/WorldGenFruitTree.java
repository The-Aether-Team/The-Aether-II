package com.gildedgames.aether.common.world.features.trees;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenFruitTree extends WorldGenAbstractTree
{
	private final IBlockState leaves, fruit;

	private final int fruitChance, randHeight;

	private final boolean branches;

	public WorldGenFruitTree(IBlockState leaves, IBlockState fruit, int fruitChance, int heightWeight, boolean branchFlag)
	{
		super(false);

		this.leaves = leaves;
		this.fruit = fruit;

		this.fruitChance = fruitChance;
		this.randHeight = heightWeight;

		this.branches = branchFlag;
	}

	private BlockPos.MutableBlockPos tmpPos = new BlockPos.MutableBlockPos();

	public void setBlockAirCheck(World world, int x, int y, int z, IBlockState block)
	{
		this.tmpPos.setPos(x, y, z);

		if (world.getBlockState(this.tmpPos).getBlock() == Blocks.AIR)
		{
			world.setBlockState(this.tmpPos, block);
		}
	}

	public void setBlockAirCheck(World world, int x, int y, int z, IBlockState log, IBlockState leaves)
	{
		this.tmpPos.setPos(x, y, z);

		if (world.getBlockState(this.tmpPos).getBlock() == Blocks.AIR)
		{
			if (leaves.getBlock() == Blocks.AIR)
			{
				world.setBlockState(this.tmpPos, log);
			}
			else
			{
				world.setBlockState(this.tmpPos, leaves);
			}
		}
	}

	@Override
	public boolean generate(World world, Random random, BlockPos position)
	{
		boolean cangen = true;

		int height = random.nextInt(this.randHeight) + (this.branches ? 8 : 4);

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

			world.setBlockState(pos.setPos(x, y - 1, z), BlocksAether.aether_dirt.getDefaultState());

			IBlockState air = Blocks.AIR.getDefaultState(),
					wall = BlocksAether.skyroot_log_wall.getDefaultState(),
					log = BlocksAether.skyroot_log.getDefaultState();

			for (int y2 = y; y2 <= y + height; y2++)
			{
				world.setBlockState(pos.setPos(x, y2, z), wall);
			}

			this.setBlockAirCheck(world, x, y + (height + 1), z, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
			this.setBlockAirCheck(world, x, y + (height + 2), z, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);

			for (int y2 = y + 2; y2 <= y + (height + 1); y2++)
			{
				this.setBlockAirCheck(world, x + 1, y2, z, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
				this.setBlockAirCheck(world, x - 1, y2, z, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
				this.setBlockAirCheck(world, x, y2, z + 1, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
				this.setBlockAirCheck(world, x, y2, z - 1, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
			}

			for (int y2 = y + 3; y2 <= y + height; y2 += 2)
			{
				this.setBlockAirCheck(world, x + 1, y2, z + 1, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
				this.setBlockAirCheck(world, x - 1, y2, z - 1, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
				this.setBlockAirCheck(world, x + 1, y2, z - 1, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
				this.setBlockAirCheck(world, x - 1, y2, z + 1, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);

				this.setBlockAirCheck(world, x + 2, y2, z, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
				this.setBlockAirCheck(world, x - 2, y2, z, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
				this.setBlockAirCheck(world, x, y2, z + 2, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
				this.setBlockAirCheck(world, x, y2, z - 2, this.leaves, random.nextInt(this.fruitChance) == 0 ? this.fruit : air);
			}

			if (this.branches)
			{
				int side = random.nextInt(3);

				for (int y2 = y + (random.nextInt(2) + 3); y2 <= y + height - 2; y2 += 1 + random.nextInt(3))
				{
					int chance = Math.max(1, (Math.max(1, y * (y2 - y)) / y2));
					int branchLength = random.nextInt(2) + 1;

					switch (side)
					{
					case (0):
						for (int x2 = x; x2 <= x + branchLength; x2++)
						{
							world.setBlockState(pos.setPos(x2, y2, z), log);
							this.setBlockAirCheck(world, x2, y2 + 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x2, y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x2 + 1, y2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x2 - 1, y2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						}

						world.setBlockState(pos.setPos(x + (branchLength + 1), y2 + 1, z), log);
						world.setBlockState(pos.setPos(x + (branchLength + 2), y2 + 2, z), log);
						world.setBlockState(pos.setPos(x + (branchLength + 2), y2 + 3, z), log);

						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 2, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 3, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 4, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + (branchLength + 3), y2 + 2, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength + 3), y2 + 3, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 3, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 3, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 1, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 1, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + branchLength, y2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + branchLength, y2 + 1, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + branchLength, y2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + branchLength, y2 + 1, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + (branchLength - 1), y2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength - 1), y2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + (branchLength - 2), y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength - 1), y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + branchLength, y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + (branchLength + 1), y2, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						break;

					case (1):
						for (int x3 = x; x3 >= x - branchLength; x3--)
						{
							world.setBlockState(pos.setPos(x3, y2, z), log);
							this.setBlockAirCheck(world, x3, y2 + 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x3, y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x3 + 1, y2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x3 - 1, y2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						}

						world.setBlockState(pos.setPos(x - (branchLength + 1), y2 + 1, z), log);
						world.setBlockState(pos.setPos(x - (branchLength + 2), y2 + 2, z), log);
						world.setBlockState(pos.setPos(x - (branchLength + 2), y2 + 3, z), log);

						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 2, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 3, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 4, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - (branchLength + 3), y2 + 2, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength + 3), y2 + 3, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 3, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 3, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 1, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 1, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - branchLength, y2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - branchLength, y2 + 1, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - branchLength, y2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - branchLength, y2 + 1, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - (branchLength - 1), y2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength - 1), y2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - (branchLength - 2), y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength - 1), y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - branchLength, y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - (branchLength + 1), y2, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						break;

					case (2):
						for (int z2 = z; z2 <= z + branchLength; z2++)
						{
							world.setBlockState(pos.setPos(x, y2, z2), log);
							this.setBlockAirCheck(world, x, y2 + 1, z2, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x, y2 - 1, z2, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x + 1, y2, z2, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x - 1, y2, z2, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						}

						world.setBlockState(pos.setPos(x, y2 + 1, z + (branchLength + 1)), log);
						world.setBlockState(pos.setPos(x, y2 + 2, z + (branchLength + 2)), log);
						world.setBlockState(pos.setPos(x, y2 + 3, z + (branchLength + 2)), log);

						this.setBlockAirCheck(world, x, y2 + 2, z + (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 + 3, z + (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 + 4, z + (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x, y2 + 2, z + (branchLength + 3), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 + 3, z + (branchLength + 3), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - 1, y2 + 2, z + (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - 1, y2 + 3, z + (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + 1, y2 + 2, z + (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + 1, y2 + 3, z + (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - 1, y2 + 1, z + (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - 1, y2 + 2, z + (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + 1, y2 + 1, z + (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + 1, y2 + 2, z + (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - 1, y2, z + branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - 1, y2 + 1, z + branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + 1, y2, z + branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + 1, y2 + 1, z + branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - 1, y2, z + (branchLength - 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + 1, y2, z + (branchLength - 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x, y2 - 1, z + (branchLength - 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 - 1, z + (branchLength - 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 - 1, z + branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x, y2, z + (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 + 1, z + (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						break;

					case (3):
						for (int z3 = z; z3 >= z - branchLength; z3--)
						{
							world.setBlockState(pos.setPos(x, y2, z3), log);
							this.setBlockAirCheck(world, x, y2 + 1, z3, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x, y2 - 1, z3, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x + 1, y2, z3, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x - 1, y2, z3, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						}

						world.setBlockState(pos.setPos(x, y2 + 1, z - (branchLength + 1)), log);
						world.setBlockState(pos.setPos(x, y2 + 2, z - (branchLength + 2)), log);
						world.setBlockState(pos.setPos(x, y2 + 3, z - (branchLength + 2)), log);

						this.setBlockAirCheck(world, x, y2 + 2, z - (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 + 3, z - (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 + 4, z - (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x, y2 + 2, z - (branchLength + 3), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 + 3, z - (branchLength + 3), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - 1, y2 + 2, z - (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - 1, y2 + 3, z - (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + 1, y2 + 2, z - (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + 1, y2 + 3, z - (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - 1, y2 + 1, z - (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - 1, y2 + 2, z - (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + 1, y2 + 1, z - (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + 1, y2 + 2, z - (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - 1, y2, z - branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x - 1, y2 + 1, z - branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x + 1, y2, z - branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + 1, y2 + 1, z - branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x - 1, y2, z - (branchLength - 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x + 1, y2, z - (branchLength - 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x, y2 - 1, z - (branchLength - 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 - 1, z - (branchLength - 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 - 1, z - branchLength, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

						this.setBlockAirCheck(world, x, y2, z - (branchLength + 1), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						this.setBlockAirCheck(world, x, y2 + 1, z - (branchLength + 2), this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);

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
