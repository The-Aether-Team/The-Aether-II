package com.aetherteam.aetherii.accessories.accessory;

import com.aetherteam.aetherii.client.renderer.accessory.GlovesRenderer;
import com.aetherteam.aetherii.item.AetherIIItems;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;

public class HandwearAccessory implements Accessory {
    public static void clientInit() {
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.TAEGORE_HIDE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.BURRUKAI_PELT_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.ZANITE_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.ARKENIUM_GLOVES.get(), GlovesRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(AetherIIItems.GRAVITITE_GLOVES.get(), GlovesRenderer::new);
    }

    public static void init() {
        AccessoriesAPI.registerAccessory(AetherIIItems.TAEGORE_HIDE_GLOVES.get(), new HandwearAccessory());
        AccessoriesAPI.registerAccessory(AetherIIItems.BURRUKAI_PELT_GLOVES.get(), new HandwearAccessory());
        AccessoriesAPI.registerAccessory(AetherIIItems.ZANITE_GLOVES.get(), new HandwearAccessory());
        AccessoriesAPI.registerAccessory(AetherIIItems.ARKENIUM_GLOVES.get(), new HandwearAccessory());
        AccessoriesAPI.registerAccessory(AetherIIItems.GRAVITITE_GLOVES.get(), new HandwearAccessory());
    }
}
