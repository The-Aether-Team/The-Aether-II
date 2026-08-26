package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CanisterSlot extends Slot {
    public static final Identifier SLOT_CANISTER = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/slot_canister");
    private final AlkahestPurifierMenu menu;

    public CanisterSlot(AlkahestPurifierMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.menu.isFuel(stack);
    }

    @Override
    public Identifier getNoItemIcon() {
        return SLOT_CANISTER;
    }
}
