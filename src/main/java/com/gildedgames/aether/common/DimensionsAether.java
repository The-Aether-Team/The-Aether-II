package com.gildedgames.aether.common;

import com.gildedgames.aether.common.world.TeleporterAether;
import com.gildedgames.aether.common.world.WorldProviderAether;
import com.gildedgames.aether.common.world.dungeon.labyrinth.dim.WorldProviderSliderLabyrinth;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DimensionsAether
{
	public static DimensionType AETHER;

	public static DimensionType SLIDER_LABYRINTH;

	public static void preInit()
	{
		// Register dimension types
		DimensionsAether.AETHER = DimensionType.register("Aether", "_aether",
				AetherCore.CONFIG.getAetherDimID(), WorldProviderAether.class, false);

		DimensionsAether.SLIDER_LABYRINTH = DimensionType.register("AetherSliderLabyrinth", "_aether_slider_labyrinth",
				AetherCore.CONFIG.getSliderLabyrinthDimID(), WorldProviderSliderLabyrinth.class, false);

		// Register dimensions
		DimensionManager.registerDimension(AetherCore.CONFIG.getAetherDimID(), DimensionsAether.AETHER);

		MinecraftForge.EVENT_BUS.register(DimensionsAether.class);
	}

	@SubscribeEvent
	public static void onWorldLoaded(WorldEvent.Load event)
	{
		if (!(event.getWorld() instanceof WorldServer))
		{
			return;
		}

		if (event.getWorld().provider.getDimensionType() == DimensionsAether.AETHER)
		{
			AetherCore.TELEPORTER = new TeleporterAether((WorldServer) event.getWorld());
		}
	}
}
