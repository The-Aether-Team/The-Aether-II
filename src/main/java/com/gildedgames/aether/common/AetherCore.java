package com.gildedgames.aether.common;

import com.gildedgames.aether.api.IAetherServices;
import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.cooler.ITemperatureRegistry;
import com.gildedgames.aether.api.registry.equipment.IEquipmentRegistry;
import com.gildedgames.aether.common.registry.minecraft.DimensionsAether;
import com.gildedgames.aether.common.world.TeleporterAether;
import com.gildedgames.aether.common.registry.SpawnRegistry;
import com.gildedgames.aether.common.world.island.logic.IslandSectorAccess;
import com.gildedgames.util.io.ClassSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = AetherCore.MOD_NAME, modid = AetherCore.MOD_ID, version = AetherCore.MOD_VERSION, certificateFingerprint = AetherCore.MOD_FINGERPRINT, guiFactory = AetherCore.MOD_GUI_FACTORY)
public class AetherCore implements IAetherServices
{

	protected static final String MOD_GUI_FACTORY = "com.gildedgames.aether.client.gui.GuiFactoryAether";

	protected static final String MOD_FINGERPRINT = "b9a9be44fb51751dd1aec1dbb881b6de1a086abc";

	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.10.2-r1";

	public static final Logger LOGGER = LogManager.getLogger("AetherII");

	@Instance(AetherCore.MOD_ID)
	public static AetherCore INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	public static ConfigAether CONFIG;

	public static TeleporterAether TELEPORTER;

	private static final SpawnRegistry SPAWN_REGISTRY = new SpawnRegistry();

	private final ClassSerializer srl = new ClassSerializer(AetherCore.MOD_ID + "Srl");

	public static ClassSerializer srl()
	{
		return AetherCore.INSTANCE.srl;
	}

	@EventHandler
	public void onFMLPreInit(FMLPreInitializationEvent event)
	{
		AetherCore.CONFIG = new ConfigAether(event.getSuggestedConfigurationFile());

		MinecraftForge.EVENT_BUS.register(AetherCore.CONFIG);
		MinecraftForge.EVENT_BUS.register(IslandSectorAccess.inst());

		AetherCore.PROXY.preInit(event);

		AetherCore.SPAWN_REGISTRY.registerAetherSpawnHandlers();
	}

	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		AetherCore.PROXY.serverStarting(event);
	}

	@EventHandler
	public void onServerStopping(FMLServerStoppingEvent event)
	{
		DimensionsAether.onServerStopping(event);

		AetherCore.SPAWN_REGISTRY.write();
		IslandSectorAccess.inst().onServerStopping(event);
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		AetherCore.SPAWN_REGISTRY.read();
	}

	@EventHandler
	public void onFMLInit(FMLInitializationEvent event)
	{
		AetherCore.PROXY.init(event);

		MinecraftForge.EVENT_BUS.register(SPAWN_REGISTRY);
	}

	@EventHandler
	public void onFMLPostInit(FMLPostInitializationEvent event)
	{
		AetherCore.PROXY.postInit(event);
	}

	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event)
	{
		AetherCore.LOGGER.warn("Heads up! Forge has failed to validate the integrity of the Aether.");
		AetherCore.LOGGER.warn("This Aether mod could be running in a development workspace, corrupted, or packaged as an unofficial build. Proceed at your own risk.");
	}

	public static ResourceLocation getResource(String name)
	{
		return new ResourceLocation(AetherCore.MOD_ID, name);
	}

	public static String getResourcePath(String name)
	{
		return (AetherCore.MOD_ID + ":") + name;
	}

	@Override
	public IAltarRecipeRegistry getAltarRecipeRegistry()
	{
		return AetherCore.PROXY.getRecipeManager();
	}

	@Override
	public IEquipmentRegistry getEquipmentRegistry()
	{
		return AetherCore.PROXY.getEquipmentRegistry();
	}

	@Override
	public ITemperatureRegistry getTemperatureRegistry()
	{
		return AetherCore.PROXY.getCoolerRegistry();
	}

}
