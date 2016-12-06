package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.renderer.entities.*;
import com.gildedgames.aether.client.renderer.entities.attachments.RenderParachute;
import com.gildedgames.aether.client.renderer.entities.companions.*;
import com.gildedgames.aether.client.renderer.entities.living.*;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderBattleBomb;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderBolt;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderDaggerfrostSnowball;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderDart;
import com.gildedgames.aether.client.renderer.items.RenderRewardItemStack;
import com.gildedgames.aether.client.renderer.tile_entities.*;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import com.gildedgames.aether.common.entities.living.companions.*;
import com.gildedgames.aether.common.entities.living.dungeon.labyrinth.*;
import com.gildedgames.aether.common.entities.living.boss.slider.EntitySlider;
import com.gildedgames.aether.common.entities.item.EntityPhoenixItem;
import com.gildedgames.aether.common.entities.item.EntityRewardItemStack;
import com.gildedgames.aether.common.entities.living.mobs.EntityAechorPlant;
import com.gildedgames.aether.common.entities.living.mobs.EntityCockatrice;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.entities.living.mobs.EntityTempest;
import com.gildedgames.aether.common.entities.living.mobs.EntityZephyr;
import com.gildedgames.aether.common.entities.living.mounts.EntityFlyingCow;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.living.mounts.EntityPhyg;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import com.gildedgames.aether.common.entities.living.passive.*;
import com.gildedgames.aether.common.entities.projectiles.*;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tiles.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityParachute.class, new AetherRenderFactory<>(RenderParachute.class));

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
		RenderingRegistry.registerEntityRenderingHandler(EntityEtherealWisp.class, new AetherRenderFactory<>(RenderEtherealWisp.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityFleetingWisp.class, new AetherRenderFactory<>(RenderFleetingWisp.class));
		RenderingRegistry.registerEntityRenderingHandler(EntitySoaringWisp.class, new AetherRenderFactory<>(RenderSoaringWisp.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityFangrin.class, new AetherRenderFactory<>(RenderFangrin.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityNexSpirit.class, new AetherRenderFactory<>(RenderNexSpirit.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityCockatrice.class, new AetherRenderFactory<>(RenderCockatrice.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityKirrid.class, new AetherRenderFactory<>(RenderKirrid.class));
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
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleGolem.class, new AetherRenderFactory<>(RenderBattleGolem.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleBomb.class, new AetherRenderFactory<>(RenderBattleBomb.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, new AetherRenderFactory<>(RenderButterfly.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityEdison.class, new AetherRenderFactory<>(RenderEdison.class));

		RenderingRegistry.registerEntityRenderingHandler(EntityTaegore.class, new TabulaRenderFactory<>(RenderTabula.class, AetherCore.getResource("textures/entities/taegore/taegore.png"), AetherCore.getResource("models/entities/taegore.tbl")));

		RenderingRegistry.registerEntityRenderingHandler(EntityRewardItemStack.class, new IRenderFactory<EntityRewardItemStack>()
		{
			@Override
			public Render<? super EntityRewardItemStack> createRenderFor(RenderManager manager)
			{
				return new RenderRewardItemStack(manager, Minecraft.getMinecraft().getRenderItem());
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntitySentryVaultbox.class, new IRenderFactory<Entity>()
		{
			@Override
			public Render<? super Entity> createRenderFor(RenderManager manager)
			{
				return new RenderSnowball<>(manager, ItemsAether.sentry_vaultbox, Minecraft.getMinecraft().getRenderItem());
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntitySlider.class, new AetherRenderFactory<>(RenderSlider.class));
	}

	private static void registerTESRs()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltar.class, new TileEntityAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkyrootChest.class, new TileEntitySkyrootChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkyrootSign.class, new TileEntitySkyrootSignRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLabyrinthTotem.class, new TileEntityLabyrinthTotemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLabyrinthChest.class, new TileEntityLabyrinthChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoaEgg.class, new TileEntityMoaEggRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPresent.class, new TileEntityPresentRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLabyrinthBridge.class, new TileEntityLabyrinthBridgeRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIcestoneCooler.class, new TileEntityIcestoneCoolerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMasonryBench.class, new TileEntityMasonryBenchRenderer());
	}
}
