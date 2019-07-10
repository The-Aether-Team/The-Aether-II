package com.gildedgames.aether.common.events.listeners;

import com.gildedgames.aether.api.world.preparation.IPrepManager;
import com.gildedgames.aether.api.world.preparation.IPrepSector;
import com.gildedgames.aether.common.world.aether.prep.PrepAether;
import com.gildedgames.aether.common.world.aether.prep.PrepSectorDataAether;
import com.gildedgames.aether.common.world.preparation.util.PrepHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class ServerTickListener
{
	@SubscribeEvent
	public static void onServerTick(TickEvent.WorldTickEvent event)
	{
		IPrepManager access = PrepHelper.getManager(event.world);

		if (access == null || !(access.getRegistryEntry() instanceof PrepAether))
		{
			return;
		}

		for (IPrepSector sector : access.getAccess().getLoadedSectors())
		{
			if (sector.hasWatchers())
			{
				((PrepSectorDataAether) sector.getData()).tick();
			}
		}
	}
}
