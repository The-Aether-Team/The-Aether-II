package com.aetherteam.aetherii.client.gui.screen.menu;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;

public class AetherIILogoRenderer extends LogoRenderer {
    private static final ResourceLocation AETHER_LOGO = new ResourceLocation(AetherII.MODID, "textures/gui/title/aether_ii.png");
    private final boolean keepLogoThroughFade;

    public AetherIILogoRenderer(boolean keepLogoThroughFade) {
        super(keepLogoThroughFade);
        this.keepLogoThroughFade = keepLogoThroughFade;
    }

    @Override
    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency) {
        this.renderLogo(guiGraphics, screenWidth, transparency, 16);
    }

    @Override
    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency, int height) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : transparency);
        guiGraphics.blit(AETHER_LOGO, screenWidth / 2 - 109, height, 0.0F, 0.0F, 218, 70, 218, 70);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}


