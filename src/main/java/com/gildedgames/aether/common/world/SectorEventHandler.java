package com.gildedgames.aether.common.world;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.world.ISectorAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SectorEventHandler
{
	@SubscribeEvent
	public static void onChunkLoaded(final ChunkEvent.Load event)
	{
		final World world = event.getWorld();

		if (world.hasCapability(AetherCapabilities.SECTOR_ACCESS, null))
		{
			final ISectorAccess access = world.getCapability(AetherCapabilities.SECTOR_ACCESS, null);

			access.onChunkLoaded(event.getChunk().x, event.getChunk().z);
		}
	}

	@SubscribeEvent
	public static void onChunkUnloaded(final ChunkEvent.Unload event)
	{
		final World world = event.getWorld();

		if (world.hasCapability(AetherCapabilities.SECTOR_ACCESS, null))
		{
			final ISectorAccess access = world.getCapability(AetherCapabilities.SECTOR_ACCESS, null);

			access.onChunkUnloaded(event.getChunk().x, event.getChunk().z);
		}
	}

	@SubscribeEvent
	public static void onWorldSaved(final WorldEvent.Save event)
	{
		final World world = event.getWorld();

		if (world.hasCapability(AetherCapabilities.SECTOR_ACCESS, null))
		{
			final ISectorAccess access = world.getCapability(AetherCapabilities.SECTOR_ACCESS, null);

			access.flush();
		}
	}
}
