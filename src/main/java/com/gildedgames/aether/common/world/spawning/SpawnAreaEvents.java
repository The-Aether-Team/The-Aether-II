package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.world.ISpawnHandler;
import com.gildedgames.aether.api.world.ISpawnSystem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpawnAreaEvents
{
	@SubscribeEvent
	public static void onLivingDeath(final LivingDeathEvent event)
	{
		final EntityLivingBase entity = event.getEntityLiving();

		if (entity.hasCapability(AetherCapabilities.ENTITY_SPAWNING_INFO, null))
		{
			final ISpawnSystem system = entity.getCapability(AetherCapabilities.SPAWN_SYSTEM, null);

			if (system != null)
			{
				for (ISpawnHandler handler : system.getSpawnHandlers())
				{
					handler.onLivingDeath(event);
				}
			}
		}
	}
}
