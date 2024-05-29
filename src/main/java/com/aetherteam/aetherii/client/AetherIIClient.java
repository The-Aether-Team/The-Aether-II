package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.client.event.listeners.AerbunnyMountClientListners;
import com.aetherteam.aetherii.client.gui.screen.inventory.HolystoneFurnaceScreen;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.AerbunnyRenderer;
import com.aetherteam.aetherii.client.renderer.AetherModelLayers;
import com.aetherteam.aetherii.client.renderer.model.AerbunnyModel;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;

public class AetherIIClient {
    public static void clientInit(IEventBus bus) {
        bus.addListener(AetherIIClient::clientSetup);
        bus.addListener(AetherIIClient::registerEntityRenders);
        bus.addListener(AetherIIClient::registerLayerDefinition);

        AetherIIClient.eventSetup(bus);
    }

    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AetherIIEntityTypes.AERBUNNY.get(), AerbunnyRenderer::new);
    }

    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AetherModelLayers.AERBUNNY, AerbunnyModel::createBodyLayer);
        event.registerLayerDefinition(AetherModelLayers.AERBUNNY_COLLAR, AerbunnyModel::createBodyLayer);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            registerGuiFactories();
            AetherIIAtlases.registerSkyrootChestAtlases();
        });
    }

    public static void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        AerbunnyMountClientListners.listen(bus);

        neoBus.addListener(AetherIIColorResolvers::registerBlockColor);
        neoBus.addListener(AetherIIColorResolvers::registerItemColor);
        neoBus.addListener(AetherIIParticleTypes::registerParticleFactories);
        neoBus.addListener(AetherIIRenderers::registerEntityRenderers);
    }

    @SuppressWarnings("deprecation")
    public static void registerGuiFactories() {
        MenuScreens.register(AetherIIMenuTypes.HOLYSTONE_FURNACE.get(), HolystoneFurnaceScreen::new);
    }
}