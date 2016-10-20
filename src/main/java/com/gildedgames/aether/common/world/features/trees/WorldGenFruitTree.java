package com.gildedgames.aether.common.world.features.trees;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.walls.BlockSkyrootWall;
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

	public void setBlockAirCheck(World world, int x, int y, int z, IBlockState block)
	{
		BlockPos pos = new BlockPos(x, y, z);

		if (world.getBlockState(pos).getBlock() == Blocks.AIR)
		{
			world.setBlockState(pos, block);
		}
	}

	public void setBlockAirCheck(World world, int x, int y, int z, IBlockState log, IBlockState leaves)
	{
		BlockPos pos = new BlockPos(x, y, z);

		if (world.getBlockState(pos).getBlock() == Blocks.AIR)
		{
			if (leaves.getBlock() == Blocks.AIR)
			{
				world.setBlockState(pos, log);
			}
			else
			{
				world.setBlockState(pos, leaves);
			}
		}
	}

	@Override
	public boolean generate(World world, Random random, BlockPos position)
	{
		boolean cangen = true;

		int height = random.nextInt(this.randHeight) + (this.branches ? 8 : 4);

		int x = position.getX(), y = position.getY(), z = position.getZ();

		BlockPos pos;

		for (int x1 = x - 3; x1 < x + 3; x1++)
		{
			for (int y1 = y + 1; y1 < y + (height + 2); y1++)
			{
				for (int z1 = z - 3; z1 < z + 3; z1++)
				{
					pos = new BlockPos(x1, y1, z1);

					if (world.getBlockState(pos).getBlock() != Blocks.AIR)
					{
						cangen = false;
					}
				}
			}
		}

		if (y + (height + 2) <= world.getActualHeight() && cangen)
		{
			pos = new BlockPos(x, y - 1, z);

			Block y1 = world.getBlockState(pos).getBlock();

			if (y1 != BlocksAether.aether_grass && y1 != BlocksAether.aether_dirt)
			{
				return false;
			}

			pos = new BlockPos(x, y - 1, z);

			world.setBlockState(pos, BlocksAether.aether_grass.getDefaultState());

			IBlockState air = Blocks.AIR.getDefaultState(),
					wall = BlocksAether.skyroot_log_wall.getDefaultState().withProperty(BlockSkyrootWall.PROPERTY_GENERATED, true),
					log = BlocksAether.skyroot_log.getDefaultState();

			for (int y2 = y; y2 <= y + height; y2++)
			{
				pos = new BlockPos(x, y2, z);

				world.setBlockState(pos, wall);
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
							pos = new BlockPos(x2, y2, z);

							world.setBlockState(pos, log);
							this.setBlockAirCheck(world, x2, y2 + 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x2, y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x2 + 1, y2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x2 - 1, y2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						}

						pos = new BlockPos(x + (branchLength + 1), y2 + 1, z);
						world.setBlockState(pos, log);

						pos = new BlockPos(x + (branchLength + 2), y2 + 2, z);
						world.setBlockState(pos, log);

						pos = new BlockPos(x + (branchLength + 2), y2 + 3, z);
						world.setBlockState(pos, log);

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
							pos = new BlockPos(x3, y2, z);

							world.setBlockState(pos, log);
							this.setBlockAirCheck(world, x3, y2 + 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x3, y2 - 1, z, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x3 + 1, y2, z + 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x3 - 1, y2, z - 1, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						}

						pos = new BlockPos(x - (branchLength + 1), y2 + 1, z);
						world.setBlockState(pos, log);

						pos = new BlockPos(x - (branchLength + 2), y2 + 2, z);
						world.setBlockState(pos, log);

						pos = new BlockPos(x - (branchLength + 2), y2 + 3, z);
						world.setBlockState(pos, log);

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
							pos = new BlockPos(x, y2, z2);

							world.setBlockState(pos, log);
							this.setBlockAirCheck(world, x, y2 + 1, z2, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x, y2 - 1, z2, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x + 1, y2, z2, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x - 1, y2, z2, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						}

						pos = new BlockPos(x, y2 + 1, z + (branchLength + 1));
						world.setBlockState(pos, log);

						pos = new BlockPos(x, y2 + 2, z + (branchLength + 2));
						world.setBlockState(pos, log);

						pos = new BlockPos(x, y2 + 3, z + (branchLength + 2));
						world.setBlockState(pos, log);

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
							pos = new BlockPos(x, y2, z3);

							world.setBlockState(pos, log);
							this.setBlockAirCheck(world, x, y2 + 1, z3, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x, y2 - 1, z3, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x + 1, y2, z3, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
							this.setBlockAirCheck(world, x - 1, y2, z3, this.leaves, random.nextInt(chance) == 0 ? this.fruit : air);
						}

						pos = new BlockPos(x, y2 + 1, z - (branchLength + 1));
						world.setBlockState(pos, log);

						pos = new BlockPos(x, y2 + 2, z - (branchLength + 2));
						world.setBlockState(pos, log);

						pos = new BlockPos(x, y2 + 3, z - (branchLength + 2));
						world.setBlockState(pos, log);

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
