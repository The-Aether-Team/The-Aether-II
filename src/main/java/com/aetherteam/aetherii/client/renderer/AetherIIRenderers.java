package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.client.renderer.blockentity.SkyrootChestRenderer;
import com.aetherteam.aetherii.client.renderer.model.AerbunnyModel;
import com.aetherteam.aetherii.client.renderer.model.QuadrupedWingsModel;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.client.model.CowModel;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class AetherIIRenderers {

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AetherIIBlockEntityTypes.SKYROOT_CHEST.get(), SkyrootChestRenderer::new);
        event.registerEntityRenderer(AetherIIEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
    }

    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AetherModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.AERBUNNY_COLLAR, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW, CowModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.FLYING_COW_WINGS, () -> QuadrupedWingsModel.createMainLayer(0.0F));
    }
}