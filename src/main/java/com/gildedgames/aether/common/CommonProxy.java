package com.gildedgames.aether.common;

import java.io.File;
import java.util.Random;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.entities.effects.EntityEffectsEventHooks;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.player.PlayerAetherEventHooks;
import com.gildedgames.aether.common.player.PlayerAetherTracker;
import com.gildedgames.aether.common.recipes.RecipesAether;
import com.gildedgames.aether.common.tile_entities.TileEntitiesAether;
import com.gildedgames.aether.common.world.WorldProviderAether;
import com.gildedgames.aether.common.world.chunk.PlacementFlagFactory;
import com.gildedgames.util.modules.chunk.ChunkModule;

public class CommonProxy
{
	private AetherGuiHandler guiHandler;

	private File storageDir;

	public void construct(FMLConstructionEvent event)
	{

	}

	public void preInit(FMLPreInitializationEvent event)
	{
		// Load the configuration file.
		AetherCore.CONFIG.load();

		this.storageDir = new File(event.getSourceFile().getParent(), "Aether/");

		// Register with NetworkRegistry.
		NetworkRegistry.INSTANCE.registerGuiHandler(AetherCore.INSTANCE, this.guiHandler = new AetherGuiHandler());

		// Register dimensions and biomes.
		DimensionManager.registerProviderType(AetherCore.getAetherDimID(), WorldProviderAether.class, true);
		DimensionManager.registerDimension(AetherCore.getAetherDimID(), AetherCore.getAetherDimID());

		// Pre-initialize content.
		AetherMaterials.preInit();

		BlocksAether.preInit();
		ItemsAether.preInit();

		NetworkingAether.preInit();

		TileEntitiesAether.preInit();
		EntitiesAether.preInit();

		RecipesAether.preInit();
	}

	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
		MinecraftForge.EVENT_BUS.register(new PlayerAetherTracker());
		MinecraftForge.EVENT_BUS.register(new PlayerAetherEventHooks());
		MinecraftForge.EVENT_BUS.register(new EntityEffectsEventHooks());

		MinecraftForge.EVENT_BUS.register(ItemsAether.skyroot_sword);

		ChunkModule.locate().registerHookFactory(new PlacementFlagFactory());
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}

	public void spawnJumpParticles(World world, double x, double y, double z, double radius, int quantity)
	{
		Random random = world.rand;

		for (int i = 0; i < quantity; i++)
		{
			double x2 = x + (random.nextDouble() * radius) - (radius * 0.5D);
			double y2 = y + (random.nextDouble() * 0.2D);
			double z2 = z + (random.nextDouble() * radius) - (radius * 0.5D);

			world.spawnParticle(EnumParticleTypes.CLOUD, x2, y2, z2, 0.0D, random.nextDouble() * 0.03D, 0.0D);
		}
	}

	public AetherGuiHandler getGuiHandler()
	{
		return this.guiHandler;
	}

	public File getAetherStorageDir()
	{
		return this.storageDir;
	}
}
