package com.gildedgames.aether;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.gildedgames.aether.blocks.BlocksAether;
import com.gildedgames.aether.recipes.RecipesAether;

@Mod(name = Aether.MOD_NAME, modid = Aether.MOD_ID, version = Aether.MOD_VERSION)
public class Aether
{
	@Instance(Aether.MOD_ID)
	public static Aether INSTANCE;

	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.8-1.0";

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.CommonProxy")
	public static CommonProxy PROXY;

	private BlocksAether blocks = new BlocksAether();

	private RecipesAether recipes = new RecipesAether();

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		Aether.PROXY.init();

		this.blocks.preInit();
		this.recipes.preInit();
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		this.blocks.init();
	}

	public static BlocksAether getBlocks()
	{
		return Aether.INSTANCE.blocks;
	}

	public static RecipesAether getRecipes()
	{
		return Aether.INSTANCE.recipes;
	}
}
