package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.aetherteam.aetherii.item.ReinforcementTier;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class ForgeCharmSlot extends Slot {
    private final ArkeniumForgeMenu menu;

    public ForgeCharmSlot(ArkeniumForgeMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public boolean isActive() {
        ReinforcementTier tier = this.menu.getInput().get(AetherIIDataComponents.REINFORCEMENT_TIER);
        if (tier != null) {
            return this.getSlotIndex() - 3 < tier.getCharmSlots();
        }
        return false;
    }
}
