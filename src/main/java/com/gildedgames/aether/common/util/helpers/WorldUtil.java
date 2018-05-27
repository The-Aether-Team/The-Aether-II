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

	public static BlockPos adjustPosToNearbyEntity(World world, BlockPos pos)
	{
		BlockPos blockpos = world.getPrecipitationHeight(pos);
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockpos, new BlockPos(blockpos.getX(), world.getHeight(), blockpos.getZ()))).grow(3.0D);
		List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb,
				p_apply_1_ -> p_apply_1_ != null && p_apply_1_.isEntityAlive() && world.canSeeSky(p_apply_1_.getPosition()));

		if (!list.isEmpty())
		{
			return list.get(world.rand.nextInt(list.size())).getPosition();
		}
		else
		{
			if (blockpos.getY() == -1)
			{
				blockpos = blockpos.up(2);
			}

			return blockpos;
		}
	}

	public static boolean isBlockInAABB(AxisAlignedBB bb, World world, IBlockState block)
	{
		int minX = MathHelper.floor(bb.minX);
		int maxX = MathHelper.ceil(bb.maxX);
		int minY = MathHelper.floor(bb.minY);
		int maxY = MathHelper.ceil(bb.maxY);
		int minZ = MathHelper.floor(bb.minZ);
		int maxZ = MathHelper.ceil(bb.maxZ);

		if (!world.isAreaLoaded(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ)))
		{
			return false;
		}

		BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();

		for (int x = minX; x < maxX; x++)
		{
			for (int y = minY; y < maxY; y++)
			{
				for (int z = minZ; z < maxZ; z++)
				{
					if (world.getBlockState(pos.setPos(x, y, z)) == block)
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
		int maxX = MathHelper.ceil(bb.maxX);
		int minY = MathHelper.floor(bb.minY);
		int maxY = MathHelper.ceil(bb.maxY);
		int minZ = MathHelper.floor(bb.minZ);
		int maxZ = MathHelper.ceil(bb.maxZ);

		if (!world.isAreaLoaded(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ)))
		{
			return false;
		}

		BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain();

		for (int x = minX; x < maxX; x++)
		{
			for (int z = minZ; z < maxZ; z++)
			{
				for (int y = maxY; y > 0; y--)
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
