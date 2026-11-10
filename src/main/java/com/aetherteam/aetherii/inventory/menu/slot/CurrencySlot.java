package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.attachment.player.CurrencyAttachment;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CurrencySlot extends Slot {
    private final CurrencyAttachment attachment;

    public CurrencySlot(CurrencyAttachment attachment, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.attachment = attachment;
    }

    public int getAmount() {
        return this.attachment.getAmount();
    }

    public void setAmount(int amount) {
        this.attachment.setAmount(amount);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean mayPickup(Player player) {
        return false;
    }

    @Override
    public boolean isFake() {
        return true;
    }
}
