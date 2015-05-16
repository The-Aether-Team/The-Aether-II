package com.gildedgames.aether.common;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherCreativeTabs;
import com.gildedgames.aether.blocks.BlocksAether;
import com.gildedgames.aether.items.ItemsAether;
import com.gildedgames.aether.recipes.RecipesAether;
import com.gildedgames.aether.world.WorldProviderAether;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void construct(FMLConstructionEvent event)
	{

	}

	public void preInit(FMLPreInitializationEvent event)
	{
		DimensionManager.registerProviderType(Aether.getAetherBiome().biomeID, WorldProviderAether.class, true);
		DimensionManager.registerDimension(Aether.getAetherBiome().biomeID, Aether.getAetherBiome().biomeID);

		// Pre-initialize content.
		BlocksAether.preInit();
		ItemsAether.preInit();

		RecipesAether.preInit();
		AetherCreativeTabs.preInit();
	}

	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
	}
}
