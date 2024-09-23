package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.renderer.accessory.GlovesRenderer;
import com.aetherteam.aetherii.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aetherii.client.renderer.block.model.baked.AmbientOcclusionLightModel;
import com.aetherteam.aetherii.client.renderer.block.model.baked.FastModel;
import com.aetherteam.aetherii.client.renderer.blockentity.ArkeniumForgeRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.MoaEggRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootBedRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootChestRenderer;
import com.aetherteam.aetherii.client.renderer.entity.*;
import com.aetherteam.aetherii.client.renderer.entity.layers.SwetLayer;
import com.aetherteam.aetherii.client.renderer.entity.model.*;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.ArcticBurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.burrukai.BurrukaiModel;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.*;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreBabyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.taegore.TaegoreModel;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AetherIIRenderers {
    public static void registerAddLayer(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(model -> {
            if (event.getSkin(model) instanceof LivingEntityRenderer<?, ?> livingEntityRenderer) {
                livingEntityRenderer.addLayer(new SwetLayer(event.getContext(), livingEntityRenderer));
            }
        });
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Blocks
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_CHEST.get(), SkyrootChestRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.MOA_EGG.get(), MoaEggRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.ARKENIUM_FORGE.get(), ArkeniumForgeRenderer::new);

        // Entities
        // Passive
        event.registerEntityRenderer(AetherIIEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.PHYG.get(), PhygRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
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

        // Hostile
        event.registerEntityRenderer(AetherIIEntityTypes.AECHOR_PLANT.get(), AechorPlantRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.TEMPEST.get(), TempestRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.COCKATRICE.get(), CockatriceRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SWET.get(), SwetRenderer::new);

        // NPCs
        event.registerEntityRenderer(AetherIIEntityTypes.EDWARD.get(), EdwardRenderer::new);

        // Projectiles
        event.registerEntityRenderer(AetherIIEntityTypes.HOLYSTONE_ROCK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKYROOT_PINECONE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SCATTERGLASS_BOLT.get(), ScatterglassBoltRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.TOXIC_DART.get(), ToxicDartRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.VENOMOUS_DART.get(), VenomousDartRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR_WEBBING_BALL.get(), ZephyrWebbingBallRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.TEMPEST_THUNDERBALL.get(), TempestThunderballRenderer::new);

        // Blocks
        event.registerEntityRenderer(AetherIIEntityTypes.HOVERING_BLOCK.get(), HoveringBlockRenderer::new);

        // Misc
        event.registerEntityRenderer(AetherIIEntityTypes.ELECTRIC_FIELD.get(), NoopRenderer::new);
    }

    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Blocks
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_BED_FOOT, BedRenderer::createFootLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_BED_HEAD, BedRenderer::createHeadLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA_EGG, MoaEggModel::createBodyLayer);

        // Entities
        // Passive
        event.registerLayerDefinition(AetherIIModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.AERBUNNY_COLLAR, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.PHYG, PhygModel::createBodyLayer);
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
        event.registerLayerDefinition(AetherIIModelLayers.SKYROOT_LIZARD, SkyrootLizardModel::createBodyLayer);

        // Hostile
        event.registerLayerDefinition(AetherIIModelLayers.AECHOR_PLANT, AechorPlantModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.TEMPEST, TempestModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.COCKATRICE, CockatriceModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.SWET, SwetModel::createBodyLayer);

        // NPCs
        event.registerLayerDefinition(AetherIIModelLayers.EDWARD, EdwardModel::createBodyLayer);

        // Accessories
        // Handwear
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), false));
        event.registerLayerDefinition(AetherIIModelLayers.GLOVES_SLIM, () -> GlovesModel.createLayer(new CubeDeformation(0.6F), true));
    }

    public static void registerAccessoryRenderers() {
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.TAEGORE_HIDE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.BURRUKAI_PELT_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.ZANITE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.ARKENIUM_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.GRAVITITE_GLOVES.get(), GlovesRenderer::new);
    }

    public static void registerBakedModels(ModelEvent.ModifyBakingResult event) {
        List<DeferredBlock<? extends Block>> fastBlocks = List.of(AetherIIBlocks.HIGHLANDS_BUSH, AetherIIBlocks.BLUEBERRY_BUSH, AetherIIBlocks.POTTED_HIGHLANDS_BUSH, AetherIIBlocks.POTTED_BLUEBERRY_BUSH);
        List<DeferredBlock<? extends Block>> aoBlocks = List.of(AetherIIBlocks.AMBROSIUM_ORE, AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE);

        getModels(event.getModels(), fastBlocks).forEach(entry -> event.getModels().put(entry.getKey(), new FastModel(entry.getValue())));
        getModels(event.getModels(), aoBlocks).forEach(entry -> event.getModels().put(entry.getKey(), new AmbientOcclusionLightModel(entry.getValue())));
    }

    private static List<Map.Entry<ModelResourceLocation, BakedModel>> getModels(Map<ModelResourceLocation, BakedModel> originalModels, List<DeferredBlock<? extends Block>> blocks) {
        List<Map.Entry<ModelResourceLocation, BakedModel>> models = new ArrayList<>();
        for (Map.Entry<ModelResourceLocation, BakedModel> model : originalModels.entrySet()) {
            if (model.getKey().id().getNamespace().equals(AetherII.MODID)) {
                String path = model.getKey().id().getPath();
                for (DeferredBlock<? extends Block> block : blocks) {
                    if (path.equals(block.getId().getPath())) {
                        models.add(model);
                    }
                }
            }
        }
        return models;
    }
}