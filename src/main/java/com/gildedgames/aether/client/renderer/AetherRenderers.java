package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.models.entities.living.ModelAechorPlant;
import com.gildedgames.aether.client.models.entities.living.ModelAerbunny;
import com.gildedgames.aether.client.models.entities.living.ModelSheepuff;
import com.gildedgames.aether.client.renderer.entities.RenderFloatingBlock;
import com.gildedgames.aether.client.renderer.entities.living.RenderAechorPlant;
import com.gildedgames.aether.client.renderer.entities.living.RenderAerbunny;
import com.gildedgames.aether.client.renderer.entities.living.RenderFlyingCow;
import com.gildedgames.aether.client.renderer.entities.living.RenderPhyg;
import com.gildedgames.aether.client.renderer.entities.living.RenderSheepuff;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderDart;
import com.gildedgames.aether.client.renderer.tile_entity.TileEntityAltarRenderer;
import com.gildedgames.aether.client.renderer.tile_entity.TileEntitySkyrootChestRenderer;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.living.EntityAechorPlant;
import com.gildedgames.aether.common.entities.living.EntityAerbunny;
import com.gildedgames.aether.common.entities.living.mounts.EntityFlyingCow;
import com.gildedgames.aether.common.entities.living.mounts.EntityPhyg;
import com.gildedgames.aether.common.entities.living.EntitySheepuff;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.tile_entities.TileEntityAltar;
import com.gildedgames.aether.common.tile_entities.TileEntitySkyrootChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

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

		RenderingRegistry.registerEntityRenderingHandler(EntityPhyg.class, new RenderPhyg(renderManager, new ModelPig(), 0.7f));
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingCow.class, new RenderFlyingCow(renderManager, new ModelCow(), 0.7f));
		RenderingRegistry.registerEntityRenderingHandler(EntitySheepuff.class, new RenderSheepuff(renderManager, new ModelSheepuff(), 0.7f));
		RenderingRegistry.registerEntityRenderingHandler(EntityAechorPlant.class, new RenderAechorPlant(renderManager, new ModelAechorPlant(), 0.7f));
		RenderingRegistry.registerEntityRenderingHandler(EntityAerbunny.class, new RenderAerbunny(renderManager, new ModelAerbunny(), 0.7f));
	}

	private static void registerTESRs()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltar.class, new TileEntityAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkyrootChest.class, new TileEntitySkyrootChestRenderer());
	}
}
