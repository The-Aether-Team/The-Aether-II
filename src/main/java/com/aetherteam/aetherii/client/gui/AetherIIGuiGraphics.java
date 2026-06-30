package com.aetherteam.aetherii.client.gui;

import com.aetherteam.aetherii.util.ARGB;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public final class AetherIIGuiGraphics {
    private AetherIIGuiGraphics() {
    }

    public static ResourceLocation spriteTexture(ResourceLocation sprite) {
        if (sprite.getPath().startsWith("textures/")) {
            return sprite;
        }
        return new ResourceLocation(sprite.getNamespace(), "textures/gui/sprites/" + sprite.getPath() + ".png");
    }

    public static void blitSprite(GuiGraphics guiGraphics, ResourceLocation sprite, int x, int y, int width, int height) {
        guiGraphics.blit(spriteTexture(sprite), x, y, 0.0F, 0.0F, width, height, width, height);
    }

    public static void blitSprite(GuiGraphics guiGraphics, ResourceLocation sprite, int x, int y, int width, int height, int color) {
        guiGraphics.setColor(ARGB.redFloat(color), ARGB.greenFloat(color), ARGB.blueFloat(color), ARGB.alphaFloat(color));
        blitSprite(guiGraphics, sprite, x, y, width, height);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void blitSprite(GuiGraphics guiGraphics, ResourceLocation sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {
        guiGraphics.blit(spriteTexture(sprite), x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static void blitSprite(GuiGraphics guiGraphics, TextureAtlasSprite sprite, int x, int y, int width, int height) {
        guiGraphics.blit(x, y, 0, width, height, sprite);
    }

    public static void blitSprite(GuiGraphics guiGraphics, TextureAtlasSprite sprite, int x, int y, int width, int height, int color) {
        guiGraphics.blit(x, y, 0, width, height, sprite, ARGB.redFloat(color), ARGB.greenFloat(color), ARGB.blueFloat(color), ARGB.alphaFloat(color));
    }
}


