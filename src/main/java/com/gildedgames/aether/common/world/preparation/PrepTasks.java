package com.gildedgames.aether.common.world.preparation;

import com.gildedgames.aether.api.world.preparation.IPrepManager;
import com.gildedgames.aether.common.world.preparation.util.PrepHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PrepTasks
{
	@SubscribeEvent
	public static void onChunkLoaded(final ChunkEvent.Load event)
	{
		final World world = event.getWorld();

		IPrepManager manager = PrepHelper.getManager(world);

		if (manager != null)
		{
			manager.getAccess().onChunkLoaded(event.getChunk().x, event.getChunk().z);
		}
	}

	@SubscribeEvent
	public static void onChunkUnloaded(final ChunkEvent.Unload event)
	{
		final World world = event.getWorld();

		IPrepManager manager = PrepHelper.getManager(world);

		if (manager != null)
		{
			manager.getAccess().onChunkUnloaded(event.getChunk().x, event.getChunk().z);
		}
	}

	@SubscribeEvent
	public static void onWorldTick(final TickEvent.WorldTickEvent event)
	{
		final World world = event.world;

		IPrepManager manager = PrepHelper.getManager(world);

		if (manager != null)
		{
			manager.getAccess().update();
		}
	}
}
