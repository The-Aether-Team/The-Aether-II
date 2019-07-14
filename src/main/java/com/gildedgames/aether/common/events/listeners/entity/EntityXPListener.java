package com.gildedgames.aether.common.events.listeners.entity;

import com.gildedgames.aether.common.init.DimensionsAether;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityXPListener
{
	@SubscribeEvent
	public static void onEntity(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof ExperienceOrbEntity)
		{
			if (event.getWorld().getDimension().getType() == DimensionsAether.AETHER
					|| event.getWorld().getDimension().getType() == DimensionsAether.NECROMANCER_TOWER)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onExperienceDrop(LivingExperienceDropEvent event)
	{
		if (event.getEntityLiving().world.getDimension().getType() == DimensionsAether.AETHER
				|| event.getEntityLiving().world.getDimension().getType() == DimensionsAether.NECROMANCER_TOWER)
		{
			event.setCanceled(true);
		}
	}
}
