package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.analytics.GAReporter;
import com.gildedgames.aether.common.registry.SpawnRegistry;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.world.aether.TeleporterAether;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(name = AetherCore.MOD_NAME, modid = AetherCore.MOD_ID, version = AetherCore.MOD_VERSION,
		certificateFingerprint = AetherCore.MOD_FINGERPRINT, guiFactory = AetherCore.MOD_GUI_FACTORY)
public class AetherCore
{

	protected static final String MOD_GUI_FACTORY = "com.gildedgames.aether.client.gui.GuiFactoryAether";

	protected static final String MOD_FINGERPRINT = "b9a9be44fb51751dd1aec1dbb881b6de1a086abc";

	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.11.2-1.1.0";

	public static final Logger LOGGER = LogManager.getLogger("AetherII");

	@Instance(AetherCore.MOD_ID)
	public static AetherCore INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	public static ConfigAether CONFIG;

	public static GAReporter ANALYTICS;

	public static TeleporterAether TELEPORTER;

	private static final SpawnRegistry SPAWN_REGISTRY = new SpawnRegistry();

	@EventHandler
	public void onFMLConstruction(FMLConstructionEvent event)
	{
		AetherAPI.registerProvider(AetherCore.PROXY);
	}

	@EventHandler
	public void onFMLPreInit(FMLPreInitializationEvent event)
	{
		AetherCore.CONFIG = new ConfigAether(event.getSuggestedConfigurationFile());
		AetherCore.PROXY.preInit(event);

		AetherCore.SPAWN_REGISTRY.registerAetherSpawnHandlers();
	}

	@EventHandler
	public void onFMLInit(FMLInitializationEvent event)
	{
		AetherCore.PROXY.init(event);

		MinecraftForge.EVENT_BUS.register(SPAWN_REGISTRY);
	}

	@EventHandler
	public void onServerStopping(FMLServerStoppingEvent event)
	{
		DimensionsAether.onServerStopping(event);

		AetherCore.SPAWN_REGISTRY.write();
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		AetherCore.SPAWN_REGISTRY.read();
	}

	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event)
	{
		if (AetherCore.isInsideDevEnvironment())
		{
			return;
		}

		AetherCore.LOGGER.warn("Heads up! Forge has failed to validate the integrity of the Aether.");
		AetherCore.LOGGER.warn("The Aether may be packaged unofficially, tampered with, or corrupted. As a result, this build will not receive support.");
	}

	public static ResourceLocation getResource(String name)
	{
		return new ResourceLocation(AetherCore.MOD_ID, name);
	}

	public static String getResourcePath(String name)
	{
		return (AetherCore.MOD_ID + ":") + name;
	}

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}

	public static boolean isServer()
	{
		return FMLCommonHandler.instance().getSide().isServer();
	}

	public static File getWorldDirectory()
	{
		return DimensionManager.getCurrentSaveRootDirectory();
	}

	public static boolean isInsideDevEnvironment()
	{
		return Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE;
	}
}
