package com.aetherteam.aetherii.client.gui.screen.guidebook;

import com.aetherteam.aetherii.api.guidebook.GuidebookEntry;
import com.aetherteam.aetherii.client.gui.component.guidebook.DescriptionButton;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DiscoveryDescriptionScreen extends Screen {
    protected final Screen lastScreen;
    protected final GuidebookEntry entry;

    public DiscoveryDescriptionScreen(Screen lastScreen, GuidebookEntry entry) {
        super(Component.translatable(entry.getName()));
        this.lastScreen = lastScreen;
        this.entry = entry;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new DescriptionButton(this, 10, 10, Guidebook.MAGNIFYING_GLASS));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, Component.translatable(this.entry.getName()).withStyle(ChatFormatting.UNDERLINE), this.width / 2, 10, 0xffffffff);
        guiGraphics.drawWordWrap(this.font, Component.translatable(this.entry.getDescriptionKey()), 85, 30, this.width - (85 * 2), 0xffffffff);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public Screen getLastScreen() {
        return this.lastScreen;
    }
}
