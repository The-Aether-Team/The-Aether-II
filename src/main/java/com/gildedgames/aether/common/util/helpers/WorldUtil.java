package com.gildedgames.aether.common.util.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldUtil
{

	public static boolean isBlockInAABB(AxisAlignedBB bb, World world, BlockState state)
	{
		return isBlockInAABB(bb, world, state, true);
	}

	public static boolean isBlockInAABB(AxisAlignedBB bb, World world, BlockState state, boolean checkLoaded)
	{
		int minX = MathHelper.floor(bb.minX);
		int minY = MathHelper.floor(bb.minY);
		int minZ = MathHelper.floor(bb.minZ);

		int maxX = MathHelper.ceil(bb.maxX);
		int maxY = MathHelper.ceil(bb.maxY);
		int maxZ = MathHelper.ceil(bb.maxZ);

		if (checkLoaded && !world.isAreaLoaded(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ)))
		{
			return false;
		}

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		Chunk chunk = null;

		for (int x = minX; x < maxX; x++)
		{
			for (int z = minZ; z < maxZ; z++)
			{
				if (chunk != null && (chunk.x != (x >> 4) || chunk.z != (z >> 4)))
				{
					chunk = null;
				}

				if (chunk == null)
				{
					chunk = world.getChunk(x >> 4, z >> 4);
				}

				for (int y = minY; y < maxY; y++)
				{
					pos.setPos(x, y, z);

					if (world.getBlockState(pos) == state)
					{
						return true;
					}
				}

			}

		}

		return false;
	}

	public static boolean isBlockBelowAABB(AxisAlignedBB bb, World world, BlockState block)
	{
		int minX = MathHelper.floor(bb.minX);
		int maxX = MathHelper.floor(bb.maxX);
		int minY = MathHelper.floor(bb.minY);
		int maxY = MathHelper.floor(bb.maxY);
		int minZ = MathHelper.floor(bb.minZ);
		int maxZ = MathHelper.floor(bb.maxZ);

		if (!world.isAreaLoaded(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ)))
		{
			return false;
		}

		BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();

		for (int x = minX; x <= maxX; x++)
		{
			for (int z = minZ; z <= maxZ; z++)
			{
				for (int y = maxY; y >= 0; y--)
				{
					BlockState state = world.getBlockState(pos.setPos(x, y, z));

					if (state == block)
					{
						pos.release();

						return true;
					}

					if (state != Blocks.AIR.getDefaultState())
					{
						break;
					}
				}
			}
		}

		pos.release();

		return false;
	}

}
