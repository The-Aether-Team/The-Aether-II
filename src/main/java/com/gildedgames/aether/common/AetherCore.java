package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.analytics.GAReporter;
import com.gildedgames.aether.common.util.JarValidator;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = AetherCore.MOD_NAME, modid = AetherCore.MOD_ID, version = AetherCore.MOD_VERSION,
		certificateFingerprint = AetherCore.MOD_FINGERPRINT,
		dependencies = AetherCore.MOD_DEPENDENCIES)
public class AetherCore
{
	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "0.3.0";

	public static final String MOD_DEPENDENCIES = "required-after:orbis-lib@[0.2.0,);" + "required-after:forge@[14.23.5.2816,)";

	public static final Logger LOGGER = LogManager.getLogger("AetherII");

	public static final String MOD_FINGERPRINT = "db341c083b1b8ce9160a769b569ef6737b3f4cdf";

	@Instance(AetherCore.MOD_ID)
	public static AetherCore INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	public static ConfigAether CONFIG;

	public static GAReporter ANALYTICS;

	public static boolean IS_SIGNED = true;

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
		AetherCore.CONFIG = new ConfigAether();

		if (!AetherCore.isInsideDevEnvironment() && !JarValidator.validate(event.getSourceFile()))
		{
			AetherCore.LOGGER.warn("Failed to validate extended properties for the file located at " + event.getSourceFile());

			AetherCore.IS_SIGNED = false;
		}

		AetherCore.PROXY.preInit(event);
	}

	@EventHandler
	public void onModInit(final FMLInitializationEvent event)
	{
		AetherCore.PROXY.init(event);
	}

	@EventHandler
	public void onModPostInit(final FMLPostInitializationEvent event)
	{
		AetherCore.PROXY.postInit(event);
	}

	@EventHandler
	public void onServerAboutToStart(final FMLServerAboutToStartEvent event)
	{
		AetherCore.PROXY.onServerAboutToStart(event);
	}

	@EventHandler
	public void onServerStarting(final FMLServerStartingEvent event)
	{
		AetherCore.PROXY.onServerStarting(event);
	}

	@EventHandler
	public void onFingerprintViolation(final FMLFingerprintViolationEvent event)
	{
		if (AetherCore.isInsideDevEnvironment())
		{
			AetherCore.LOGGER.warn("Ignoring missing certificate for the Aether II because we are in a de-obfuscated workspace...");

			return;
		}

		AetherCore.IS_SIGNED = false;

		AetherCore.LOGGER.warn("No valid certificates could be found for the file located at: " + event.getSource());
		AetherCore.LOGGER.warn("You have downloaded a release of the Aether II which does not contain a valid code signing certificate. This release will not receive support from Gilded Games.");
	}
}
