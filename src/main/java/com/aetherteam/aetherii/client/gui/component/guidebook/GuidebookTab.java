package com.aetherteam.aetherii.client.gui.component.guidebook;

import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;

public class GuidebookTab extends ImageButton {
    private final Screen currentScreen;
    private final Screen screenToOpen;

    public GuidebookTab(Screen currentScreen, Screen screenToOpen, int x, int y, int width, int height, WidgetSprites sprites) {
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
