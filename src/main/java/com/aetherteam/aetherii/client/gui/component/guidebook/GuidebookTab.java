package com.aetherteam.aetherii.client.gui.component.guidebook;

import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import net.minecraft.client.Minecraft;
import com.aetherteam.aetherii.client.gui.component.AetherIIImageButton;
import com.aetherteam.aetherii.client.gui.component.AetherIIWidgetSprites;
import net.minecraft.client.gui.screens.Screen;

public class GuidebookTab extends AetherIIImageButton {
    private final Screen currentScreen;
    private final Screen screenToOpen;

    public GuidebookTab(Screen currentScreen, Screen screenToOpen, int x, int y, int width, int height, AetherIIWidgetSprites sprites) {
        super(x, y, width, height, sprites, (button) -> {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.containerMenu.getCarried().isEmpty()) {
                if (currentScreen instanceof Guidebook guidebook) {
                    guidebook.switchTab();
                }
                Minecraft.getInstance().setScreen(screenToOpen);
            }
        });
        this.currentScreen = currentScreen;
        this.screenToOpen = screenToOpen;
    }

    @Override
    public boolean isFocused() {
        return this.currentScreen.getClass() == this.screenToOpen.getClass();
    }
}


