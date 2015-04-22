package com.gildedgames.aether;

import com.gildedgames.aether.blocks.BlocksAether;
import com.gildedgames.aether.items.ItemsAether;
import com.gildedgames.aether.recipes.RecipesAether;
import com.gildedgames.aether.server.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = Aether.MOD_NAME, modid = Aether.MOD_ID, version = Aether.MOD_VERSION)
public class Aether
{
	@Instance(Aether.MOD_ID)
	public static Aether INSTANCE;

	public static final String MOD_NAME = "Aether II";

	public static final String MOD_ID = "aether";

	public static final String MOD_VERSION = "1.8-1.0";

	@SidedProxy(clientSide = "com.gildedgames.aether.client.ClientProxy", serverSide = "com.gildedgames.aether.server.ServerProxy")
	public static ServerProxy PROXY;

	private BlocksAether blocks = new BlocksAether();

	private RecipesAether recipes = new RecipesAether();

	private ItemsAether items = new ItemsAether();

	private AetherCreativeTabs tabs = new AetherCreativeTabs();

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		this.blocks.preInit();
		this.items.preInit();
		this.recipes.preInit();
		this.tabs.preInit();

		Aether.PROXY.preInit();
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		Aether.PROXY.init();
	}

	public static BlocksAether getBlocks()
	{
		return Aether.INSTANCE.blocks;
	}

	public static RecipesAether getRecipes()
	{
		return Aether.INSTANCE.recipes;
	}

	public static ItemsAether getItems()
	{
		return Aether.INSTANCE.items;
	}

	public static AetherCreativeTabs getCreativeTabs()
	{
		return Aether.INSTANCE.tabs;
	}
}
