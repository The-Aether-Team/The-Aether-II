package com.gildedgames.aether.common;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.gui.misc.CustomLoadingRenderer;
import com.gildedgames.aether.common.analytics.GAReporter;
import com.gildedgames.aether.common.registry.SpawnRegistry;
import com.gildedgames.aether.common.registry.content.CurrencyAether;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.helpers.PerfHelper;
import com.gildedgames.aether.common.world.aether.TeleporterAether;
import com.gildedgames.orbis_api.preparation.impl.util.PrepHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(name = AetherCore.MOD_NAME, modid = AetherCore.MOD_ID, version = AetherCore.MOD_VERSION,
		certificateFingerprint = AetherCore.MOD_FINGERPRINT, guiFactory = AetherCore.MOD_GUI_FACTORY,
		dependencies = AetherCore.MOD_DEPENDENCIES)
public class AetherCore
{

	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.12.2-1.0.5";

	public static final String MOD_DEPENDENCIES = "required-after:orbis_api@1.12.2-1.1.2";

	public static final Logger LOGGER = LogManager.getLogger("AetherII");

	protected static final String MOD_GUI_FACTORY = "com.gildedgames.aether.client.gui.GuiFactoryAether";

	protected static final String MOD_FINGERPRINT = "b9a9be44fb51751dd1aec1dbb881b6de1a086abc";

	private static final SpawnRegistry SPAWN_REGISTRY = new SpawnRegistry();

	public static boolean isAetherLegacyInstalled;

	@Instance(AetherCore.MOD_ID)
	public static AetherCore INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	public static ConfigAether CONFIG;

	public static GAReporter ANALYTICS;

	public static TeleporterAether TELEPORTER;

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

	public static File getWorldDirectory()
	{
		return DimensionManager.getCurrentSaveRootDirectory();
	}

	public static boolean isInsideDevEnvironment()
	{
		return Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE;
	}

	@EventHandler
	public void onFMLConstruction(final FMLConstructionEvent event)
	{
		AetherAPI.registerProvider(AetherCore.PROXY);
	}

	@EventHandler
	public void onFMLPreInit(final FMLPreInitializationEvent event)
	{
		AetherCore.CONFIG = new ConfigAether(event.getSuggestedConfigurationFile());
		AetherCore.PROXY.preInit(event);
	}

	@EventHandler
	@SideOnly(Side.CLIENT)
	public void onFMLPreInitClient(final FMLPreInitializationEvent event)
	{
		Minecraft.getMinecraft().loadingScreen = new CustomLoadingRenderer(Minecraft.getMinecraft(), Minecraft.getMinecraft().loadingScreen);
	}

	@EventHandler
	public void onFMLInit(final FMLInitializationEvent event)
	{
		isAetherLegacyInstalled = Loader.isModLoaded("aether_legacy");

		AetherCore.LOGGER.info("OrbisAPI is installed: " + Loader.isModLoaded("orbis_api"));

		AetherCore.PROXY.init(event);

		MinecraftForge.EVENT_BUS.register(SPAWN_REGISTRY);
	}

	@EventHandler
	public void onServerStopping(final FMLServerStoppingEvent event)
	{
		DimensionsAether.onServerStopping(event);
	}

	@EventHandler
	public void serverStarted(final FMLServerStartedEvent event)
	{
		World world = DimensionManager.getWorld(CONFIG.getAetherDimID());

		// TODO: In SpongeForge, the world is not loaded yet for some reason?
		if (world != null)
		{
			PrepHelper.getManager(world).getAccess().provideSectorForChunk(0, 0);
		}

		PROXY.serverStarted(event);

		AetherAPI.content().currency().clearRegistrations();
		PerfHelper.measure("Initialize currency", CurrencyAether::serverStarted);
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
