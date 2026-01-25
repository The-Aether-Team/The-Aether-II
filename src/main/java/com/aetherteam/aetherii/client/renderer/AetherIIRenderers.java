package com.aetherteam.aetherii.client.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.renderer.accessory.GlovesLayer;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.BreakingFixModel;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.TrunkModel;
import com.aetherteam.aetherii.client.renderer.blockentity.*;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.AmbientOcclusionLightModel;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.FastModel;
import com.aetherteam.aetherii.client.renderer.blockentity.AlkahestPurifierRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.ArkeniumForgeRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.MoaEggRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootBedRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootChestRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.model.AlkahestPurifierModel;
import com.aetherteam.aetherii.client.renderer.entity.*;
import com.aetherteam.aetherii.client.renderer.entity.layers.ProjectilesStuckLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.SwetLatchLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.*;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.ArcticBurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.ArcticKirridBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.ArcticKirridModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.HighfieldsKirridBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.HighfieldsKirridModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.MagneticKirridBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.MagneticKirridModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import com.aetherteam.aetherii.client.renderer.item.model.AlkahestPurifierSpecialRenderer;
import com.aetherteam.aetherii.client.renderer.item.model.ShieldModel;
import com.aetherteam.aetherii.client.renderer.item.model.SkyrootBedSpecialRenderer;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.aetherteam.aetherii.entity.passive.Moa;

import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

