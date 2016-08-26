package com.gildedgames.aether.common;

import com.gildedgames.aether.common.world.TeleporterAether;
import com.gildedgames.aether.common.world.WorldProviderAether;
import com.gildedgames.aether.common.world.labyrinth.WorldProviderSliderLabyrinth;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.util.GGHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

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

			NBTTagCompound tag = GGHelper.readNBTFromFile("//data//teleporter.dat");

			if (tag != null)
			{
				AetherCore.TELEPORTER.read(tag);
			}
		}
	}

	public static void onServerStopping(FMLServerStoppingEvent event)
	{
		NBTTagCompound tag = new NBTTagCompound();

		AetherCore.TELEPORTER.write(tag);

		GGHelper.writeNBTToFile(tag, "//data//teleporter.dat");
	}

}
