package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.hooks.GuiExtensionHooks;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class GuiExtensionListener {
    public static void listen(IEventBus bus) {
        bus.addListener(GuiExtensionListener::onGuiInitialize);
    }

    public static void onGuiInitialize(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        Button inventoryAccessoryButton = GuiExtensionHooks.setupAccessoryButton(screen);
        if (inventoryAccessoryButton != null) {
            event.addListener(inventoryAccessoryButton);
        }
    }
}
