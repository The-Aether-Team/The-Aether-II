package com.aetherteam.aetherii.client.gui.component.guidebook;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class GuidebookButton extends Button.Plain {
    private final ItemLike renderItem;

    public GuidebookButton(ItemLike renderItem, Builder builder) {
        super(builder);
        this.renderItem = renderItem;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderContents(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.renderItem(new ItemStack(this.renderItem), this.getX() + 3, this.getY() + 3);
    }
}
