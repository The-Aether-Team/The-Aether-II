package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.api.capabilites.entity.boss.IBoss;
import com.gildedgames.aether.api.capabilites.entity.boss.IBossManager;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BossProcessor
{

	@SubscribeEvent
	public static void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof IBoss)
		{
			IBoss boss = (IBoss)event.getEntity();
			IBossManager manager = boss.getBossManager();

			manager.updateStagesAndActions();
		}
	}

}
