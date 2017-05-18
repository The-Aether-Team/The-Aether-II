package com.gildedgames.aether.common.entities.util;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class QuicksoilProcessor
{
	@SubscribeEvent
	public static void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		final EntityLivingBase entity = event.getEntityLiving();

		if (entity instanceof EntityPlayer)
		{
			if (((EntityPlayer) entity).isSpectator())
			{
				return;
			}
		}

		AxisAlignedBB bb = entity.getEntityBoundingBox().offset(0.0D, -0.2D, 0.0D).expand(0.0D, 0.2D, 0.0D);

		if (isBlockInAABB(bb, entity.getEntityWorld(), BlocksAether.quicksoil))
		{
			AetherCore.PROXY.modifyEntityQuicksoil(entity);
		}
	}

	private static boolean isBlockInAABB(AxisAlignedBB bb, World world, Block block)
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
					if (world.getBlockState(pos.setPos(x, y, z)).getBlock() == block)
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
}
