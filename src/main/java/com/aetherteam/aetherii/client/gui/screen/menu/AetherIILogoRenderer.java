package com.aetherteam.aetherii.client.gui.screen.menu;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class AetherIILogoRenderer extends LogoRenderer {
    private static final Identifier AETHER_LOGO = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/title/aether_ii.png");
    private final boolean keepLogoThroughFade;

    public AetherIILogoRenderer(boolean keepLogoThroughFade) {
        super(keepLogoThroughFade);
        this.keepLogoThroughFade = keepLogoThroughFade;
    }

    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency) {
        this.renderLogo(guiGraphics, screenWidth, transparency, 30);
    }

    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency, int height) {
        float f = this.keepLogoThroughFade ? 1.0F : transparency;
        int i = ARGB.white(f);
        int logoX = (int) ((screenWidth / 2.0F - (218.0F / 2.0F)));
        int logoY = 16;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, AETHER_LOGO, logoX, logoY, 0, 0, 218, 70, 218, 70, i);
    }
}