package com.gildedgames.aether.common.world.features.trees;

import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

//public class WorldGenMassiveTree extends WorldGenAbstractTree
//{
//	private final IBlockState leaves;
//
//	private final boolean branches;
//
//	public WorldGenMassiveTree(IBlockState leaves, boolean branches)
//	{
//		super(true);
//
//		this.leaves = leaves;
//
//		this.branches = branches;
//	}
//
//	@Override
//	public boolean generate(World world, Random random, BlockPos pos)
//	{
//		boolean canGen = true;
//
//		BlockPos.MutableBlockPos nPos = new BlockPos.MutableBlockPos();
//
//		int height = random.nextInt(this.randHeight) + (this.branches ? 8 : 4);
//
//		for (int x1 = pos.getX() - 3; x1 < pos.getX() + 3; x1++)
//		{
//			for (int y1 = pos.getY() + 1; y1 < pos.getY() + (height + 2); y1++)
//			{
//				for (int z1 = pos.getZ() - 3; z1 < pos.getZ() + 3; z1++)
//				{
//					nPos.set(x1, y1, z1);
//
//					if (world.getBlockState(nPos).getBlock() != Blocks.air)
//					{
//						canGen = false;
//					}
//				}
//			}
//		}
//
//		int x = pos.getX(), y = pos.getY(), z = pos.getZ();
//
//		if (y + (height + 2) <= world.getHeight() && cangen)
//		{
//			Block y1 = world.getBlock(x, y - 1, z);
//
//			if (y1 != AetherBlocks.AetherGrass && y1 != AetherBlocks.AetherDirt)
//			{
//				return false;
//			}
//
//			world.setBlock(x, y - 1, z, AetherBlocks.AetherDirt);
//
//			for (int y2 = y; y2 <= y + height; y2++)
//			{
//				world.setBlock(x, y2, z, AetherBlocks.AetherLog);
//			}
//
//			if (this.branches)
//			{
//				world.setBlock(x + 1, y, z, AetherBlocks.SkyrootLogWall);
//				world.setBlock(x + 1, y + 1, z, AetherBlocks.SkyrootLogWall);
//				world.setBlock(x + 2, y, z, AetherBlocks.SkyrootLogWall);
//
//				world.setBlock(x - 1, y, z, AetherBlocks.SkyrootLogWall);
//				world.setBlock(x - 1, y + 1, z, AetherBlocks.SkyrootLogWall);
//				world.setBlock(x - 2, y, z, AetherBlocks.SkyrootLogWall);
//
//				world.setBlock(x, y, z + 1, AetherBlocks.SkyrootLogWall);
//				world.setBlock(x, y + 1, z + 1, AetherBlocks.SkyrootLogWall);
//				world.setBlock(x, y, z + 2, AetherBlocks.SkyrootLogWall);
//
//				world.setBlock(x, y, z - 1, AetherBlocks.SkyrootLogWall);
//				world.setBlock(x, y + 1, z - 1, AetherBlocks.SkyrootLogWall);
//				world.setBlock(x, y, z - 2, AetherBlocks.SkyrootLogWall);
//
//				world.setBlock(x + 1, y - 1, z, AetherBlocks.AetherGrass);
//				world.setBlock(x + 2, y - 1, z, AetherBlocks.AetherGrass);
//				world.setBlock(x - 1, y - 1, z, AetherBlocks.AetherGrass);
//				world.setBlock(x - 2, y - 1, z, AetherBlocks.AetherGrass);
//				world.setBlock(x, y - 1, z + 1, AetherBlocks.AetherGrass);
//				world.setBlock(x, y - 1, z + 2, AetherBlocks.AetherGrass);
//				world.setBlock(x, y - 1, z - 1, AetherBlocks.AetherGrass);
//				world.setBlock(x, y - 1, z - 2, AetherBlocks.AetherGrass);
//
//				world.setBlock(x + 1, y - 2, z, AetherBlocks.AetherDirt);
//				world.setBlock(x + 2, y - 2, z, AetherBlocks.AetherDirt);
//				world.setBlock(x - 1, y - 2, z, AetherBlocks.AetherDirt);
//				world.setBlock(x - 2, y - 2, z, AetherBlocks.AetherDirt);
//				world.setBlock(x, y - 2, z + 1, AetherBlocks.AetherDirt);
//				world.setBlock(x, y - 2, z + 2, AetherBlocks.AetherDirt);
//				world.setBlock(x, y - 2, z - 1, AetherBlocks.AetherDirt);
//				world.setBlock(x, y - 2, z - 2, AetherBlocks.AetherDirt);
//			}
//
//			this.setBlockAirCheck(world, x, y + (height + 1), z, this.leaves);
//			this.setBlockAirCheck(world, x, y + (height + 2), z, this.leaves);
//
//			for (int y2 = y + 2; y2 <= y + (height + 1); y2++)
//			{
//				this.setBlockAirCheck(world, x + 1, y2, z, this.leaves);
//				this.setBlockAirCheck(world, x - 1, y2, z, this.leaves);
//				this.setBlockAirCheck(world, x, y2, z + 1, this.leaves);
//				this.setBlockAirCheck(world, x, y2, z - 1, this.leaves);
//			}
//
//			for (int y2 = y + 3; y2 <= y + height; y2 += 2)
//			{
//				this.setBlockAirCheck(world, x + 1, y2, z + 1, this.leaves);
//				this.setBlockAirCheck(world, x - 1, y2, z - 1, this.leaves);
//				this.setBlockAirCheck(world, x + 1, y2, z - 1, this.leaves);
//				this.setBlockAirCheck(world, x - 1, y2, z + 1, this.leaves);
//
//				this.setBlockAirCheck(world, x + 2, y2, z, this.leaves);
//				this.setBlockAirCheck(world, x - 2, y2, z, this.leaves);
//				this.setBlockAirCheck(world, x, y2, z + 2, this.leaves);
//				this.setBlockAirCheck(world, x, y2, z - 2, this.leaves);
//			}
//
//			if (this.branches)
//			{
//				int side = random.nextInt(3);
//
//				for (int y2 = y + (random.nextInt(2) + 3); y2 <= y + height - 2; y2 += 1 + random.nextInt(3))
//				{
//					int branchLength = random.nextInt(4) + 1;
//
//					switch (side)
//					{
//					case (0):
//						for (int x2 = x; x2 <= x + branchLength; x2++)
//						{
//							world.setBlock(x2, y2, z, AetherBlocks.AetherLog);
//							this.setBlockAirCheck(world, x2, y2 + 1, z, this.leaves);
//							this.setBlockAirCheck(world, x2, y2 - 1, z, this.leaves);
//							this.setBlockAirCheck(world, x2 + 1, y2, z + 1, this.leaves);
//							this.setBlockAirCheck(world, x2 - 1, y2, z - 1, this.leaves);
//						}
//
//						world.setBlock(x + (branchLength + 1), y2 + 1, z, AetherBlocks.AetherLog);
//						world.setBlock(x + (branchLength + 2), y2 + 2, z, AetherBlocks.AetherLog);
//						world.setBlock(x + (branchLength + 2), y2 + 3, z, AetherBlocks.AetherLog);
//
//						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 2, z, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 3, z, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 4, z, this.leaves);
//
//						this.setBlockAirCheck(world, x + (branchLength + 3), y2 + 2, z, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength + 3), y2 + 3, z, this.leaves);
//
//						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 2, z - 1, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 3, z - 1, this.leaves);
//
//						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 2, z + 1, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 3, z + 1, this.leaves);
//
//						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 1, z - 1, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 2, z - 1, this.leaves);
//
//						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 1, z + 1, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength + 1), y2 + 2, z + 1, this.leaves);
//
//						this.setBlockAirCheck(world, x + branchLength, y2, z - 1, this.leaves);
//						this.setBlockAirCheck(world, x + branchLength, y2 + 1, z - 1, this.leaves);
//
//						this.setBlockAirCheck(world, x + branchLength, y2, z + 1, this.leaves);
//						this.setBlockAirCheck(world, x + branchLength, y2 + 1, z + 1, this.leaves);
//
//						this.setBlockAirCheck(world, x + (branchLength - 1), y2, z - 1, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength - 1), y2, z + 1, this.leaves);
//
//						this.setBlockAirCheck(world, x + (branchLength - 2), y2 - 1, z, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength - 1), y2 - 1, z, this.leaves);
//						this.setBlockAirCheck(world, x + branchLength, y2 - 1, z, this.leaves);
//
//						this.setBlockAirCheck(world, x + (branchLength + 1), y2, z, this.leaves);
//						this.setBlockAirCheck(world, x + (branchLength + 2), y2 + 1, z, this.leaves);
//
//						break;
//
//					case (1):
//						for (int x3 = x; x3 >= x - branchLength; x3--)
//						{
//							world.setBlockState(new BlockPos(x3, y2, z), AetherBlocks.AetherLog);
//							this.setBlockAirCheck(world, x3, y2 + 1, z, this.leaves);
//							this.setBlockAirCheck(world, x3, y2 - 1, z, this.leaves);
//							this.setBlockAirCheck(world, x3 + 1, y2, z + 1, this.leaves);
//							this.setBlockAirCheck(world, x3 - 1, y2, z - 1, this.leaves);
//						}
//
//						world.setBlockState(new BlockPos(x - (branchLength + 1)), y2 + 1, z, AetherBlocks.AetherLog);
//						world.setBlockState(new BlockPos(x - (branchLength + 2)), y2 + 2, z, AetherBlocks.AetherLog);
//						world.setBlockState(new BlockPos(x - (branchLength + 2)), y2 + 3, z, AetherBlocks.AetherLog);
//
//						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 2, z, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 3, z, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 4, z, this.leaves);
//
//						this.setBlockAirCheck(world, x - (branchLength + 3), y2 + 2, z, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength + 3), y2 + 3, z, this.leaves);
//
//						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 2, z - 1, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 3, z - 1, this.leaves);
//
//						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 2, z + 1, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 3, z + 1, this.leaves);
//
//						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 1, z - 1, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 2, z - 1, this.leaves);
//
//						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 1, z + 1, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength + 1), y2 + 2, z + 1, this.leaves);
//
//						this.setBlockAirCheck(world, x - branchLength, y2, z - 1, this.leaves);
//						this.setBlockAirCheck(world, x - branchLength, y2 + 1, z - 1, this.leaves);
//
//						this.setBlockAirCheck(world, x - branchLength, y2, z + 1, this.leaves);
//						this.setBlockAirCheck(world, x - branchLength, y2 + 1, z + 1, this.leaves);
//
//						this.setBlockAirCheck(world, x - (branchLength - 1), y2, z - 1, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength - 1), y2, z + 1, this.leaves);
//
//						this.setBlockAirCheck(world, x - (branchLength - 2), y2 - 1, z, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength - 1), y2 - 1, z, this.leaves);
//						this.setBlockAirCheck(world, x - branchLength, y2 - 1, z, this.leaves);
//
//						this.setBlockAirCheck(world, x - (branchLength + 1), y2, z, this.leaves);
//						this.setBlockAirCheck(world, x - (branchLength + 2), y2 + 1, z, this.leaves);
//
//						break;
//
//					case (2):
//						for (int z2 = z; z2 <= z + branchLength; z2++)
//						{
//							world.setBlockState(new BlockPos(x, y2, z2), AetherBlocks.AetherLog);
//							this.setBlockAirCheck(world, x, y2 + 1, z2, this.leaves);
//							this.setBlockAirCheck(world, x, y2 - 1, z2, this.leaves);
//							this.setBlockAirCheck(world, x + 1, y2, z2, this.leaves);
//							this.setBlockAirCheck(world, x - 1, y2, z2, this.leaves);
//						}
//
//						world.setBlockState(new BlockPos(x, y2 + 1, z + (branchLength + 1)), AetherBlocks.AetherLog);
//						world.setBlockState(new BlockPos(x, y2 + 2, z + (branchLength + 2)), AetherBlocks.AetherLog);
//						world.setBlockState(new BlockPos(x, y2 + 3, z + (branchLength + 2)), AetherBlocks.AetherLog);
//
//						this.setBlockAirCheck(world, x, y2 + 2, z + (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x, y2 + 3, z + (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x, y2 + 4, z + (branchLength + 2), this.leaves);
//
//						this.setBlockAirCheck(world, x, y2 + 2, z + (branchLength + 3), this.leaves);
//						this.setBlockAirCheck(world, x, y2 + 3, z + (branchLength + 3), this.leaves);
//
//						this.setBlockAirCheck(world, x - 1, y2 + 2, z + (branchLength + 2), this.leaves);
//						this.setBlockAirCheck(world, x - 1, y2 + 3, z + (branchLength + 2), this.leaves);
//
//						this.setBlockAirCheck(world, x + 1, y2 + 2, z + (branchLength + 2), this.leaves);
//						this.setBlockAirCheck(world, x + 1, y2 + 3, z + (branchLength + 2), this.leaves);
//
//						this.setBlockAirCheck(world, x - 1, y2 + 1, z + (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x - 1, y2 + 2, z + (branchLength + 1), this.leaves);
//
//						this.setBlockAirCheck(world, x + 1, y2 + 1, z + (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x + 1, y2 + 2, z + (branchLength + 1), this.leaves);
//
//						this.setBlockAirCheck(world, x - 1, y2, z + branchLength, this.leaves);
//						this.setBlockAirCheck(world, x - 1, y2 + 1, z + branchLength, this.leaves);
//
//						this.setBlockAirCheck(world, x + 1, y2, z + branchLength, this.leaves);
//						this.setBlockAirCheck(world, x + 1, y2 + 1, z + branchLength, this.leaves);
//
//						this.setBlockAirCheck(world, x - 1, y2, z + (branchLength - 1), this.leaves);
//						this.setBlockAirCheck(world, x + 1, y2, z + (branchLength - 1), this.leaves);
//
//						this.setBlockAirCheck(world, x, y2 - 1, z + (branchLength - 2), this.leaves);
//						this.setBlockAirCheck(world, x, y2 - 1, z + (branchLength - 1), this.leaves);
//						this.setBlockAirCheck(world, x, y2 - 1, z + branchLength, this.leaves);
//
//						this.setBlockAirCheck(world, x, y2, z + (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x, y2 + 1, z + (branchLength + 2), this.leaves);
//
//						break;
//
//					case (3):
//						for (int z3 = z; z3 >= z - branchLength; z3--)
//						{
//							world.setBlockState(new BlockPos(x, y2, z3), AetherBlocks.AetherLog);
//							this.setBlockAirCheck(world, x, y2 + 1, z3, this.leaves);
//							this.setBlockAirCheck(world, x, y2 - 1, z3, this.leaves);
//							this.setBlockAirCheck(world, x + 1, y2, z3, this.leaves);
//							this.setBlockAirCheck(world, x - 1, y2, z3, this.leaves);
//						}
//
//						world.setBlockState(new BlockPos(x, y2 + 1, z - (branchLength + 1)), BlocksAether.skyroot_log);
//						world.setBlockState(new BlockPos(x, y2 + 2, z - (branchLength + 2)), BlocksAether.skyroot_log);
//						world.setBlockState(new BlockPos(x, y2 + 3, z - (branchLength + 2)), BlocksAether.skyroot_log);
//
//						this.setBlockAirCheck(world, x, y2 + 2, z - (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x, y2 + 3, z - (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x, y2 + 4, z - (branchLength + 2), this.leaves);
//
//						this.setBlockAirCheck(world, x, y2 + 2, z - (branchLength + 3), this.leaves);
//						this.setBlockAirCheck(world, x, y2 + 3, z - (branchLength + 3), this.leaves);
//
//						this.setBlockAirCheck(world, x - 1, y2 + 2, z - (branchLength + 2), this.leaves);
//						this.setBlockAirCheck(world, x - 1, y2 + 3, z - (branchLength + 2), this.leaves);
//
//						this.setBlockAirCheck(world, x + 1, y2 + 2, z - (branchLength + 2), this.leaves);
//						this.setBlockAirCheck(world, x + 1, y2 + 3, z - (branchLength + 2), this.leaves);
//
//						this.setBlockAirCheck(world, x - 1, y2 + 1, z - (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x - 1, y2 + 2, z - (branchLength + 1), this.leaves);
//
//						this.setBlockAirCheck(world, x + 1, y2 + 1, z - (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x + 1, y2 + 2, z - (branchLength + 1), this.leaves);
//
//						this.setBlockAirCheck(world, x - 1, y2, z - branchLength, this.leaves);
//						this.setBlockAirCheck(world, x - 1, y2 + 1, z - branchLength, this.leaves);
//
//						this.setBlockAirCheck(world, x + 1, y2, z - branchLength, this.leaves);
//						this.setBlockAirCheck(world, x + 1, y2 + 1, z - branchLength, this.leaves);
//
//						this.setBlockAirCheck(world, x - 1, y2, z - (branchLength - 1), this.leaves);
//						this.setBlockAirCheck(world, x + 1, y2, z - (branchLength - 1), this.leaves);
//
//						this.setBlockAirCheck(world, x, y2 - 1, z - (branchLength - 2), this.leaves);
//						this.setBlockAirCheck(world, x, y2 - 1, z - (branchLength - 1), this.leaves);
//						this.setBlockAirCheck(world, x, y2 - 1, z - branchLength, this.leaves);
//
//						this.setBlockAirCheck(world, x, y2, z - (branchLength + 1), this.leaves);
//						this.setBlockAirCheck(world, x, y2 + 1, z - (branchLength + 2), this.leaves);
//
//						break;
//					}
//
//					side++;
//
//					if (side > 3)
//					{
//						side = 0;
//					}
//				}
//			}
//
//			return true;
//		}
//
//		return false;
//	}
//
//	public void setBlockAirCheck(World world, int x, int y, int z, IBlockState state)
//	{
//		BlockPos pos = new BlockPos(x, y, z);
//
//		if (world.getBlockState(pos).getBlock() == Blocks.air)
//		{
//			world.setBlockState(pos, state);
//		}
//	}
//}
