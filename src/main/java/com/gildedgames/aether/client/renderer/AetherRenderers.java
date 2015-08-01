package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.renderer.entities.RenderFloatingBlock;
import com.gildedgames.aether.client.renderer.entities.player.PlayerRenderHandler;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderDart;
import com.gildedgames.aether.client.renderer.tile_entity.TileEntityAltarRenderer;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.tile_entities.TileEntityAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class AetherRenderers
{
	public static void init()
	{
		registerEntityRenderers();
		registerTESRs();
	}

	private static void registerEntityRenderers()
	{
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

		RenderingRegistry.registerEntityRenderingHandler(EntityFloatingBlock.class, new RenderFloatingBlock(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(EntityDart.class, new RenderDart(renderManager));

		PlayerRenderHandler playerRenderHandler = new PlayerRenderHandler();

		MinecraftForge.EVENT_BUS.register(playerRenderHandler);
		FMLCommonHandler.instance().bus().register(playerRenderHandler);
	}

	private static void registerTESRs()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltar.class, new TileEntityAltarRenderer());
	}
}
