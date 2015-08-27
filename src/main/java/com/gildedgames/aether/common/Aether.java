package com.gildedgames.aether.common;

import com.gildedgames.aether.common.services.AetherServices;
import com.gildedgames.util.core.SidedObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(name = Aether.MOD_NAME, modid = Aether.MOD_ID, version = Aether.MOD_VERSION)
public class Aether
{
	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.8-1.0";

	public static final int AETHER_DIM_ID = 3;

	@Instance
	public static Aether INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	private final SidedObject<AetherServices> services =
			new SidedObject<AetherServices>(new AetherServices(Side.CLIENT), new AetherServices(Side.SERVER));

	@EventHandler
	public void onFMLPreInit(FMLPreInitializationEvent event)
	{
		Aether.PROXY.preInit(event);
	}

	@EventHandler
	public void onFMLInit(FMLInitializationEvent event)
	{
		Aether.PROXY.init(event);
	}

	public static SidedObject<AetherServices> getServices()
	{
		return Aether.INSTANCE.services;
	}

	public static String getResourcePath(String resource)
	{
		return (Aether.MOD_ID + ":") + resource;
	}
}
