package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.renderer.block.AmbientOcclusionLightModel;
import com.aetherteam.aetherii.client.renderer.block.FastModel;
import com.aetherteam.aetherii.client.renderer.blockentity.MoaEggRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootBedRenderer;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootChestRenderer;
import com.aetherteam.aetherii.client.renderer.entity.*;
import com.aetherteam.aetherii.client.renderer.entity.model.*;
import com.aetherteam.aetherii.client.renderer.entity.model.kirrid.*;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AetherIIRenderers {
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Blocks
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_CHEST.get(), SkyrootChestRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_BED.get(), SkyrootBedRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.AETHER_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.AETHER_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.MOA_EGG.get(), MoaEggRenderer::new);

        // Entities
        // Passive
        event.registerEntityRenderer(AetherIIEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.PHYG.get(), PhygRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SHEEPUFF.get(), SheepuffRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), (context) -> new KirridRenderer(context, BiomeVariantPresets.HIGHFIELDS_KIRRID));
        event.registerEntityRenderer(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), (context) -> new KirridRenderer(context, BiomeVariantPresets.MAGNETIC_KIRRID));
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_KIRRID.get(), (context) -> new KirridRenderer(context, BiomeVariantPresets.ARCTIC_KIRRID));
        event.registerEntityRenderer(AetherIIEntityTypes.MOA.get(), MoaRenderer::new);

        // Hostile
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);

        // Projectiles
        event.registerEntityRenderer(AetherIIEntityTypes.HOLYSTONE_ROCK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ARCTIC_SNOWBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SKYROOT_PINECONE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.SCATTERGLASS_BOLT.get(), ScatterglassBoltRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR_SNOWBALL.get(), renderer -> new ThrownItemRenderer<>(renderer, 3.0F, true));
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
        event.registerLayerDefinition(AetherIIModelLayers.HIGHFIELDS_KIRRID, HighfieldsKirridModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.HIGHFIELDS_KIRRID_BABY, HighfieldsKirridBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MAGNETIC_KIRRID, MagneticKirridModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MAGNETIC_KIRRID_BABY, MagneticKirridBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARCTIC_KIRRID, ArcticKirridModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ARCTIC_KIRRID_BABY, ArcticKirridBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA, MoaModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.MOA_BABY, MoaBabyModel::createBodyLayer);

        // Hostile
        event.registerLayerDefinition(AetherIIModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherIIModelLayers.ZEPHYR_TRANSPARENCY, ZephyrModel::createBodyLayer);
    }

    public static void bakeModels(ModelEvent.ModifyBakingResult event) {
        List<DeferredBlock<? extends Block>> fastBlocks = List.of(AetherIIBlocks.HIGHLANDS_BUSH, AetherIIBlocks.BLUEBERRY_BUSH, AetherIIBlocks.POTTED_HIGHLANDS_BUSH, AetherIIBlocks.POTTED_BLUEBERRY_BUSH);
        List<DeferredBlock<? extends Block>> aoBlocks = List.of(AetherIIBlocks.AMBROSIUM_ORE, AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE);

        getModels(event.getModels(), fastBlocks).forEach(entry -> event.getModels().put(entry.getKey(), new FastModel(entry.getValue())));
        getModels(event.getModels(), aoBlocks).forEach(entry -> event.getModels().put(entry.getKey(), new AmbientOcclusionLightModel(entry.getValue())));
    }

    private static List<Map.Entry<ResourceLocation, BakedModel>> getModels(Map<ResourceLocation, BakedModel> originalModels, List<DeferredBlock<? extends Block>> blocks) {
        List<Map.Entry<ResourceLocation, BakedModel>> models = new ArrayList<>();
        for (Map.Entry<ResourceLocation, BakedModel> model : originalModels.entrySet()) {
            if (model.getKey().getNamespace().equals(AetherII.MODID)) {
                String path = model.getKey().getPath();
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