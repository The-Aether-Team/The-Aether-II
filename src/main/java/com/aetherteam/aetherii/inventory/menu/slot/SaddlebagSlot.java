package com.aetherteam.aetherii.inventory.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SaddlebagSlot extends Slot {
    public final int originalX;
    private boolean hidden;

    public SaddlebagSlot(Container container, int slot, int x, int y, boolean hidden) {
        super(container, slot, x, y);
        this.originalX = x;
        this.hidden = hidden;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return !this.isHidden();
    }

    @Override
    public boolean mayPickup(Player player) {
        return !this.isHidden();
    }

    @Override
    public boolean isActive() {
        return !this.isHidden();
    }

    @Override
    public boolean isHighlightable() {
        return !this.isHidden();
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
