package com.gildedgames.aether.client.renderer;

import com.gildedgames.aether.client.renderer.entities.RenderFloatingBlock;
import com.gildedgames.aether.client.renderer.entities.RenderMovingBlock;
import com.gildedgames.aether.client.renderer.entities.attachments.RenderParachute;
import com.gildedgames.aether.client.renderer.entities.companions.*;
import com.gildedgames.aether.client.renderer.entities.living.*;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderBattleBomb;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderBolt;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderDaggerfrostSnowball;
import com.gildedgames.aether.client.renderer.entities.projectiles.RenderDart;
import com.gildedgames.aether.client.renderer.items.RenderRewardItemStack;
import com.gildedgames.aether.client.renderer.tile_entities.*;
import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import com.gildedgames.aether.common.entities.item.EntityRewardItemStack;
import com.gildedgames.aether.common.entities.living.boss.slider.EntitySlider;
import com.gildedgames.aether.common.entities.living.companions.*;
import com.gildedgames.aether.common.entities.living.dungeon.labyrinth.*;
import com.gildedgames.aether.common.entities.living.mobs.*;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.living.npc.EntityEdison;
import com.gildedgames.aether.common.entities.living.passive.*;
import com.gildedgames.aether.common.entities.projectiles.*;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tiles.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityFloatingBlock.class, RenderFloatingBlock::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMovingBlock.class, RenderMovingBlock::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDart.class, RenderDart::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBolt.class, RenderBolt::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDaggerfrostSnowball.class, RenderDaggerfrostSnowball::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityParachute.class, RenderParachute::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityAechorPlant.class, RenderAechorPlant::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAerbunny.class, RenderAerbunny::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrionSprout.class, RenderCarrionSprout::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityFrostpineTotem.class, RenderFrostpineTotem::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityKraisith.class, RenderKraisith::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShadeOfArkenzus.class, RenderShadeOfArkenzus::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEtherealWisp.class, RenderEtherealWisp::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFleetingWisp.class, RenderFleetingWisp::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySoaringWisp.class, RenderSoaringWisp::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFangrin.class, RenderFangrin::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityNexSpirit.class, RenderNexSpirit::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCockatrice.class, RenderCockatrice::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityKirrid.class, RenderKirrid::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMoa.class, RenderMoa::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAerwhale.class, RenderAerwhale::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityZephyr.class, RenderZephyr::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTempest.class, RenderTempest::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDetonationSentry.class, RenderDetonationSentry::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleSentry.class, RenderBattleSentry::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTrackingSentry.class, RenderTrackingSentry::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityChestMimic.class, RenderLabyrinthChestMimic::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySwet.class, RenderSwet::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityPinkBabySwet.class, RenderPinkBabySwet::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleGolem.class, RenderBattleGolem::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleBomb.class, RenderBattleBomb::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGlitterwing.class, RenderGlitterwing::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEdison.class, RenderEdison::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTaegore.class, RenderTaegore::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBurrukai.class, RenderBurrukai::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRewardItemStack.class, manager -> new RenderRewardItemStack(manager, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntitySentryVaultbox.class, manager -> new RenderSnowball<>(manager, ItemsAether.sentry_vaultbox, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntitySlider.class, RenderSlider::new);
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOutpostCampfire.class, new TileEntityOutpostCampfireRenderer());
	}
}
