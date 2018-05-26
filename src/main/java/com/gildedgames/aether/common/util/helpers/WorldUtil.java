package com.gildedgames.aether.common.util.helpers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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

}
