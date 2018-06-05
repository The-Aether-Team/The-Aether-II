package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.analytics.GAReporter;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = AetherCore.MOD_NAME, modid = AetherCore.MOD_ID, version = AetherCore.MOD_VERSION,
		certificateFingerprint = AetherCore.MOD_FINGERPRINT, guiFactory = AetherCore.MOD_GUI_FACTORY,
		dependencies = AetherCore.MOD_DEPENDENCIES)
public class AetherCore
{
	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.12.2-1.0.7";

	public static final String MOD_DEPENDENCIES = "required-after:orbis_api@1.12.2-1.1.4";

	public static final Logger LOGGER = LogManager.getLogger("AetherII");

	protected static final String MOD_GUI_FACTORY = "com.gildedgames.aether.client.gui.GuiFactoryAether";

	protected static final String MOD_FINGERPRINT = "b9a9be44fb51751dd1aec1dbb881b6de1a086abc";

	public static boolean isAetherLegacyInstalled;

	@Instance(AetherCore.MOD_ID)
	public static AetherCore INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	public static ConfigAether CONFIG;

	public static GAReporter ANALYTICS;

	public static ResourceLocation getResource(final String name)
	{
		return new ResourceLocation(AetherCore.MOD_ID, name);
	}

	public static String getResourcePath(final String name)
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

	public static boolean isInsideDevEnvironment()
	{
		return Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE;
	}

	@EventHandler
	public void onModConstruction(final FMLConstructionEvent event)
	{
		AetherAPI.registerProvider(AetherCore.PROXY);
	}

	@EventHandler
	public void onModPreInit(final FMLPreInitializationEvent event)
	{
		AetherCore.CONFIG = new ConfigAether(event.getSuggestedConfigurationFile());
		AetherCore.PROXY.preInit(event);
	}

	@EventHandler
	public void onModInit(final FMLInitializationEvent event)
	{
		isAetherLegacyInstalled = Loader.isModLoaded("aether_legacy");

		PROXY.init(event);
	}

	@EventHandler
	public void onModPostInit(final FMLPostInitializationEvent event)
	{
		PROXY.postInit(event);
	}

	@EventHandler
	public void onServerStarting(final FMLServerStartingEvent event)
	{
		PROXY.onServerStarting(event);
	}

	@EventHandler
	public void onServerStarted(final FMLServerStartedEvent event)
	{
		PROXY.onServerStarted(event);
	}

	@EventHandler
	public void onFingerprintViolation(final FMLFingerprintViolationEvent event)
	{
		if (AetherCore.isInsideDevEnvironment())
		{
			return;
		}

		AetherCore.LOGGER.warn("Heads up! Forge has failed to validate the integrity of the Aether.");
		AetherCore.LOGGER.warn("The Aether may be packaged unofficially, tampered with, or corrupted. As a result, this build will not receive support.");
	}

}
