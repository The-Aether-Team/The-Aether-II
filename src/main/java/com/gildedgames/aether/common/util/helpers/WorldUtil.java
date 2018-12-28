package com.gildedgames.aether.common.util.helpers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class WorldUtil
{

	public static boolean isBlockInAABB(AxisAlignedBB bb, World world, IBlockState state)
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
			for (int y = minY; y <= maxY; y++)
			{
				for (int z = minZ; z <= maxZ; z++)
				{
					pos.setPos(x, y, z);

					if (world.getBlockState(pos) == state)
					{
						pos.release();

						return true;
					}
				}
			}
		}

		pos.release();

		return false;
	}

	public static boolean isBlockBelowAABB(AxisAlignedBB bb, World world, IBlockState block)
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
					IBlockState state = world.getBlockState(pos.setPos(x, y, z));

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
