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
	public static void onChunkLoaded(ChunkEvent.Load event)
	{
		World world = event.getWorld();

		if (world.hasCapability(AetherCapabilities.SECTOR_ACCESS, null))
		{
			ISectorAccess access = world.getCapability(AetherCapabilities.SECTOR_ACCESS, null);

			access.onChunkLoaded(event.getChunk().xPosition, event.getChunk().zPosition);
		}
	}

	@SubscribeEvent
	public static void onChunkUnloaded(ChunkEvent.Unload event)
	{
		World world = event.getWorld();

		if (world.hasCapability(AetherCapabilities.SECTOR_ACCESS, null))
		{
			ISectorAccess access = world.getCapability(AetherCapabilities.SECTOR_ACCESS, null);

			access.onChunkUnloaded(event.getChunk().xPosition, event.getChunk().zPosition);
		}
	}

	@SubscribeEvent
	public static void onWorldSaved(WorldEvent.Save event)
	{
		World world = event.getWorld();

		if (world.hasCapability(AetherCapabilities.SECTOR_ACCESS, null))
		{
			ISectorAccess access = world.getCapability(AetherCapabilities.SECTOR_ACCESS, null);

			access.flush();
		}
	}
}
