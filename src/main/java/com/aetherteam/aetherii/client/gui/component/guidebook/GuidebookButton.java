package com.aetherteam.aetherii.client.gui.component.guidebook;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class GuidebookButton extends Button {
    private final ItemLike renderItem;

    public GuidebookButton(ItemLike renderItem, Builder builder) {
        super(builder);
        this.renderItem = renderItem;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.renderItem(new ItemStack(this.renderItem), this.getX() + 3, this.getY() + 3);
    }
}
