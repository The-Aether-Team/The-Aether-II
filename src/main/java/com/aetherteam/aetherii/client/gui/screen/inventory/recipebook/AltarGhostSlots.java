package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.mixin.mixins.client.accessor.GhostSlotsAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.SlotSelectTime;
import net.minecraft.world.item.ItemStack;

public class AltarGhostSlots extends GhostSlots {
    private final SlotSelectTime slotSelectTime;

    public AltarGhostSlots(SlotSelectTime slotSelectTime) {
        super(slotSelectTime);
        this.slotSelectTime = slotSelectTime;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, Minecraft minecraft, boolean isBiggerResultSlot) {
        ((GhostSlotsAccessor) this).aether_ii$getIngredients().forEach((slot, ghostSlot) -> {
            int x = slot.x;
            int y = slot.y;
            if (ghostSlot.isResultSlot() && isBiggerResultSlot) {
                guiGraphics.fill(x - 4, y - 4, x + 20, y + 20, 822018048);
            } else {
                guiGraphics.fill(x, y, x + 16, y + 16, 822018048);
            }

            ItemStack stack = ghostSlot.getItem(this.slotSelectTime.currentIndex());
            guiGraphics.renderFakeItem(stack, x, y);
            guiGraphics.fill(x, y, x + 16, y + 16, 822083583);
            guiGraphics.renderItemDecorations(minecraft.font, stack, x, y);
        });
    }
}
