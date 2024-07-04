package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.hooks.GuiExtensionHooks;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class GuiExtensionListener {
    public static void listen(IEventBus bus) {
        bus.addListener(GuiExtensionListener::onGuiOpen);
        bus.addListener(GuiExtensionListener::onGuiInitialize);
        bus.addListener(GuiExtensionListener::onGuiClose);
    }

    public static void onGuiOpen(ScreenEvent.Opening event) {
        Screen screen = event.getScreen();
        Screen storedScreen = GuiExtensionHooks.openStoredGuidebookScreen(screen);
        if (storedScreen != null) {
            event.setNewScreen(storedScreen);
        }
    }

    public static void onGuiInitialize(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        Button inventoryAccessoryButton = GuiExtensionHooks.setupAccessoryButton(screen);
        if (inventoryAccessoryButton != null) {
            event.addListener(inventoryAccessoryButton);
        }
    }

    public static void onGuiClose(ScreenEvent.Closing event) {
        Screen screen = event.getScreen();
        GuiExtensionHooks.storeGuidebookScreen(screen);
    }
}
