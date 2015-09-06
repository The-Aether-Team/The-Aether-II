package com.gildedgames.aether.common;

import com.gildedgames.util.core.SidedObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(name = AetherCore.MOD_NAME, modid = AetherCore.MOD_ID, version = AetherCore.MOD_VERSION)
public class AetherCore
{
	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.8-1.0";

	public static final boolean DEBUG_MODE = true;

	@Instance
	public static AetherCore INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	private final SidedObject<AetherServices> services = new SidedObject<AetherServices>(new AetherServices(Side.CLIENT), new AetherServices(Side.SERVER));

	@EventHandler
	public void onFMLPreInit(FMLPreInitializationEvent event)
	{
		AetherCore.PROXY.preInit(event);
	}

	@EventHandler
	public void onFMLInit(FMLInitializationEvent event)
	{
		AetherCore.PROXY.init(event);
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

	public static String getResourcePath(String resource)
	{
		return (AetherCore.MOD_ID + ":") + resource;
	}

	public static void print(Object line)
	{
		if (DEBUG_MODE && line != null)
		{
			System.out.println("[AETHER_II]: " + line.toString());
		}
	}

	public static int aetherDimId()
	{
		return AetherConfig.AetherDimensionID;
	}

	public static int dungeonDimId()
	{
		return AetherConfig.DungeonDimensionID;
	}
}
