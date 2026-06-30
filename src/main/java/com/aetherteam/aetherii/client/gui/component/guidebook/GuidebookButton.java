package com.aetherteam.aetherii.client.gui.component.guidebook;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class GuidebookButton extends Button {
    private final ItemLike renderItem;

    public GuidebookButton(ItemLike renderItem, Component message, int x, int y, int width, int height, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.renderItem = renderItem;
    }

    public GuidebookButton(ItemLike renderItem, int x, int y, int width, int height, OnPress onPress) {
        this(renderItem, Component.empty(), x, y, width, height, onPress);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float a) {
        super.renderWidget(graphics, mouseX, mouseY, a);
        graphics.renderItem(new ItemStack(this.renderItem), this.getX() + 3, this.getY() + 3);
    }
}


