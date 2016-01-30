package com.gildedgames.aether.common;

import com.gildedgames.aether.common.world.chunk.PlacementFlagChunkData;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.Logger;

import com.gildedgames.aether.common.world.TeleporterAether;
import com.gildedgames.util.core.SidedObject;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(name = AetherCore.MOD_NAME, modid = AetherCore.MOD_ID, version = AetherCore.MOD_VERSION)
public class AetherCore
{
	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.8-1.0";

	@Instance(AetherCore.MOD_ID)
	public static AetherCore INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	public static AetherConfig CONFIG;

	public static Logger LOGGER;

	private final SidedObject<AetherServices> services = new SidedObject<>(new AetherServices(Side.CLIENT), new AetherServices(Side.SERVER));

	private static TeleporterAether teleporter;

	@EventHandler
	public void onFMLPreInit(FMLPreInitializationEvent event)
	{
		AetherCore.LOGGER = event.getModLog();

		AetherCore.CONFIG = new AetherConfig(event.getSuggestedConfigurationFile());

		AetherCore.PROXY.preInit(event);
	}

	@EventHandler
	public void onFMLInit(FMLInitializationEvent event)
	{
		AetherCore.PROXY.init(event);
	}

	@EventHandler
	public void onFMLPostInit(FMLPostInitializationEvent event)
	{
		AetherCore.PROXY.postInit(event);
	}

	@EventHandler
	public void onServerStarted(FMLServerStartedEvent event)
	{
		teleporter = new TeleporterAether(MinecraftServer.getServer().worldServerForDimension(getAetherDimID()));
	}

	public static AetherServices locate()
	{
		return AetherCore.INSTANCE.services.instance();
	}

	public static AetherServices client()
	{
		return AetherCore.INSTANCE.services.client();
	}

	public static AetherServices server()
	{
		return AetherCore.INSTANCE.services.server();
	}

	public static ResourceLocation getResource(String name)
	{
		return new ResourceLocation(AetherCore.MOD_ID, name);
	}

	public static String getResourcePath(String name)
	{
		return (AetherCore.MOD_ID + ":") + name;
	}

	public static int getAetherDimID()
	{
		return AetherCore.CONFIG.getAetherDimID();
	}

	public static TeleporterAether getTeleporter()
	{
		return teleporter;
	}
}
