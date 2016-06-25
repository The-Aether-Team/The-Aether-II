package com.gildedgames.aether.common.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GenUtil
{
	
	/**
	 * Fast array filling for identical value
	 * @param array
	 * @param value
	 */
	public static <T> void fillArray(T[] array, T value) 
	{
		int len = array.length;
	
		if (len > 0)
		{
			array[0] = value;
		}
	
		for (int i = 1; i < len; i += i) 
		{
			System.arraycopy(array, 0, array, i, ((len - i) < i) ? (len - i) : i);
		}
	}
	
	public static void fillArray(short[] array, short value) 
	{
		int len = array.length;
	
		if (len > 0)
		{
			array[0] = value;
		}
	
		for (int i = 1; i < len; i += i) 
		{
			System.arraycopy(array, 0, array, i, ((len - i) < i) ? (len - i) : i);
		}
	}
	
	public static void cuboid(World world, BlockPos min, BlockPos max, IBlockState state)
	{
		GenUtil.cuboid(world, min, max, state, state, true);
	}
	
	public static void cuboid(World world, BlockPos min, BlockPos max, IBlockState state, boolean replaceSolidBlocks)
	{
		GenUtil.cuboid(world, min, max, state, state, replaceSolidBlocks);
	}
	
	public static void cuboid(World world, BlockPos min, BlockPos max, IBlockState edgeBlockState, IBlockState fillBlockState)
	{
		GenUtil.cuboid(world, min, max, edgeBlockState, fillBlockState, true);
	}

	public static void cuboid(World world, BlockPos min, BlockPos max, IBlockState edgeBlockState, IBlockState fillBlockState, boolean replaceSolidBlocks)
	{
		for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max))
		{
			if (replaceSolidBlocks || world.isAirBlock(pos))
			{
				if (pos.getY() != min.getY() && pos.getY() != max.getY() && pos.getX() != min.getX() && pos.getX() != max.getX() && pos.getZ() != min.getZ() && pos.getZ() != max.getZ())
				{
					world.setBlockState(pos, fillBlockState);
				}
				else
				{
					world.setBlockState(pos, edgeBlockState);
				}
			}
		}
	}
	
	public static void cuboidVaried(World world, BlockPos min, BlockPos max, IBlockState block1, IBlockState block2, int rarity, Random rand)
	{
		GenUtil.cuboidVaried(world, min, max, block1, block2, rarity, rand, true);
	}

	public static void cuboidVaried(World world, BlockPos min, BlockPos max, IBlockState block1, IBlockState block2, int rarity, Random rand, boolean replaceSolidBlocks)
	{
		for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max))
		{
			if (replaceSolidBlocks || world.isAirBlock(pos))
			{
				IBlockState chosenState = block1;

				if (rand.nextInt(rarity) == 1)
				{
					chosenState = block2;
				}

				world.setBlockState(pos, chosenState);
			}
		}
	}
	
}
