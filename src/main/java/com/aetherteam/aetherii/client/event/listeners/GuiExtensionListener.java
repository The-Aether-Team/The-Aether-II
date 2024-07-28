package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.hooks.GuiExtensionHooks;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookEquipmentScreen;
import com.aetherteam.aetherii.network.packet.serverbound.OpenGuidebookPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;

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
            if (Minecraft.getInstance().player != null && (Minecraft.getInstance().player.portalProcess == null || !Minecraft.getInstance().player.portalProcess.isInsidePortalThisTick())) {
                event.setNewScreen(storedScreen);
                if (storedScreen instanceof GuidebookEquipmentScreen) {
                    PacketDistributor.sendToServer(new OpenGuidebookPacket(ItemStack.EMPTY));
                }
            }
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
