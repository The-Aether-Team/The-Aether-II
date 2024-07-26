package com.aetherteam.aetherii.client.renderer.item.tooltip;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.aetherteam.aetherii.item.ReinforcementTier;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ClientCharmTooltip implements ClientTooltipComponent {
    public final ItemStack base;
    public final List<ItemStack> items;

    public ClientCharmTooltip(ItemStack base, List<ItemStack> items) {
        this.base = base;
        this.items = items;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                if (index < this.limit()) {
                    ItemStack stack = this.items.get(index);
                    int xOffset = x + (18 * i);
                    int yOffset = y + (18 * j);
                    if (stack.isEmpty()) {
                        guiGraphics.blitSprite(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "tooltip/charm"), xOffset, yOffset, 16, 16);
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
        if (this.limit() > 0) {
            return 18;
        } else if (this.limit() > 4) {
            return 36;
        }
        return 0;
    }

    @Override
    public int getWidth(Font font) {
        return Math.min(this.limit() * 18, 72);
    }

    private int limit() {
        int limit = 0;
        ReinforcementTier tier = this.base.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        if (tier != null) {
            limit = tier.getCharmSlots();
        }
        return limit;
    }

    public record CharmTooltip(ItemStack base, List<ItemStack> items) implements TooltipComponent {

    }
}
