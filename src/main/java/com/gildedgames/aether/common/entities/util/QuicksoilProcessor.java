package com.gildedgames.aether.common.entities.util;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.util.helpers.WorldUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class QuicksoilProcessor
{
	@SubscribeEvent
	public static void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		final EntityLivingBase entity = event.getEntityLiving();

		if (!entity.onGround)
		{
			return;
		}

		if (entity instanceof EntityPlayer)
		{
			if (((EntityPlayer) entity).isSpectator() || entity.isInWater())
			{
				return;
			}
		}

		AxisAlignedBB bb = entity.getEntityBoundingBox();

		// Set height of bounding box to 0.5, offset 0.5 into ground
		// Prevents scanning of all the blocks above an entity... performance optimizations
		AxisAlignedBB bbBelow = new AxisAlignedBB(bb.minX, bb.minY - 0.5D, bb.minZ, bb.maxX, bb.minY, bb.maxZ);

		if (WorldUtil.isBlockInAABB(bbBelow, entity.getEntityWorld(), BlocksAether.quicksoil.getDefaultState()))
		{
			AetherCore.PROXY.modifyEntityQuicksoil(entity);
		}
	}
}
