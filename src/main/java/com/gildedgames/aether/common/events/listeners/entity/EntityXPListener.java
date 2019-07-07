package com.gildedgames.aether.common.events.listeners.entity;

import com.gildedgames.aether.common.init.DimensionsAether;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EntityXPListener
{
	@SubscribeEvent
	public static void onEntity(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityXPOrb)
		{
			if (event.getWorld().provider.getDimensionType() == DimensionsAether.AETHER
					|| event.getWorld().provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onExperienceDrop(LivingExperienceDropEvent event)
	{
		if (event.getEntityLiving().world.provider.getDimensionType() == DimensionsAether.AETHER
				|| event.getEntityLiving().world.provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
		{
			event.setCanceled(true);
		}
	}
}
