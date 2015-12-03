package com.gildedgames.aether.common;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.player.PlayerAetherEventHandler;
import com.gildedgames.aether.common.recipes.RecipesAether;
import com.gildedgames.aether.common.tile_entities.TileEntitiesAether;
import com.gildedgames.aether.common.world.WorldProviderAether;
import com.gildedgames.util.player.PlayerCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
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
		// Load the configuration file.
		AetherCore.CONFIG.load();

		// Register our content with GGUtil.
		PlayerCore.INSTANCE.registerPlayerPool(AetherCore.client().getPool(), AetherCore.server().getPool());

		// Register with NetworkRegistry.
		NetworkRegistry.INSTANCE.registerGuiHandler(AetherCore.INSTANCE, new AetherGuiHandler());

		// Register dimensions and biomes.
		DimensionManager.registerProviderType(AetherCore.getAetherDimID(), WorldProviderAether.class, true);
		DimensionManager.registerDimension(AetherCore.getAetherDimID(), AetherCore.getAetherDimID());

		// Pre-initialize content.
		AetherMaterials.preInit();

		BlocksAether.preInit();
		ItemsAether.preInit();

		TileEntitiesAether.preInit();
		EntitiesAether.preInit();

		RecipesAether.preInit();
	}

	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
		MinecraftForge.EVENT_BUS.register(new PlayerAetherEventHandler());

		MinecraftForge.EVENT_BUS.register(ItemsAether.skyroot_sword);
	}

	public void spawnJumpParticles(EntityPlayer player)
	{
		for (int i = 0; i < 16; i++)
		{
			double x = player.posX + (player.worldObj.rand.nextFloat() * player.width * 2.5f) - (player.width * 1.25f);
			double y = player.posY + (player.worldObj.rand.nextFloat() * 0.2f) - 0.2f;
			double z = player.posZ + (player.worldObj.rand.nextFloat() * player.width * 2.5f) - (player.width * 1.25f);

			player.worldObj.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, 0.0D, 0.06D, 0.0D);
		}
	}
}
