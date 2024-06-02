package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.renderer.block.FastModel;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootChestRenderer;
import com.aetherteam.aetherii.client.renderer.entity.AerbunnyRenderer;
import com.aetherteam.aetherii.client.renderer.entity.FlyingCowRenderer;
import com.aetherteam.aetherii.client.renderer.entity.PhygRenderer;
import com.aetherteam.aetherii.client.renderer.entity.ZephyrRenderer;
import com.aetherteam.aetherii.client.renderer.entity.model.AerbunnyModel;
import com.aetherteam.aetherii.client.renderer.entity.model.QuadrupedWingsModel;
import com.aetherteam.aetherii.client.renderer.entity.model.ZephyrModel;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
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
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_CHEST.get(), SkyrootChestRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.PHYG.get(), PhygRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR.get(), ZephyrRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.ZEPHYR_SNOWBALL.get(), renderer -> new ThrownItemRenderer<>(renderer, 3.0F, true));

    }

    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AetherModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.AERBUNNY_COLLAR, AerbunnyModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.PHYG, () -> PigModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(AetherModelLayers.PHYG_WINGS, () -> QuadrupedWingsModel.createMainLayer(10.0F));
        event.registerLayerDefinition(AetherModelLayers.PHYG_SADDLE, () -> PigModel.createBodyLayer(new CubeDeformation(0.5F)));
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW, CowModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW_WINGS, () -> QuadrupedWingsModel.createMainLayer(0.0F));
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR_TRANSPARENCY, ZephyrModel::createBodyLayer);
    }

    public static void bakeModels(ModelEvent.ModifyBakingResult event) {
        List<DeferredBlock<? extends Block>> fastBlocks = List.of(AetherIIBlocks.HIGHLANDS_BUSH, AetherIIBlocks.BLUEBERRY_BUSH, AetherIIBlocks.POTTED_HIGHLANDS_BUSH, AetherIIBlocks.POTTED_BLUEBERRY_BUSH);
        List<Map.Entry<ResourceLocation, BakedModel>> models = new ArrayList<>();
        for (Map.Entry<ResourceLocation, BakedModel> model : event.getModels().entrySet()) {
            if (model.getKey().getNamespace().equals(AetherII.MODID)) {
                String path = model.getKey().getPath();
                for (DeferredBlock<? extends Block> block : fastBlocks) {
                    if (path.equals(block.getId().getPath())) {
                        models.add(model);
                    }
                }
            }
        }
        models.forEach(entry -> event.getModels().put(entry.getKey(), new FastModel(entry.getValue())));
    }
}