package com.gildedgames.aether.common.events.listeners.entity;

import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.spawn.ISpawnHandler;
import com.gildedgames.aether.api.world.spawn.ISpawnSystem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EntityDeathListener
{
	@SubscribeEvent
	public static void onLivingDeath(final LivingDeathEvent event)
	{
		final EntityLivingBase entity = event.getEntityLiving();

		final ISpawnSystem system = entity.getCapability(CapabilitiesAether.SPAWN_SYSTEM, null);

		if (system != null)
		{
			for (ISpawnHandler handler : system.getSpawnHandlers())
			{
				handler.onLivingDeath(event);
			}
		}
	}
}
