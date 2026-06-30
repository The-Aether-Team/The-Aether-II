package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.TrunkBlock;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.renderer.accessory.AccessoryLayer;
import com.aetherteam.aetherii.client.renderer.accessory.GlovesLayer;
import com.aetherteam.aetherii.client.renderer.block.TrunkCornerModel;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.AmbientOcclusionLightModel;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.BreakingFixModel;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.CopyBlockModel;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.MuralModel;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.OverlaidLeavesModel;
import com.aetherteam.aetherii.client.renderer.blockentity.AbandonedBagRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.AlkahestPurifierRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.AltarRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.ArkeniumForgeRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.FungalCacheRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.MoaEggRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SageChestRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SentryCrateRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SentrySpawnerRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.ShelfRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootBedRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootChestRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.VaseRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AbandonedBagModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AlkahestPurifierModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.FungalCacheModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SageChestModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentryCrateModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.SentrySpawnerPistonModel;
import com.aetherteam.aetherii.client.renderer.blockentity.model.VaseModel;
import com.aetherteam.aetherii.client.renderer.entity.*;
import com.aetherteam.aetherii.client.renderer.entity.layers.ProjectilesStuckLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.SwetLatchLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.*;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.*;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.*;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.*;
import com.aetherteam.aetherii.client.renderer.item.AercloudGliderBakedModel;
import com.aetherteam.aetherii.client.renderer.item.HammerOfDemolitionBakedModel;
import com.aetherteam.aetherii.client.renderer.item.MoaEggBakedModel;
import com.aetherteam.aetherii.client.renderer.item.MoaFeatherBakedModel;
import com.aetherteam.aetherii.client.renderer.item.ReinforcedBakedModel;
import com.aetherteam.aetherii.client.renderer.item.ShieldBakedModel;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AetherIIRenderers {
    private static final Map<String, String> TRUNK_CORNERS = Map.of(
            "northwest_connection", "northwest",
            "northeast_connection", "northeast",
            "southeast_connection", "southeast",
            "southwest_connection", "southwest");

    private static final List<RegistryObject<TrunkBlock>> TRUNK_BLOCKS = List.of(
            AetherIIBlocks.SKYROOT_TRUNK,
            AetherIIBlocks.STRIPPED_SKYROOT_TRUNK,
            AetherIIBlocks.GREATROOT_TRUNK,
            AetherIIBlocks.STRIPPED_GREATROOT_TRUNK,
            AetherIIBlocks.WISPROOT_TRUNK,
            AetherIIBlocks.MOSSY_WISPROOT_TRUNK,
            AetherIIBlocks.STRIPPED_WISPROOT_TRUNK,
            AetherIIBlocks.AMBEROOT_TRUNK,
            AetherIIBlocks.STRIPPED_AMBEROOT_TRUNK,
            AetherIIBlocks.GUARDIAN_TRUNK,
            AetherIIBlocks.STRIPPED_GUARDIAN_TRUNK,
            AetherIIBlocks.INFECTED_TRUNK,
            AetherIIBlocks.STRIPPED_INFECTED_TRUNK);

    private static final List<RegistryObject<? extends Block>> OVERLAID_LEAF_BLOCKS = List.of(
            AetherIIBlocks.SKYROOT_LEAVES,
            AetherIIBlocks.SKYPLANE_LEAVES,
            AetherIIBlocks.SKYBIRCH_LEAVES,
            AetherIIBlocks.SKYPINE_LEAVES,
            AetherIIBlocks.WISPROOT_LEAVES,
            AetherIIBlocks.WISPTOP_LEAVES,
            AetherIIBlocks.GREATROOT_LEAVES,
            AetherIIBlocks.GREATOAK_LEAVES,
            AetherIIBlocks.GREATBOA_LEAVES,
            AetherIIBlocks.AMBEROOT_LEAVES,
            AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES,
            AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES,
            AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES,
            AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES,
            AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES,
            AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES,
            AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES,
            AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES,
            AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES);

    private static final List<RegistryObject<? extends Block>> AO_BLOCKS = List.of(
            AetherIIBlocks.AMBROSIUM_ORE,
            AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE,
            AetherIIBlocks.SENTRY_BRICKS,
            AetherIIBlocks.SENTRY_BRICK_STAIRS,
            AetherIIBlocks.SENTRY_BRICK_SLAB,
            AetherIIBlocks.SENTRY_BRICK_WALL,
            AetherIIBlocks.SENTRY_LIGHTSTONE,
            AetherIIBlocks.SENTRY_FLAGSTONES,
            AetherIIBlocks.SENTRY_TILE,
            AetherIIBlocks.SENTRY_BASE_BRICKS,
            AetherIIBlocks.SENTRY_CAPSTONE_BRICKS,
            AetherIIBlocks.SENTRY_BASE_PILLAR,
            AetherIIBlocks.SENTRY_CAPSTONE_PILLAR,
            AetherIIBlocks.SENTRY_PILLAR,
            AetherIIBlocks.BLOOMING_ARILUM,
            AetherIIBlocks.BLOOMING_ARILUM_PLANT,
            AetherIIBlocks.SPOTTED_MAGNETIC_SHROOM_BLOCK,
            AetherIIBlocks.LUCENT_GUARDIAN_ROOTS,
            AetherIIBlocks.GUARDIAN_LAMP);

    private static final List<RegistryObject<? extends Block>> BREAKING_FIX_BLOCKS = List.of(
            AetherIIBlocks.AETHER_GRASS_BLOCK);

    private static final List<RegistryObject<? extends Block>> COPY_BLOCKS = List.of(
            AetherIIBlocks.LOCKED_BLOCK,
            AetherIIBlocks.BOSS_DOORWAY_BLOCK,
            AetherIIBlocks.TREASURE_DOORWAY_BLOCK);

    private static final List<RegistryObject<? extends Block>> MURAL_BLOCKS = List.of(
            AetherIIBlocks.MURAL);

    private static final List<String> SHIELD_ITEMS = List.of(
            "skyroot_shield",
            "burrukai_plate_shield",
            "zanite_shield",
            "arkenium_shield",
            "gravitite_shield");

    public static void registerAddLayer(EntityRenderersEvent.AddLayers event) {
        for (String skin : event.getSkins()) {
            LivingEntityRenderer<? extends Player, ? extends EntityModel<? extends Player>> renderer = event.getSkin(skin);
            if (renderer instanceof PlayerRenderer playerRenderer) {
                registerPlayerLayers(playerRenderer);
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void registerPlayerLayers(PlayerRenderer renderer) {
        renderer.addLayer(new SwetLatchLayer(renderer));
        renderer.addLayer(new GlovesLayer(renderer));
        renderer.addLayer(new AccessoryLayer(renderer));
        renderer.addLayer(new ProjectilesStuckLayer(renderer));
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_CHEST.get(), SkyrootChestRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.MOA_EGG.get(), MoaEggRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.ALTAR.get(), AltarRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.ALKAHEST_PURIFIER.get(), AlkahestPurifierRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.ARKENIUM_FORGE.get(), ArkeniumForgeRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SENTRY_CRATE.get(), SentryCrateRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SENTRY_SPAWNER.get(), SentrySpawnerRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.VASE.get(), VaseRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.AMBROSIUM_CAMPFIRE.get(), CampfireRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.ABANDONED_BAG.get(), AbandonedBagRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.FUNGAL_CACHE.get(), FungalCacheRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SAGE_CHEST.get(), SageChestRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SHELF.get(), ShelfRenderer::new);

        event.registerEntityRenderer(AetherIIEntityTypes.PHYG.get(), PhygRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), context -> new TaegoreRenderer(context, BiomeVariantPresets.HIGHFIELDS_TAEGORE));
        event.registerEntityRenderer(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), context -> new TaegoreRenderer(context, BiomeVariantPresets.MAGNETIC_TAEGORE));
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), context -> new TaegoreRenderer(context, BiomeVariantPresets.ARCTIC_TAEGORE));
        event.registerEntityRenderer(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), context -> new BurrukaiRenderer(context, BiomeVariantPresets.HIGHFIELDS_BURRUKAI));
        event.registerEntityRenderer(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), context -> new BurrukaiRenderer(context, BiomeVariantPresets.MAGNETIC_BURRUKAI));
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), context -> new BurrukaiRenderer(context, BiomeVariantPresets.ARCTIC_BURRUKAI));
        event.registerEntityRenderer(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), context -> new KirridRenderer(context, BiomeVariantPresets.HIGHFIELDS_KIRRID));
        event.registerEntityRenderer(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), context -> new KirridRenderer(context, BiomeVariantPresets.MAGNETIC_KIRRID));
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_KIRRID.get(), context -> new KirridRenderer(context, BiomeVariantPresets.ARCTIC_KIRRID));
        event.registerEntityRenderer(AetherIIEntityTypes.MOA.get(), MoaRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.PRISMALLARD.get(), PrismallardRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKYROOT_LIZARD.get(), SkyrootLizardRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.GLITTERWING.get(), GlitterwingRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SHROUDWING.get(), ShroudwingRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.CARRION_SPROUT.get(), CarrionSproutRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.BLUE_SWET.get(), BlueSwetRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.GOLDEN_SWET.get(), GoldenSwetRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKEPHID.get(), SkephidRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.TEMPEST.get(), TempestRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), ArkeniumTalutonRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), GravititeTalutonRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.MIMIC.get(), MimicRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.DETONATION_SENTRY.get(), DetonationSentryRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SENTRY_GOLEM.get(), SentryGolemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SLIDER.get(), SliderRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.BLADESHROOM_HUNTER.get(), BladeshroomHunterRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.EDWARD.get(), EdwardRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.HOLYSTONE_ROCK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKYROOT_PINECONE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.PRISMALLARD_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.LASSO_LOOP.get(), LassoLoopRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SCATTERGLASS_BOLT.get(), ScatterglassBoltRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.AMBER_DART.get(), AmberDartRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.TOXIC_DART.get(), ToxicDartRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.VENOMOUS_DART.get(), VenomousDartRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR_WEBBING_BALL.get(), ZephyrWebbingBallRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKEPHID_WEBBING_BALL.get(), SkephidWebbingBallRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.TEMPEST_THUNDERBALL.get(), TempestThunderballRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.GRAVITITE_DEBRIS_SHOT.get(), GravititeDebrisShotRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SITTABLE.get(), NoopRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.HOVERING_BLOCK.get(), HoveringBlockRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.CLOUD_SKIFF.get(), CloudSkiffRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ELECTRIC_FIELD.get(), NoopRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.DEMOLITION_PROJECTILE.get(), DemolitionProjectileRenderer::new);
    }

    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_BED_FOOT, SkyrootBedRenderer::createFootLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_BED_HEAD, SkyrootBedRenderer::createHeadLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA_EGG, MoaEggModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ALKAHEST_PURIFIER, AlkahestPurifierModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SENTRY_CRATE, SentryCrateModel::createSingleBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_LEFT, SentryCrateModel::createDoubleBodyLeftLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_RIGHT, SentryCrateModel::createDoubleBodyRightLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SENTRY_SPAWNER, SentrySpawnerModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SENTRY_SPAWNER_PISTON, SentrySpawnerPistonModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.VASE, VaseModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ABANDONED_BAG, AbandonedBagModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.FUNGAL_CACHE, FungalCacheModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SAGE_CHEST, SageChestModel::createSingleBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DOUBLE_SAGE_CHEST_LEFT, SageChestModel::createDoubleBodyLeftLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DOUBLE_SAGE_CHEST_RIGHT, SageChestModel::createDoubleBodyRightLayer);
        event.registerLayerDefinition(AetherIIModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.AERBUNNY_COLLAR, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.PHYG, PhygModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.AERWHALE, AerwhaleModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.FLYING_COW, FlyingCowModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SHEEPUFF, SheepuffModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.HIGHFIELDS_TAEGORE, TaegoreModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.HIGHFIELDS_TAEGORE_BABY, TaegoreBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MAGNETIC_TAEGORE, TaegoreModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MAGNETIC_TAEGORE_BABY, TaegoreBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARCTIC_TAEGORE, TaegoreModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARCTIC_TAEGORE_BABY, TaegoreBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.HIGHFIELDS_BURRUKAI, BurrukaiModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.HIGHFIELDS_BURRUKAI_BABY, BurrukaiBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MAGNETIC_BURRUKAI, BurrukaiModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MAGNETIC_BURRUKAI_BABY, BurrukaiBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARCTIC_BURRUKAI, ArcticBurrukaiModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARCTIC_BURRUKAI_BABY, BurrukaiBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.HIGHFIELDS_KIRRID, HighfieldsKirridModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.HIGHFIELDS_KIRRID_BABY, HighfieldsKirridBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MAGNETIC_KIRRID, MagneticKirridModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MAGNETIC_KIRRID_BABY, MagneticKirridBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARCTIC_KIRRID, ArcticKirridModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARCTIC_KIRRID_BABY, ArcticKirridBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA, MoaModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA_BABY, MoaBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA_SADDLE, MoaSaddleModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA_SADDLEBAG, MoaSaddlebagModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA_LARGE_SADDLEBAG, MoaLargeSaddlebagModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.PRISMALLARD, PrismallardModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_LIZARD, SkyrootLizardModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.GLITTERWING, GlitterwingModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SHROUDWING, ShroudwingModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.AECHOR_PLANT, AechorPlantModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.CARRION_SPROUT, CarrionSproutModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.TEMPEST, TempestModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.COCKATRICE, CockatriceModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SWET, SwetModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.BLUE_SWET, SwetModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.GOLDEN_SWET, SwetModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SKEPHID, SkephidModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARKENIUM_TALUTON, ArkeniumTalutonModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.GRAVITITE_TALUTON, GravititeTalutonModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DETONATION_SENTRY, DetonationSentryModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SENTRY_GOLEM, SentryGolemModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MIMIC, MimicModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SLIDER, SliderModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.BLADESHROOM_HUNTER, BladeshroomHunterModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.EDWARD, EdwardModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.CLOUD_SKIFF, CloudSkiffModel::createLayer);
        event.registerLayerDefinition(AetherIIModelLayers.GRAVITITE_DEBRIS_SHOT, GravititeDebrisShotModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DEMOLITION_PROJECTILE, DemolitionProjectileModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), false));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), true));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_SLIM_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), true));
        event.registerLayerDefinition(AetherIIModelLayers.ACCESSORY, () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.0F), 64, 32));
    }

    public static boolean isFastBlock(net.minecraft.world.level.block.state.BlockState state) {
        List<Block> fastBlocks = List.of(
                AetherIIBlocks.SKYROOT_LEAF_PILE.get(),
                AetherIIBlocks.SKYPLANE_LEAF_PILE.get(),
                AetherIIBlocks.SKYBIRCH_LEAF_PILE.get(),
                AetherIIBlocks.SKYPINE_LEAF_PILE.get(),
                AetherIIBlocks.WISPROOT_LEAF_PILE.get(),
                AetherIIBlocks.WISPTOP_LEAF_PILE.get(),
                AetherIIBlocks.GREATROOT_LEAF_PILE.get(),
                AetherIIBlocks.GREATOAK_LEAF_PILE.get(),
                AetherIIBlocks.GREATBOA_LEAF_PILE.get(),
                AetherIIBlocks.AMBEROOT_LEAF_PILE.get(),
                AetherIIBlocks.AETHER_BUSH.get(),
                AetherIIBlocks.BLUEBERRY_BUSH.get(),
                AetherIIBlocks.POTTED_AETHER_BUSH.get(),
                AetherIIBlocks.POTTED_BLUEBERRY_BUSH.get(),
                AetherIIBlocks.TANGLED_BRANCHES.get(),
                AetherIIBlocks.UNDERGROWTH_LEAVES.get());
        return fastBlocks.contains(state.getBlock());
    }

    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        for (RegistryObject<TrunkBlock> block : TRUNK_BLOCKS) {
            String path = block.getId().getPath();
            for (String corner : TRUNK_CORNERS.values()) {
                event.register(trunkCornerLocation(path, corner, false));
                event.register(trunkCornerLocation(path, corner, true));
            }
        }
        for (String shield : SHIELD_ITEMS) {
            event.register(shieldBlockingModelLocation(shield));
        }
        registerHammerOfDemolitionModels(event);
        registerAercloudGliderModels(event);
        registerMoaFeatherModels(event);
        registerMoaEggModels(event);
        registerReinforcedItemModels(event);
    }

    public static void bakeModels(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> models = event.getModels();
        wrapBlockModels(models, OVERLAID_LEAF_BLOCKS, OverlaidLeavesModel::new);
        wrapBlockModels(models, AO_BLOCKS, AmbientOcclusionLightModel::new);
        wrapBlockModels(models, BREAKING_FIX_BLOCKS, BreakingFixModel::new);
        wrapBlockModels(models, MURAL_BLOCKS, MuralModel::new);
        wrapBlockModels(models, COPY_BLOCKS, CopyBlockModel::new);
        wrapShieldItemModels(models);
        wrapHammerOfDemolitionItemModel(models);
        wrapAercloudGliderItemModels(models);
        wrapMoaFeatherItemModel(models);
        wrapMoaEggItemModel(models);
        wrapReinforcedItemModels(models);
        for (RegistryObject<TrunkBlock> block : TRUNK_BLOCKS) {
            ResourceLocation blockId = block.getId();
            Map<String, BakedModel> cornerModels = loadTrunkCornerModels(models, blockId.getPath(), false);
            Map<String, BakedModel> cornerTallModels = loadTrunkCornerModels(models, blockId.getPath(), true);
            if (cornerModels.size() != TRUNK_CORNERS.size() || cornerTallModels.size() != TRUNK_CORNERS.size()) {
                continue;
            }

            List<ResourceLocation> modelLocations = new ArrayList<>();
            for (ResourceLocation modelLocation : models.keySet()) {
                if (modelLocation.getNamespace().equals(blockId.getNamespace()) && modelLocation.getPath().equals(blockId.getPath())) {
                    modelLocations.add(modelLocation);
                }
            }
            for (ResourceLocation modelLocation : modelLocations) {
                models.put(modelLocation, new TrunkCornerModel(models.get(modelLocation), cornerModels, cornerTallModels));
            }
        }
    }

    private static void registerReinforcedItemModels(ModelEvent.RegisterAdditional event) {
        for (ResourceLocation modelLocation : ReinforcedBakedModel.requiredModels()) {
            event.register(modelLocation);
        }
    }

    private static void wrapReinforcedItemModels(Map<ResourceLocation, BakedModel> models) {
        Map<ResourceLocation, String> modelLocations = new HashMap<>();
        for (String itemName : ReinforcedBakedModel.itemNames()) {
            BakedModel reinforced1 = models.get(ReinforcedBakedModel.itemModel(itemName + "_reinforced_1"));
            BakedModel reinforced2 = models.get(ReinforcedBakedModel.itemModel(itemName + "_reinforced_2"));
            if (reinforced1 == null || reinforced2 == null) {
                continue;
            }
            for (ResourceLocation modelLocation : models.keySet()) {
                if (modelLocation.getNamespace().equals(AetherII.MODID)
                        && modelLocation.getPath().equals(itemName)
                        && (!(modelLocation instanceof ModelResourceLocation modelResourceLocation) || modelResourceLocation.getVariant().equals("inventory"))) {
                    modelLocations.put(modelLocation, itemName);
                }
            }
        }
        for (Map.Entry<ResourceLocation, String> entry : modelLocations.entrySet()) {
            String itemName = entry.getValue();
            models.put(entry.getKey(), new ReinforcedBakedModel(
                    models.get(entry.getKey()),
                    itemName,
                    models.get(ReinforcedBakedModel.itemModel(itemName + "_reinforced_1")),
                    models.get(ReinforcedBakedModel.itemModel(itemName + "_reinforced_2"))));
        }
    }

    private static Map<String, BakedModel> loadTrunkCornerModels(Map<ResourceLocation, BakedModel> models, String path, boolean tall) {
        return TRUNK_CORNERS.entrySet().stream()
                .filter(entry -> models.containsKey(trunkCornerLocation(path, entry.getValue(), tall)))
                .collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, entry -> models.get(trunkCornerLocation(path, entry.getValue(), tall))));
    }

    private static ResourceLocation trunkCornerLocation(String path, String corner, boolean tall) {
        return new ResourceLocation(AetherII.MODID, "block/" + path + "_corner" + (tall ? "_tall" : "") + "_" + corner);
    }

    private static void wrapBlockModels(Map<ResourceLocation, BakedModel> models, List<? extends RegistryObject<? extends Block>> blocks, Function<BakedModel, BakedModel> wrapper) {
        List<ResourceLocation> modelLocations = new ArrayList<>();
        for (RegistryObject<? extends Block> block : blocks) {
            ResourceLocation blockId = block.getId();
            String path = blockId.getPath();
            for (ResourceLocation modelLocation : models.keySet()) {
                if (modelLocation.getNamespace().equals(blockId.getNamespace()) && isBlockModelPath(modelLocation.getPath(), path)) {
                    modelLocations.add(modelLocation);
                }
            }
        }
        for (ResourceLocation modelLocation : modelLocations) {
            models.put(modelLocation, wrapper.apply(models.get(modelLocation)));
        }
    }

    private static boolean isBlockModelPath(String modelPath, String blockPath) {
        return modelPath.equals(blockPath) || modelPath.equals("block/" + blockPath) || modelPath.startsWith("block/" + blockPath + "_");
    }

    private static void wrapShieldItemModels(Map<ResourceLocation, BakedModel> models) {
        Map<String, BakedModel> blockingModels = new HashMap<>();
        for (String shield : SHIELD_ITEMS) {
            ResourceLocation blockingLocation = shieldBlockingModelLocation(shield);
            BakedModel blockingModel = models.get(blockingLocation);
            if (blockingModel != null) {
                BakedModel wrappedBlockingModel = new ShieldBakedModel(blockingModel, shield, null);
                models.put(blockingLocation, wrappedBlockingModel);
                blockingModels.put(shield, wrappedBlockingModel);
            }
        }

        List<ResourceLocation> modelLocations = new ArrayList<>();
        for (String shield : SHIELD_ITEMS) {
            for (ResourceLocation modelLocation : models.keySet()) {
                if (isShieldInventoryModel(modelLocation, shield)) {
                    modelLocations.add(modelLocation);
                }
            }
        }
        for (ResourceLocation modelLocation : modelLocations) {
            String shield = modelLocation.getPath();
            models.put(modelLocation, new ShieldBakedModel(models.get(modelLocation), shield, blockingModels.get(shield)));
        }
    }

    private static boolean isShieldInventoryModel(ResourceLocation modelLocation, String shield) {
        return modelLocation.getNamespace().equals(AetherII.MODID)
                && modelLocation.getPath().equals(shield)
                && (!(modelLocation instanceof ModelResourceLocation modelResourceLocation) || modelResourceLocation.getVariant().equals("inventory"));
    }

    private static ResourceLocation shieldBlockingModelLocation(String shield) {
        return new ResourceLocation(AetherII.MODID, "item/" + shield + "_blocking");
    }

    private static void registerHammerOfDemolitionModels(ModelEvent.RegisterAdditional event) {
        for (ResourceLocation modelLocation : HammerOfDemolitionBakedModel.requiredModels()) {
            event.register(modelLocation);
        }
    }

    private static void wrapHammerOfDemolitionItemModel(Map<ResourceLocation, BakedModel> models) {
        List<ResourceLocation> modelLocations = new ArrayList<>();
        for (ResourceLocation modelLocation : models.keySet()) {
            if (modelLocation.getNamespace().equals(AetherII.MODID)
                    && modelLocation.getPath().equals("hammer_of_demolition")
                    && (!(modelLocation instanceof ModelResourceLocation modelResourceLocation) || modelResourceLocation.getVariant().equals("inventory"))) {
                modelLocations.add(modelLocation);
            }
        }
        for (ResourceLocation modelLocation : modelLocations) {
            models.put(modelLocation, new HammerOfDemolitionBakedModel(models.get(modelLocation), models));
        }
    }

    private static void registerAercloudGliderModels(ModelEvent.RegisterAdditional event) {
        for (ResourceLocation modelLocation : AercloudGliderBakedModel.requiredModels()) {
            event.register(modelLocation);
        }
    }

    private static void wrapAercloudGliderItemModels(Map<ResourceLocation, BakedModel> models) {
        List<ResourceLocation> modelLocations = new ArrayList<>();
        for (String gliderName : AercloudGliderBakedModel.gliderNames()) {
            for (ResourceLocation modelLocation : models.keySet()) {
                if (modelLocation.getNamespace().equals(AetherII.MODID)
                        && modelLocation.getPath().equals(gliderName)
                        && (!(modelLocation instanceof ModelResourceLocation modelResourceLocation) || modelResourceLocation.getVariant().equals("inventory"))) {
                    modelLocations.add(modelLocation);
                }
            }
        }
        for (ResourceLocation modelLocation : modelLocations) {
            models.put(modelLocation, new AercloudGliderBakedModel(models.get(modelLocation), models, modelLocation.getPath()));
        }
    }

    private static void registerMoaEggModels(ModelEvent.RegisterAdditional event) {
        for (Moa.FeatherShape shape : Moa.FeatherShape.values()) {
            for (Moa.FeatherColor color : Moa.FeatherColor.values()) {
                event.register(MoaEggBakedModel.featherModel(shape, color));
            }
        }
        for (Moa.EyeColor color : Moa.EyeColor.values()) {
            event.register(MoaEggBakedModel.eyesModel(color));
        }
        for (Moa.KeratinColor color : Moa.KeratinColor.values()) {
            event.register(MoaEggBakedModel.keratinModel(color));
        }
    }

    private static void registerMoaFeatherModels(ModelEvent.RegisterAdditional event) {
        for (ResourceLocation modelLocation : MoaFeatherBakedModel.requiredModels()) {
            event.register(modelLocation);
        }
    }

    private static void wrapMoaFeatherItemModel(Map<ResourceLocation, BakedModel> models) {
        List<ResourceLocation> modelLocations = new ArrayList<>();
        for (ResourceLocation modelLocation : models.keySet()) {
            if (modelLocation.getNamespace().equals(AetherII.MODID)
                    && modelLocation.getPath().equals("moa_feather")
                    && (!(modelLocation instanceof ModelResourceLocation modelResourceLocation) || modelResourceLocation.getVariant().equals("inventory"))) {
                modelLocations.add(modelLocation);
            }
        }
        for (ResourceLocation modelLocation : modelLocations) {
            models.put(modelLocation, new MoaFeatherBakedModel(models.get(modelLocation), models));
        }
    }

    private static void wrapMoaEggItemModel(Map<ResourceLocation, BakedModel> models) {
        List<ResourceLocation> modelLocations = new ArrayList<>();
        for (ResourceLocation modelLocation : models.keySet()) {
            if (modelLocation.getNamespace().equals(AetherII.MODID)
                    && modelLocation.getPath().equals("moa_egg")
                    && (!(modelLocation instanceof ModelResourceLocation modelResourceLocation) || modelResourceLocation.getVariant().equals("inventory"))) {
                modelLocations.add(modelLocation);
            }
        }
        for (ResourceLocation modelLocation : modelLocations) {
            models.put(modelLocation, new MoaEggBakedModel(models.get(modelLocation), models));
        }
    }
}
