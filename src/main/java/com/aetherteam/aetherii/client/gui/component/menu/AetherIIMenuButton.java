package com.aetherteam.aetherii.client.gui.component.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.menu.AetherIITitleScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AetherIIMenuButton extends Button {
    private static final ResourceLocation AETHER_WIDGET = new ResourceLocation(AetherII.MODID, "textures/gui/sprites/title/button.png");
    private static final ResourceLocation AETHER_WIDGET_HIGHLIGHTED = new ResourceLocation(AetherII.MODID, "textures/gui/sprites/title/button_highlighted.png");
    private static final ResourceLocation MAKESHIP_WIDGET = new ResourceLocation(AetherII.MODID, "textures/gui/sprites/title/makeship_button.png");
    private static final ResourceLocation MAKESHIP_WIDGET_HIGHLIGHTED = new ResourceLocation(AetherII.MODID, "textures/gui/sprites/title/makeship_button_highlighted.png");

    public int hoverOffset;
    public int buttonCountOffset;
    public boolean serverButton;
    public boolean makeshipButton;

    public AetherIIMenuButton(AetherIITitleScreen screen, Button oldButton) {
        super(oldButton.getX(), oldButton.getY(), oldButton.getWidth(), oldButton.getHeight(), oldButton.getMessage(), oldButton.onPress, oldButton.createNarration);
        this.active = oldButton.active;
        this.visible = oldButton.visible;
        this.setTooltip(oldButton.getTooltip());
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        ResourceLocation location = this.getTexture();
        int alpha = Mth.ceil(this.alpha * 255.0F) << 24;

        RenderSystem.enableBlend();
        if (!this.makeshipButton) {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            guiGraphics.blit(location, this.getX() + this.hoverOffset, this.getY(), 200, 20, 0.0F, 0.0F, 1200, 120, 1200, 120);
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            guiGraphics.drawString(font, this.getMessage(), this.getX() + 35 + this.hoverOffset, this.getY() + (this.height - 8) / 2, this.getTextColor(mouseX, mouseY) | alpha);
        } else {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            guiGraphics.blit(location, this.getX(), this.getY(), 900 / 7, 524 / 7, 0.0F, 0.0F, 900, 524, 900, 524);
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private ResourceLocation getTexture() {
        if (this.makeshipButton) {
            return this.isActive() && this.isHoveredOrFocused() ? MAKESHIP_WIDGET_HIGHLIGHTED : MAKESHIP_WIDGET;
        }
        return this.isActive() && this.isHoveredOrFocused() ? AETHER_WIDGET_HIGHLIGHTED : AETHER_WIDGET;
    }

    public int getTextColor(int mouseX, int mouseY) {
        if (!this.serverButton) {
            return this.isMouseOver(mouseX, mouseY) ? 0xADDFFF : 0xD4D7D4;
        }
        return this.isMouseOver(mouseX, mouseY) ? 0x434328 : 0xEBDBD9;
    }
}


