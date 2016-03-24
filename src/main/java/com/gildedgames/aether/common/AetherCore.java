package com.gildedgames.aether.common;

import com.gildedgames.aether.common.world.TeleporterAether;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.exceptions.IOManagerTakenException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = AetherCore.MOD_NAME, modid = AetherCore.MOD_ID, version = AetherCore.MOD_VERSION, certificateFingerprint = AetherCore.MOD_FINGERPRINT)
public class AetherCore
{
	protected static final String MOD_FINGERPRINT = "b9a9be44fb51751dd1aec1dbb881b6de1a086abc";

	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.8.9-r1";

	public static final Logger LOGGER = LogManager.getLogger("AetherII");

	@Instance(AetherCore.MOD_ID)
	public static AetherCore INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	private static SidedObject<AetherServices> serviceLocator = new SidedObject<>(new AetherServices(Side.CLIENT), new AetherServices(Side.SERVER));

	public static AetherConfig CONFIG;

	private static TeleporterAether teleporter;

	public static AetherServices locate()
	{
		return serviceLocator.instance();
	}

	@EventHandler
	public void onFMLPreInit(FMLPreInitializationEvent event)
	{
		try
		{
			IOCore.io().registerManager(AetherCore.locate().getIOManager());
		}
		catch (IOManagerTakenException e)
		{
			e.printStackTrace();
		}

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

	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event)
	{
		AetherCore.LOGGER.warn("The Aether's signature is invalid, and will not receive support. Either you're running in a development environment, your JAR is corrupted, " +
				"or you've downloaded an unofficial version of the Aether II. Proceed at your own risk.");
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
