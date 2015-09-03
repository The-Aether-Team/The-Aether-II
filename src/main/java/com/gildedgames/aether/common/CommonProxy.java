package com.gildedgames.aether.common;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.AetherArmorMaterials;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.recipes.RecipesAether;
import com.gildedgames.aether.common.tile_entities.TileEntitiesAether;
import com.gildedgames.aether.common.util.CreativeTab;
import com.gildedgames.aether.common.world.WorldProviderAether;
import com.gildedgames.util.player.PlayerCore;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{
	
	public final CreativeTab TabBlocks = new CreativeTab("aetherBlocks");

	public final CreativeTab TabMaterials = new CreativeTab("aetherMaterials");

	public final CreativeTab TabTools = new CreativeTab("aetherTools");

	public final CreativeTab TabWeapons = new CreativeTab("aetherWeapons");

	public final CreativeTab TabArmor = new CreativeTab("aetherArmor");

	public final CreativeTab TabConsumables = new CreativeTab("aetherConsumables");
	
	public void construct(FMLConstructionEvent event)
	{
		
	}

	public void preInit(FMLPreInitializationEvent event)
	{
		// Register our content with GGUtil.
		PlayerCore.INSTANCE.registerPlayerPool(AetherCore.client().getPool(), AetherCore.server().getPool());

		// Register with NetworkRegistry.
		NetworkRegistry.INSTANCE.registerGuiHandler(AetherCore.INSTANCE, new AetherGuiHandler());

		// Register dimensions and biomes.
		DimensionManager.registerProviderType(AetherCore.AETHER_DIM_ID, WorldProviderAether.class, true);

		DimensionManager.registerDimension(AetherCore.AETHER_DIM_ID, AetherCore.AETHER_DIM_ID);

		// Pre-initialize content.
		AetherArmorMaterials.preInit();

		BlocksAether.preInit();
		ItemsAether.preInit();

		TileEntitiesAether.preInit();
		EntitiesAether.preInit();

		RecipesAether.preInit();
	}

	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
	}
}
