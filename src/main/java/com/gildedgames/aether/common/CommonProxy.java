package com.gildedgames.aether.common;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.AetherArmorMaterials;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.recipes.RecipesAether;
import com.gildedgames.aether.common.tile_entities.TileEntitiesAether;
import com.gildedgames.aether.common.world.WorldProviderAether;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{
	public void construct(FMLConstructionEvent event)
	{

	}

	public void preInit(FMLPreInitializationEvent event)
	{
		// Register our content with Forge.
		DimensionManager.registerProviderType(Aether.getAetherBiome().biomeID, WorldProviderAether.class, true);
		DimensionManager.registerDimension(Aether.getAetherBiome().biomeID, Aether.getAetherBiome().biomeID);

		NetworkRegistry.INSTANCE.registerGuiHandler(Aether.INSTANCE, new AetherGuiHandler());

		// Pre-initialize content.
		AetherArmorMaterials.preInit();

		BlocksAether.preInit();
		ItemsAether.preInit();

		TileEntitiesAether.preInit();

		RecipesAether.preInit();
		AetherCreativeTabs.preInit();
	}

	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
	}
}
