package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootChestRenderer;
import com.aetherteam.aetherii.client.renderer.model.*;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class AetherIIRenderers {

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_CHEST.get(), SkyrootChestRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.PHYG.get(), PhygRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.FLYING_COW.get(), FlyingCowRenderer::new);

        event.registerEntityRenderer(AetherIIEntityTypes.KIRRID.get(), KirridRenderer::new);

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

        event.registerLayerDefinition(AetherModelLayers.KIRRID, KirridModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.KIRRID_BABY, KirridBabyModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.KIRRID_WOOL, KirridBabyModel::createBodyLayer);

        event.registerLayerDefinition(AetherModelLayers.ZEPHYR, ZephyrModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.ZEPHYR_TRANSPARENCY, ZephyrModel::createBodyLayer);
    }
}