public class AetherIIRenderers {
    public static final ContextKey<Boolean> RIDING_SKIFF_KEY = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "riding_skiff"));
    public static final ContextKey<Float> SKIFF_STEERING_KEY = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skiff_steering"));
    public static final ContextKey<Boolean> RIDING_MOA_KEY = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "riding_moa"));
    public static final ContextKey<List<SwetRenderState>> SWET_KEY = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "swet"));
    public static final ContextKey<List<EntityType<?>>> STUCK_PROJECTILES_KEY = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "stuck_projectiles"));

    public static void registerAddLayer(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(model -> {
            if (event.getSkin(model) instanceof LivingEntityRenderer<?, ?, ?> livingEntityRenderer) {
                registerLivingEntityLayers(event.getContext(), livingEntityRenderer);
                if (livingEntityRenderer instanceof PlayerRenderer playerRenderer) {
                    playerRenderer.addLayer(new ProjectilesStuckLayer<>(playerRenderer, event.getContext()));
                }
            }
        });
    }

    private static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> void registerLivingEntityLayers(EntityRendererProvider.Context context, LivingEntityRenderer<T, S, M> livingEntityRenderer) {
        livingEntityRenderer.addLayer(new SwetLatchLayer<S, M>(livingEntityRenderer));
        livingEntityRenderer.addLayer(new GlovesLayer<S, M>(livingEntityRenderer));
    }

    public static void registerRenderStateModifier(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(PlayerRenderer.class, (abstractClientPlayer, playerRenderState) -> {
            List<Swet> swets = abstractClientPlayer.getData(AetherIIDataAttachments.SWET_LATCH).getLatchedSwets();
            if (swets != null) {
                List<SwetRenderState> states = new ArrayList<>();
                for (Swet swet : swets) {
                    SwetRenderState state = new SwetRenderState();
                    state.entityType = swet.getType();
                    state.swetScale = swet.getSwetScale();
                    states.add(state);
                }
                playerRenderState.setRenderData(SWET_KEY, states);
            }
            playerRenderState.setRenderData(RIDING_MOA_KEY, abstractClientPlayer.getVehicle() instanceof Moa);
            if (abstractClientPlayer.getVehicle() instanceof CloudSkiff cloudSkiff) {
                playerRenderState.setRenderData(RIDING_SKIFF_KEY, true);
                playerRenderState.setRenderData(SKIFF_STEERING_KEY, cloudSkiff.steering);
            }
            playerRenderState.setRenderData(STUCK_PROJECTILES_KEY, abstractClientPlayer.getData(AetherIIDataAttachments.PLAYER).getStuckProjectiles());
        });
    }


    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Blocks
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_CHEST.get(), SkyrootChestRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.MOA_EGG.get(), MoaEggRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.ALTAR.get(), AltarRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.ARKENIUM_FORGE.get(), ArkeniumForgeRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.ALKAHEST_PURIFIER.get(), AlkahestPurifierRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.AMBROSIUM_CAMPFIRE.get(), CampfireRenderer::new);

        // Entities
        // Passive
        event.registerEntityRenderer(AetherIIEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.PHYG.get(), PhygRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.AERWHALE.get(), AerwhaleRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), (context) -> new TaegoreRenderer(context, BiomeVariantPresets.HIGHFIELDS_TAEGORE));
        event.registerEntityRenderer(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), (context) -> new TaegoreRenderer(context, BiomeVariantPresets.MAGNETIC_TAEGORE));
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), (context) -> new TaegoreRenderer(context, BiomeVariantPresets.ARCTIC_TAEGORE));
        event.registerEntityRenderer(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), (context) -> new BurrukaiRenderer(context, BiomeVariantPresets.HIGHFIELDS_BURRUKAI));
        event.registerEntityRenderer(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), (context) -> new BurrukaiRenderer(context, BiomeVariantPresets.MAGNETIC_BURRUKAI));
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), (context) -> new BurrukaiRenderer(context, BiomeVariantPresets.ARCTIC_BURRUKAI));
        event.registerEntityRenderer(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), (context) -> new KirridRenderer(context, BiomeVariantPresets.HIGHFIELDS_KIRRID));
        event.registerEntityRenderer(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), (context) -> new KirridRenderer(context, BiomeVariantPresets.MAGNETIC_KIRRID));
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_KIRRID.get(), (context) -> new KirridRenderer(context, BiomeVariantPresets.ARCTIC_KIRRID));
        event.registerEntityRenderer(AetherIIEntityTypes.MOA.get(), MoaRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKYROOT_LIZARD.get(), SkyrootLizardRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.CARRION_SPROUT.get(), CarrionSproutRenderer::new);

        // Hostile
        event.registerEntityRenderer(AetherIIEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.TEMPEST.get(), TempestRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.BLUE_SWET.get(), BlueSwetRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.GOLDEN_SWET.get(), GoldenSwetRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKEPHID.get(), SkephidRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ARKENIUM_TALUTON.get(), ArkeniumTalutonRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.GRAVITITE_TALUTON.get(), GravititeTalutonRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.BLADESHROOM_HUNTER.get(), BladeshroomHunterRenderer::new);

        // NPCs
        event.registerEntityRenderer(AetherIIEntityTypes.EDWARD.get(), EdwardRenderer::new);

        // Projectiles
        event.registerEntityRenderer(AetherIIEntityTypes.HOLYSTONE_ROCK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKYROOT_PINECONE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.LASSO_LOOP.get(), LassoLoopRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SCATTERGLASS_BOLT.get(), ScatterglassBoltRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.AMBER_DART.get(), AmberDartRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.TOXIC_DART.get(), ToxicDartRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.VENOMOUS_DART.get(), VenomousDartRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR_WEBBING_BALL.get(), ZephyrWebbingBallRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.TEMPEST_THUNDERBALL.get(), TempestThunderballRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKEPHID_WEBBING_BALL.get(), SkephidWebbingBallRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.GRAVITITE_DEBRIS_SHOT.get(), GravititeDebrisShotRenderer::new);

        // Blocks
        event.registerEntityRenderer(AetherIIEntityTypes.HOVERING_BLOCK.get(), HoveringBlockRenderer::new);

        // Vehicles
        event.registerEntityRenderer(AetherIIEntityTypes.CLOUD_SKIFF.get(), CloudSkiffRenderer::new);

        // Misc
        event.registerEntityRenderer(AetherIIEntityTypes.ELECTRIC_FIELD.get(), NoopRenderer::new);
    }

    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Blocks
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_BED_FOOT, SkyrootBedRenderer::createFootLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_BED_HEAD, SkyrootBedRenderer::createHeadLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA_EGG, MoaEggModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ALKAHEST_PURIFIER, AlkahestPurifierModel::createBodyLayer);

        // Entities
        // Passive
        event.registerLayerDefinition(AetherIIModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.AERBUNNY_COLLAR, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.PHYG, PhygModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.FLYING_COW, FlyingCowModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SHEEPUFF, SheepuffModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.AERWHALE, AerwhaleModel::createBodyLayer);
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
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_LIZARD, SkyrootLizardModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.CARRION_SPROUT, CarrionSproutModel::createBodyLayer);

        // Hostile
        event.registerLayerDefinition(AetherIIModelLayers.AECHOR_PLANT, AechorPlantModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.TEMPEST, TempestModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.COCKATRICE, CockatriceModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.BLUE_SWET, SwetModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.GOLDEN_SWET, SwetModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SKEPHID, SkephidModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARKENIUM_TALUTON, ArkeniumTalutonModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.GRAVITITE_TALUTON, GravititeTalutonModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.BLADESHROOM_HUNTER, BladeshroomHunterModel::createBodyLayer);

        // Projectiles
        event.registerLayerDefinition(AetherIIModelLayers.GRAVITITE_DEBRIS_SHOT, GravititeDebrisShotModel::createBodyLayer);

        // NPCs
        event.registerLayerDefinition(AetherIIModelLayers.EDWARD, EdwardModel::createBodyLayer);

        // Vehicles
        event.registerLayerDefinition(AetherIIModelLayers.CLOUD_SKIFF, CloudSkiffModel::createLayer);

        // Accessories
        // Handwear
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), false));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), true));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_SLIM_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), true));
    }

    public static void registerItemModels(RegisterItemModelsEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "shield"), ShieldModel.Unbaked.MAP_CODEC);
    }

    public static void registerBlockStateModels(RegisterBlockStateModels event) {
        event.registerModel(TrunkModel.Unbaked.ID , TrunkModel.Unbaked.CODEC);
    }

    public static void registerBakedModels(ModelEvent.ModifyBakingResult event) {
        List<DeferredBlock<? extends Block>> fastBlocks = List.of(
                AetherIIBlocks.SKYROOT_LEAF_PILE,
                AetherIIBlocks.SKYPLANE_LEAF_PILE,
                AetherIIBlocks.SKYBIRCH_LEAF_PILE,
                AetherIIBlocks.SKYPINE_LEAF_PILE,
                AetherIIBlocks.WISPROOT_LEAF_PILE,
                AetherIIBlocks.WISPTOP_LEAF_PILE,
                AetherIIBlocks.GREATROOT_LEAF_PILE,
                AetherIIBlocks.GREATOAK_LEAF_PILE,
                AetherIIBlocks.GREATBOA_LEAF_PILE,
                AetherIIBlocks.AMBEROOT_LEAF_PILE,
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
                AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES,
                AetherIIBlocks.HIGHLANDS_BUSH,
                AetherIIBlocks.BLUEBERRY_BUSH,
                AetherIIBlocks.POTTED_HIGHLANDS_BUSH,
                AetherIIBlocks.POTTED_BLUEBERRY_BUSH);
        List<DeferredBlock<? extends Block>> aoBlocks = List.of(
                AetherIIBlocks.AMBROSIUM_ORE,
                AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE,
                AetherIIBlocks.BLOOMING_ARILUM,
                AetherIIBlocks.BLOOMING_ARILUM_PLANT,
                AetherIIBlocks.SPOTTED_MAGNETIC_SHROOM_BLOCK,
                AetherIIBlocks.LUCENT_GUARDIAN_ROOTS,
                AetherIIBlocks.GUARDIAN_LAMP);
        List<DeferredBlock<? extends Block>> breakingFixBlocks = List.of(
                AetherIIBlocks.AETHER_GRASS_BLOCK,
                AetherIIBlocks.MOA_EGG);

        getModels(event.getBakingResult().blockStateModels(), fastBlocks).forEach(entry -> event.getBakingResult().blockStateModels().put(entry.getKey(), new FastModel(entry.getValue())));
        getModels(event.getBakingResult().blockStateModels(), aoBlocks).forEach(entry -> event.getBakingResult().blockStateModels().put(entry.getKey(), new AmbientOcclusionLightModel(entry.getValue())));
        getModels(event.getBakingResult().blockStateModels(), breakingFixBlocks).forEach(entry -> event.getBakingResult().blockStateModels().put(entry.getKey(), new BreakingFixModel(entry.getValue())));
    }

    private static List<Map.Entry<BlockState, BlockStateModel>> getModels(Map<BlockState, BlockStateModel> originalModels, List<DeferredBlock<? extends Block>> blocks) {
        List<Map.Entry<BlockState, BlockStateModel>> models = new ArrayList<>();
        for (Map.Entry<BlockState, BlockStateModel> model : originalModels.entrySet()) {
            for (DeferredBlock<? extends Block> block : blocks) {
                if (model.getKey().is(block)) {
                    models.add(model);
                }
            }
        }
        return models;
    }

    public static void registerSpecialModelRenderers(RegisterSpecialModelRendererEvent event) {
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skyroot_bed"), SkyrootBedSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier"), AlkahestPurifierSpecialRenderer.Unbaked.MAP_CODEC);
    }
}