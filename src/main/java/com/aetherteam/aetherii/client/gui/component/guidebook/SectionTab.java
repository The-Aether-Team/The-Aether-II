package com.aetherteam.aetherii.client.gui.component.guidebook;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class SectionTab extends ImageButton {
    public static WidgetSprites SECTION_TAB = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/page_section_tab"), ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/page_section_tab_selected"));

    private ResourceLocation icon;

    public SectionTab(int x, int y, int width, int height, ResourceLocation icon, OnPress onPress) {
        super(x, y, width, height, SECTION_TAB, onPress);
        this.icon = icon;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blitSprite(this.icon, this.getX() + 10, this.getY() + 2, 22, 16);
    }
}
