package com.aetherteam.aetherii.client.renderer.item.tooltip;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.components.Charms;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Locale;

public record ClientCharmTooltip(ItemStack base, List<Charms.CharmHolder> charmHolders) implements ClientTooltipComponent {
    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                if (index < this.limit()) {
                    Charms.CharmHolder charmHolder = this.charmHolders.get(index);
                    ItemStack stack = charmHolder.getStack();
                    int xOffset = x + (18 * i);
                    int yOffset = y + (18 * j);
                    if (stack.isEmpty()) {
                        ResourceLocation texture = new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/arkenium_forge/slot_" + charmHolder.getType().name().toLowerCase(Locale.ROOT) + "_charm_" + charmHolder.getTier().getValue() + ".png");
                        guiGraphics.blit(texture, xOffset, yOffset, 0, 0, 16, 16, 16, 16);
                    } else {
                        guiGraphics.renderItem(stack, xOffset, yOffset);
                    }
                }
                index++;
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.limit() > 4) {
            return 36;
        } else if (this.limit() > 0) {
            return 18;
        }
        return 0;
    }

    @Override
    public int getWidth(Font font) {
        return Math.min(this.limit() * 18, 72);
    }

    private int limit() {
        return this.charmHolders().size();
    }

    public record CharmTooltip(ItemStack base, List<Charms.CharmHolder> items) implements TooltipComponent {

    }
}
