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

		// Do not scan blocks beneath a player if they are not on the ground
		if (!entity.onGround)
		{
			return;
		}

		// Do not affect entities in water
		if (entity.isInWater())
		{
			return;
		}

		// Do not scan blocks beneath "sleeping" entities (entities which are not moving)
		// Due to floating point inaccuracy, we use an epsilon of 0.001
		if (Math.abs(entity.motionX + entity.motionY + entity.motionZ) < 0.001D)
		{
			return;
		}

		// Spectators should not be affected by quicksoil
		if (entity instanceof EntityPlayer)
		{
			if (((EntityPlayer) entity).isSpectator())
			{
				return;
			}
		}

		AxisAlignedBB bb = entity.getEntityBoundingBox();

		// Set height of bounding box to 0.3, offset 0.3 into ground
		// Prevents scanning of all the blocks above an entity
		AxisAlignedBB bbBelow = new AxisAlignedBB(bb.minX - 0.1D, bb.minY - 0.3D, bb.minZ - 0.1D, bb.maxX + 0.1D, bb.minY, bb.maxZ + 0.1D);

		// We assume the blocks under an entity are always loaded
		if (WorldUtil.isBlockInAABB(bbBelow, entity.getEntityWorld(), BlocksAether.quicksoil.getDefaultState(), false))
		{
			AetherCore.PROXY.modifyEntityQuicksoil(entity);
		}
	}
}
