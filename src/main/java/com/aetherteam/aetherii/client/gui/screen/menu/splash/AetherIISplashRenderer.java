package com.aetherteam.aetherii.client.gui.screen.menu.splash;

import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.util.Mth;

public class AetherIISplashRenderer extends SplashRenderer {
    private final boolean alignedLeft;
    private final String splash;

    public AetherIISplashRenderer(boolean alignedLeft, String splash) {
        super(splash);
        this.alignedLeft = alignedLeft;
        this.splash = splash;
    }

    public void render(GuiGraphics guiGraphics, int screenWidth, Font font, int color) {
        guiGraphics.pose().pushMatrix();
        float splashX = this.alignedLeft ? 205.0F : (screenWidth / 2.0F) + (165.0F / 2.0F);
        float splashY = this.alignedLeft ? 57.0F : 68.0F;
        guiGraphics.pose().translate(splashX, splashY);
        guiGraphics.pose().rotate(-0.34906584F);
        float textSize = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
        textSize = textSize * 100.0F / 1 / (font.width(this.splash) + 32);
        guiGraphics.pose().scale(textSize, textSize);
        guiGraphics.drawCenteredString(font, this.splash, 0, -8, 8158399 | color);
        guiGraphics.pose().popMatrix();
    }
}