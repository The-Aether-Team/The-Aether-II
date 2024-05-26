package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.client.gui.screen.inventory.HolystoneFurnaceScreen;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.inventory.menu.AetherIIMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class AetherIIClient {
    public static void clientInit(IEventBus bus) {
        bus.addListener(AetherIIClient::clientSetup);

        AetherIIClient.eventSetup(bus);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            registerGuiFactories();
        });
    }

    public static void eventSetup(IEventBus neoBus) {
        neoBus.addListener(AetherIIColorResolvers::registerBlockColor);
        neoBus.addListener(AetherIIColorResolvers::registerItemColor);
        neoBus.addListener(AetherIIParticleTypes::registerParticleFactories);
    }

    @SuppressWarnings("deprecation")
    public static void registerGuiFactories() {
        MenuScreens.register(AetherIIMenuTypes.HOLYSTONE_FURNACE.get(), HolystoneFurnaceScreen::new);
    }
}