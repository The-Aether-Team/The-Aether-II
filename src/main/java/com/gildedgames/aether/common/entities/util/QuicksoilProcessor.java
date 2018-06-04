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

		if (entity instanceof EntityPlayer)
		{
			if (((EntityPlayer) entity).isSpectator() || entity.isInWater())
			{
				return;
			}
		}

		AxisAlignedBB bb = entity.getEntityBoundingBox().offset(0.0D, -0.2D, 0.0D).expand(0.0D, 0.2D, 0.0D);

		if (WorldUtil.isBlockInAABB(bb, entity.getEntityWorld(), BlocksAether.quicksoil.getDefaultState()))
		{
			AetherCore.PROXY.modifyEntityQuicksoil(entity);
		}
	}
}
