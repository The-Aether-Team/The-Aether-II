package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.client.event.listeners.AerbunnyMountClientListners;
import com.aetherteam.aetherii.client.gui.screen.inventory.AltarScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.ArtisansBenchScreen;
import com.aetherteam.aetherii.client.gui.screen.inventory.HolystoneFurnaceScreen;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.AetherIIOverlays;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.client.renderer.level.AetherIIRenderEffects;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

public class AetherIIClient {
    public static void clientInit(IEventBus bus) {
        bus.addListener(AetherIIClient::clientSetup);

        AetherIIClient.eventSetup(bus);
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
        neoBus.addListener(AetherIIRecipeCategories::registerRecipeCategories);
        neoBus.addListener(AetherIIParticleTypes::registerParticleFactories);
        neoBus.addListener(AetherIIOverlays::registerOverlays);
        neoBus.addListener(AetherIIRenderers::registerEntityRenderers);
        neoBus.addListener(AetherIIRenderers::registerLayerDefinition);
        neoBus.addListener(AetherIIRenderers::bakeModels);
        neoBus.addListener(AetherIIRenderEffects::registerRenderEffects);
    }

    @SuppressWarnings("deprecation")
    public static void registerGuiFactories() {
        MenuScreens.register(AetherIIMenuTypes.HOLYSTONE_FURNACE.get(), HolystoneFurnaceScreen::new);
        MenuScreens.register(AetherIIMenuTypes.ALTAR.get(), AltarScreen::new);
        MenuScreens.register(AetherIIMenuTypes.ARTISANS_BENCH.get(), ArtisansBenchScreen::new);
    }
}