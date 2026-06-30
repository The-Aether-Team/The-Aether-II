package com.aetherteam.aetherii.client.gui.component;

import com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;

public class AetherIIImageButton extends Button {
    private final AetherIIWidgetSprites sprites;

    public AetherIIImageButton(int x, int y, int width, int height, AetherIIWidgetSprites sprites, OnPress onPress) {
        super(x, y, width, height, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        this.sprites = sprites;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        AetherIIGuiGraphics.blitSprite(guiGraphics, this.sprites.get(this.isActive(), this.isHoveredOrFocused()), this.getX(), this.getY(), this.width, this.height);
    }
}


