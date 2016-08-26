package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.renderer.entities.AetherRenderFactory;
import com.gildedgames.aether.client.renderer.entities.RenderFloatingBlock;
import com.gildedgames.aether.client.renderer.entities.RenderMovingBlock;
import com.gildedgames.aether.client.renderer.entities.companions.*;
import com.gildedgames.aether.client.renderer.entities.living.*;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderBolt;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderDaggerfrostSnowball;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderDart;
import com.gildedgames.aether.client.renderer.tile_entities.*;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.entities.companions.*;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityBattleSentry;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityChestMimic;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityDetonationSentry;
import com.gildedgames.aether.common.entities.dungeon.labyrinth.EntityTrackingSentry;
import com.gildedgames.aether.common.entities.item.EntityPhoenixItem;
import com.gildedgames.aether.common.entities.living.*;
import com.gildedgames.aether.common.entities.living.enemies.EntityCockatrice;
import com.gildedgames.aether.common.entities.living.enemies.EntitySwet;
import com.gildedgames.aether.common.entities.living.enemies.EntityTempest;
import com.gildedgames.aether.common.entities.living.enemies.EntityZephyr;
import com.gildedgames.aether.common.entities.living.mounts.EntityFlyingCow;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.living.mounts.EntityPhyg;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import com.gildedgames.aether.common.entities.projectiles.EntityDart;
import com.gildedgames.aether.common.tile_entities.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class AetherRenderers
{
	public static void preInit()
	{
		registerEntityRenderers();
	}

	public static void init()
	{
		registerTESRs();
	}

	private static void registerEntityRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityFloatingBlock.class, new AetherRenderFactory<>(RenderFloatingBlock.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityMovingBlock.class, new AetherRenderFactory<>(RenderMovingBlock.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityDart.class, new AetherRenderFactory<>(RenderDart.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityBolt.class, new AetherRenderFactory<>(RenderBolt.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityDaggerfrostSnowball.class, new AetherRenderFactory<>(RenderDaggerfrostSnowball.class));

		RenderingRegistry.registerEntityRenderingHandler(EntityPhyg.class, new AetherRenderFactory<>(RenderPhyg.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingCow.class, new AetherRenderFactory<>(RenderFlyingCow.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityAechorPlant.class, new AetherRenderFactory<>(RenderAechorPlant.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityAerbunny.class, new AetherRenderFactory<>(RenderAerbunny.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrionSprout.class, new AetherRenderFactory<>(RenderCarrionSprout.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityPhoenixItem.class, new IRenderFactory<EntityPhoenixItem>()
		{
			@Override
			public Render<? super EntityPhoenixItem> createRenderFor(RenderManager manager)
			{
				return new RenderEntityItem(manager, Minecraft.getMinecraft().getRenderItem());
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityFrostpineTotem.class, new AetherRenderFactory<>(RenderFrostpineTotem.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityKraisith.class, new AetherRenderFactory<>(RenderKraisith.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadeOfArkenzus.class, new AetherRenderFactory<>(RenderShadeOfArkenzus.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityEtheralWisp.class, new AetherRenderFactory<>(RenderEtherealWisp.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityFleetingWisp.class, new AetherRenderFactory<>(RenderFleetingWisp.class));
		RenderingRegistry.registerEntityRenderingHandler(EntitySoaringWisp.class, new AetherRenderFactory<>(RenderSoaringWisp.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityFangrin.class, new AetherRenderFactory<>(RenderFangrin.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityNexSpirit.class, new AetherRenderFactory<>(RenderNexSpirit.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityCockatrice.class, new AetherRenderFactory<>(RenderCockatrice.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityRam.class, new AetherRenderFactory<>(RenderRam.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityMoa.class, new AetherRenderFactory<>(RenderMoa.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityAerwhale.class, new AetherRenderFactory<>(RenderAerwhale.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityZephyr.class, new AetherRenderFactory<>(RenderZephyr.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityTempest.class, new AetherRenderFactory<>(RenderTempest.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityDetonationSentry.class, new AetherRenderFactory<>(RenderDetonationSentry.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleSentry.class, new AetherRenderFactory<>(RenderBattleSentry.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityTrackingSentry.class, new AetherRenderFactory<>(RenderTrackingSentry.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityChestMimic.class, new AetherRenderFactory<>(RenderLabyrinthChestMimic.class));
		RenderingRegistry.registerEntityRenderingHandler(EntitySwet.class, new AetherRenderFactory<>(RenderSwet.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityPinkBabySwet.class, new AetherRenderFactory<>(RenderPinkBabySwet.class));
	}

	private static void registerTESRs()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltar.class, new TileEntityAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkyrootChest.class, new TileEntitySkyrootChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkyrootSign.class, new TileEntitySkyrootSignRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLabyrinthTotem.class, new TileEntityLabyrinthTotemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLabyrinthChest.class, new TileEntityLabyrinthChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoaEgg.class, new TileEntityMoaEggRenderer());
	}
}
