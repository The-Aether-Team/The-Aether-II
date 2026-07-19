package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIIFluids;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.renderer.accessory.AccessoryLayer;
import com.aetherteam.aetherii.client.renderer.accessory.GlovesLayer;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.client.renderer.block.model.blockstate.*;
import com.aetherteam.aetherii.client.renderer.blockentity.*;
import com.aetherteam.aetherii.client.renderer.blockentity.model.*;
import com.aetherteam.aetherii.client.renderer.entity.*;
import com.aetherteam.aetherii.client.renderer.entity.layers.ProjectilesStuckLayer;
import com.aetherteam.aetherii.client.renderer.entity.layers.SwetLatchLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.*;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.ArcticBurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.*;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SwetRenderState;
import com.aetherteam.aetherii.client.renderer.item.model.*;
import com.aetherteam.aetherii.client.renderer.level.DungeonBlockOverlayRenderer;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.google.common.reflect.TypeToken;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AetherIIRenderers {
    public static final ContextKey<Boolean> RIDING_SKIFF_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "riding_skiff"));
    public static final ContextKey<Float> SKIFF_STEERING_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "skiff_steering"));
    public static final ContextKey<Boolean> RIDING_MOA_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "riding_moa"));
    public static final ContextKey<Boolean> HAS_AERBUNNY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "has_aerbunny"));
    public static final ContextKey<List<SwetRenderState>> SWET_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "swet"));
    public static final ContextKey<List<EntityType<?>>> STUCK_PROJECTILES_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "stuck_projectiles"));
    public static final ContextKey<ItemStack> HANDWEAR_EQUIPMENT_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "handwear_equipment"));
    public static final ContextKey<ItemStack> ACCESSORY_EQUIPMENT_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "accessory_equipment"));

    public static void registerAddLayer(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(model -> {
            if (event.getPlayerRenderer(model) instanceof LivingEntityRenderer livingEntityRenderer) {
                registerLivingEntityLayers(event.getContext(), livingEntityRenderer);
                livingEntityRenderer.addLayer(new AccessoryLayer(livingEntityRenderer));
                AvatarRenderer playerRenderer = (AvatarRenderer) livingEntityRenderer;
                playerRenderer.addLayer(new ProjectilesStuckLayer<>(playerRenderer, event.getContext()));
            }
            if (event.getMannequinRenderer(model) instanceof LivingEntityRenderer livingEntityRenderer) {
                registerLivingEntityLayers(event.getContext(), livingEntityRenderer);
                livingEntityRenderer.addLayer(new AccessoryLayer(livingEntityRenderer));
                AvatarRenderer playerRenderer = (AvatarRenderer) livingEntityRenderer;
                playerRenderer.addLayer(new ProjectilesStuckLayer<>(playerRenderer, event.getContext()));
            }
        });
    }

    private static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> void registerLivingEntityLayers(EntityRendererProvider.Context context, LivingEntityRenderer<T, S, M> livingEntityRenderer) {
        livingEntityRenderer.addLayer(new SwetLatchLayer<>(livingEntityRenderer));
        livingEntityRenderer.addLayer(new GlovesLayer<>(livingEntityRenderer));
    }

    public static void registerRenderStateModifier(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(new TypeToken<AvatarRenderer<?>>(AvatarRenderer.class) {
        }, (avatar, avatarRenderState) -> {
            List<Swet> swets = avatar.getData(AetherIIDataAttachments.SWET_LATCH).getLatchedSwets();
            if (swets != null) {
                List<SwetRenderState> states = new ArrayList<>();
                for (Swet swet : swets) {
                    SwetRenderState state = new SwetRenderState();
                    state.entityType = swet.getType();
                    state.swetScale = swet.getSwetScale();
                    states.add(state);
                }
                avatarRenderState.setRenderData(SWET_KEY, states);
            }
            avatarRenderState.setRenderData(RIDING_MOA_KEY, avatar.getVehicle() instanceof Moa);
            if (avatar.getVehicle() instanceof CloudSkiff cloudSkiff) {
                avatarRenderState.setRenderData(RIDING_SKIFF_KEY, true);
                avatarRenderState.setRenderData(SKIFF_STEERING_KEY, cloudSkiff.steering);
            }
            avatarRenderState.setRenderData(STUCK_PROJECTILES_KEY, avatar.getData(AetherIIDataAttachments.PLAYER).getStuckProjectiles());
            avatarRenderState.setRenderData(HAS_AERBUNNY, avatar.getFirstPassenger() instanceof Aerbunny);
            avatarRenderState.setRenderData(HANDWEAR_EQUIPMENT_KEY, AccessoryUtil.getFirst(avatar, AccessoryContainer.SlotType.HANDWEAR).orElse(ItemStack.EMPTY));
            avatarRenderState.setRenderData(ACCESSORY_EQUIPMENT_KEY, AccessoryUtil.getFirst(avatar, AccessoryContainer.SlotType.ACCESSORY).orElse(ItemStack.EMPTY));
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
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.AMBROSIUM_CAMPFIRE.get(), AmbrosiumCampfireRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.VASE.get(), VaseRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SENTRY_CRATE.get(), SentryCrateRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SENTRY_SPAWNER.get(), SentrySpawnerRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.ABANDONED_BAG.get(), AbandonedBagRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.FUNGAL_CACHE.get(), FungalCacheRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SAGE_CHEST.get(), SageChestRenderer::new);


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
        event.registerEntityRenderer(AetherIIEntityTypes.PRISMALLARD.get(), PrismallardRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKYROOT_LIZARD.get(), SkyrootLizardRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.CARRION_SPROUT.get(), CarrionSproutRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.GLITTERWING.get(), GlitterwingRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SHROUDWING.get(), ShroudwingRenderer::new);

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
        event.registerEntityRenderer(AetherIIEntityTypes.MIMIC.get(), MimicRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.DETONATION_SENTRY.get(), DetonationSentryRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SENTRY_GOLEM.get(), SentryGolemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SLIDER.get(), SliderRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.BLADESHROOM_HUNTER.get(), BladeshroomHunterRenderer::new);

        // NPCs
        event.registerEntityRenderer(AetherIIEntityTypes.EDWARD.get(), EdwardRenderer::new);

        // Projectiles
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
        event.registerEntityRenderer(AetherIIEntityTypes.TEMPEST_THUNDERBALL.get(), TempestThunderballRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKEPHID_WEBBING_BALL.get(), SkephidWebbingBallRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.GRAVITITE_DEBRIS_SHOT.get(), GravititeDebrisShotRenderer::new);

        event.registerEntityRenderer(AetherIIEntityTypes.DEMOLITION_PROJECTILE.get(), DemolitionProjectileRenderer::new);

        // Blocks
        event.registerEntityRenderer(AetherIIEntityTypes.SITTABLE.get(), NoopRenderer::new);
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
        event.registerLayerDefinition(AetherIIModelLayers.VASE, VaseModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SENTRY_SPAWNER, SentrySpawnerModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SENTRY_SPAWNER_PISTON, SentrySpawnerPistonModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SENTRY_CRATE, SentryCrateModel::createSingleBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_RIGHT, SentryCrateModel::createDoubleBodyRightLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DOUBLE_SENTRY_CRATE_LEFT, SentryCrateModel::createDoubleBodyLeftLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ABANDONED_BAG, AbandonedBagModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.FUNGAL_CACHE, FungalCacheModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SAGE_CHEST, SageChestModel::createSingleBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DOUBLE_SAGE_CHEST_RIGHT, SageChestModel::createDoubleBodyRightLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DOUBLE_SAGE_CHEST_LEFT, SageChestModel::createDoubleBodyLeftLayer);

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
        event.registerLayerDefinition(AetherIIModelLayers.PRISMALLARD, PrismallardModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_LIZARD, SkyrootLizardModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.GLITTERWING, GlitterwingModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SHROUDWING, ShroudwingModel::createBodyLayer);
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
        event.registerLayerDefinition(AetherIIModelLayers.DETONATION_SENTRY, DetonationSentryModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MIMIC, MimicModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SENTRY_GOLEM, SentryGolemModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SLIDER, SliderModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.BLADESHROOM_HUNTER, BladeshroomHunterModel::createBodyLayer);

        // Projectiles
        event.registerLayerDefinition(AetherIIModelLayers.GRAVITITE_DEBRIS_SHOT, GravititeDebrisShotModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.DEMOLITION_PROJECTILE, DemolitionProjectileModel::createBodyLayer);
        // NPCs
        event.registerLayerDefinition(AetherIIModelLayers.EDWARD, EdwardModel::createBodyLayer);

        // Vehicles
        event.registerLayerDefinition(AetherIIModelLayers.CLOUD_SKIFF, CloudSkiffModel::createLayer);

        // Accessories
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), false));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), true));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), false));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_SLIM_FIRST_PERSON, () -> GlovesModel.createLayer(new CubeDeformation(0.25F), true));

        event.registerLayerDefinition(AetherIIModelLayers.ACCESSORY, () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.0F), 64, 32));
    }

    public static void registerItemModels(RegisterItemModelsEvent event) {
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "shield"), ShieldModel.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "music_player_disc"), MusicPlayerDiscModel.Unbaked.MAP_CODEC);
//        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "mural"), MuralItemModel.Unbaked.MAP_CODEC);
    }

    public static void registerBlockStateModels(RegisterBlockStateModels event) {
        event.registerModel(TrunkModel.Unbaked.ID, TrunkModel.Unbaked.CODEC);
    }

    public static void registerFluidModels(RegisterFluidModelsEvent event) {
        FluidModel.Unbaked alkahestModel = new FluidModel.Unbaked(
                new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "fluid/alkahest_still")),
                new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "fluid/alkahest_flow")),
                new Material(Identifier.fromNamespaceAndPath(AetherII.MODID, "fluid/alkahest_overlay")),
                null
        );
        event.register(alkahestModel, AetherIIFluids.ALKAHEST);
        event.register(alkahestModel, AetherIIFluids.FLOWING_ALKAHEST);
    }

    public static void registerBakedModels(ModelEvent.ModifyBakingResult event) {
        List<DeferredBlock<? extends Block>> overlaidLeafBlocks = List.of(
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
        List<DeferredBlock<? extends Block>> aoBlocks = List.of(
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
        List<DeferredBlock<? extends Block>> breakingFixBlocks = List.of(
                AetherIIBlocks.AETHER_GRASS_BLOCK);
        List<DeferredBlock<? extends Block>> copyBlocks = List.of(
                AetherIIBlocks.LOCKED_BLOCK,
                AetherIIBlocks.BOSS_DOORWAY_BLOCK,
                AetherIIBlocks.TREASURE_DOORWAY_BLOCK);

        getModels(event.getBakingResult().blockStateModels(), overlaidLeafBlocks).forEach(entry -> event.getBakingResult().blockStateModels().put(entry.getKey(), new OverlaidLeavesModel(entry.getValue())));
        getModels(event.getBakingResult().blockStateModels(), aoBlocks).forEach(entry -> event.getBakingResult().blockStateModels().put(entry.getKey(), new AmbientOcclusionLightModel(entry.getValue())));
        getModels(event.getBakingResult().blockStateModels(), breakingFixBlocks).forEach(entry -> event.getBakingResult().blockStateModels().put(entry.getKey(), new BreakingFixModel(entry.getValue())));
//        getModels(event.getBakingResult().blockStateModels(), List.of(AetherIIBlocks.MURAL)).forEach(entry -> event.getBakingResult().blockStateModels().put(entry.getKey(), new MuralModel(entry.getValue()))); //todo
        getModels(event.getBakingResult().blockStateModels(), copyBlocks).forEach(entry -> event.getBakingResult().blockStateModels().put(entry.getKey(), new CopyBlockModel(entry.getValue())));
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
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot_bed"), SkyrootBedSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_purifier"), AlkahestPurifierSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "vase"), VaseSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "sentry_crate"), SentryCrateSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "sentry_spawner"), SentrySpawnerSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "abandoned_bag"), AbandonedBagSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "fungal_cache"), FungalCacheSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "sage_chest"), SageChestSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(AetherII.MODID, "copy_block"), CopyBlockSpecialRenderer.Unbaked.MAP_CODEC);
    }

    public static void submitCustomGeometryRendering(SubmitCustomGeometryEvent event) {
        LevelRenderState levelRenderState = event.getLevelRenderState();
        PoseStack poseStack = event.getPoseStack();
        SubmitNodeCollector submitNodeCollector = event.getSubmitNodeCollector();
        CameraRenderState cameraRenderState = levelRenderState.cameraRenderState;

        DungeonBlockOverlayRenderer.submitDungeonBlockOverlays(poseStack, submitNodeCollector, cameraRenderState.pos, cameraRenderState.cullFrustum, Minecraft.getInstance());
    }

    public static boolean isFastBlock(BlockState state) {
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
}