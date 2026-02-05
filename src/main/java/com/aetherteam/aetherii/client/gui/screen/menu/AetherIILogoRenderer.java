package com.aetherteam.aetherii.client.gui.screen.menu;

import com.aetherteam.aetherii.AetherII;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;

public class AetherIILogoRenderer extends LogoRenderer {
    private static final ResourceLocation AETHER_LOGO = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/title/aether_ii.png");
    private final boolean keepLogoThroughFade;
    private final boolean alignedLeft;

    public AetherIILogoRenderer(boolean keepLogoThroughFade, boolean alignedLeft) {
        super(keepLogoThroughFade);
        this.keepLogoThroughFade = keepLogoThroughFade;
        this.alignedLeft = alignedLeft;
    }

    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency) {
        this.renderLogo(guiGraphics, screenWidth, transparency, 30);
    }

    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency, int height) {
       // guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : transparency);
       // RenderSystem.enableBlend();
        int logoX = this.alignedLeft ? 8 : (int) ((screenWidth / 2.0F - (190.0F / 2.0F)));
        int logoY = this.alignedLeft ? 8 : 36;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, AETHER_LOGO, logoX, logoY, 0, 0, 190, 61, 190, 61);
       // guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F); //todo
       // RenderSystem.disableBlend();
    }
}
