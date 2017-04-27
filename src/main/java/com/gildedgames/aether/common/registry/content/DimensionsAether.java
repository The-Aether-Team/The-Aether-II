package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.util.helpers.NBTHelper;
import com.gildedgames.aether.common.world.dimensions.aether.TeleporterAether;
import com.gildedgames.aether.common.world.dimensions.aether.WorldProviderAether;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DimensionsAether
{
	public static DimensionType AETHER;

	public static void preInit()
	{
		// Register dimension types
		DimensionsAether.AETHER = DimensionType.register("Aether", "_aether",
				AetherCore.CONFIG.getAetherDimID(), WorldProviderAether.class, false);

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

	public static void onServerStopping(FMLServerStoppingEvent event)
	{
		NBTTagCompound tag = new NBTTagCompound();

		AetherCore.TELEPORTER.write(tag);

		NBTHelper.writeNBTToFile(tag, "//data//teleporter.dat");
	}

}
