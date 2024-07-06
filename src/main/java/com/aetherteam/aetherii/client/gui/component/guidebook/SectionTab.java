package com.aetherteam.aetherii.client.gui.component.guidebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.guidebook.GuidebookDiscoveryScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class SectionTab extends ImageButton {
    public static WidgetSprites SECTION_TAB = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/page_section_tab"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/page_section_tab_selected"));

    private final GuidebookDiscoveryScreen currentScreen;
    private final GuidebookDiscoveryScreen.DiscoverySectionTab tab;
    private final ResourceLocation icon;

    public SectionTab(GuidebookDiscoveryScreen currentScreen, GuidebookDiscoveryScreen.DiscoverySectionTab tab, int x, int y, int width, int height, ResourceLocation icon) {
        super(x, y, width, height, SECTION_TAB, (button) -> currentScreen.setCurrentSectionTab(tab));
        this.currentScreen = currentScreen;
        this.tab = tab;
        this.icon = icon;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blitSprite(this.icon, this.getX() + 10, this.getY() + 2, 22, 16);
    }

    @Override
    public boolean isFocused() {
        return this.currentScreen.getCurrentSectionTab() == this.tab;
    }
}
