package com.aetherteam.aetherii.client.gui.component.guidebook;

import net.minecraft.client.gui.GuiGraphicsExtractor;
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
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractContents(graphics, mouseX, mouseY, a);
        graphics.item(new ItemStack(this.renderItem), this.getX() + 3, this.getY() + 3);
    }

}
