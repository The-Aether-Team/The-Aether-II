package com.aetherteam.aetherii.client.gui.component.guidebook;

import com.aetherteam.aetherii.client.gui.screen.guidebook.DiscoveryDescriptionScreen;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.aetherteam.aetherii.client.gui.component.AetherIIImageButton;
import net.minecraft.client.gui.components.Tooltip;
import com.aetherteam.aetherii.client.gui.component.AetherIIWidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DescriptionButton extends AetherIIImageButton {
    private final Screen currentScreen;

    public DescriptionButton(Screen currentScreen, int x, int y, AetherIIWidgetSprites sprites) {
        super(x, y, 8, 8, sprites, (button) -> {
            if (currentScreen instanceof GuidebookDiscoveryScreen screen && screen.getCurrentSection().getSelectedEntry() != null) {
                Minecraft.getInstance().setScreen(new DiscoveryDescriptionScreen(screen, screen.getCurrentSection().getSelectedEntry().getEntry().value()));
            } else if (currentScreen instanceof DiscoveryDescriptionScreen screen) {
                Minecraft.getInstance().setScreen(screen.getLastScreen());
            }
        });
        if (currentScreen instanceof GuidebookDiscoveryScreen screen && screen.getCurrentSection().getSelectedEntry() != null) {
            this.setTooltip(Tooltip.create(Component.translatable("gui.aether_ii.guidebook.description.button.open")));
        } else if (currentScreen instanceof DiscoveryDescriptionScreen screen) {
            this.setTooltip(Tooltip.create(Component.translatable("gui.aether_ii.guidebook.description.button.close")));
        }
        this.currentScreen = currentScreen;
    }


    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        if (!(this.currentScreen instanceof GuidebookDiscoveryScreen screen) || screen.getCurrentSection().getSelectedEntry() != null) {
            super.renderWidget(graphics, mouseX, mouseY, a);
        }
    }
}


