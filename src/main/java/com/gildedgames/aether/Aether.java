package com.gildedgames.aether;

import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.common.services.AetherServices;
import com.gildedgames.aether.world.biome.BiomeGenAether;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.player.PlayerCore;
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
	public static final int AETHER_DIM_ID = 3;

	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.8-1.0";

	@Instance(Aether.MOD_ID)
	public static Aether INSTANCE;

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.common.CommonProxy")
	public static CommonProxy PROXY;

	private final BiomeGenAether aetherBiome = new BiomeGenAether(AETHER_DIM_ID);

	private SidedObject<AetherServices> services;

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		this.services = new SidedObject<AetherServices>(new AetherServices(Side.CLIENT), new AetherServices(Side.SERVER));
		PlayerCore.INSTANCE.registerPlayerPool(this.services.client().getPool(), this.services.server().getPool());

		Aether.PROXY.preInit(event);
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		Aether.PROXY.init(event);
	}

	public static BiomeGenAether getAetherBiome()
	{
		return Aether.INSTANCE.aetherBiome;
	}

	public static SidedObject<AetherServices> getServices()
	{
		return Aether.INSTANCE.services;
	}

	public static String getResource(String resource)
	{
		return (Aether.MOD_ID + ":") + resource;
	}
}
