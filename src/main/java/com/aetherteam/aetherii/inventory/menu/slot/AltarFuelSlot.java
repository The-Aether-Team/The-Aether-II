package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AltarFuelSlot extends Slot {
    public static final ResourceLocation SLOT_FUEL = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/slot_fuel");
    private final AltarMenu menu;

    public AltarFuelSlot(AltarMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.menu.isFuel(stack);
    }

    @Override
    public ResourceLocation getNoItemIcon() {
        return SLOT_FUEL;
    }
}
