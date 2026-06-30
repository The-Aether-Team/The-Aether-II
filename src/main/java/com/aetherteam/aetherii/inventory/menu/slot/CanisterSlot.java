package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CanisterSlot extends Slot {
    public static final ResourceLocation SLOT_CANISTER = new ResourceLocation(AetherII.MODID, "container/alkahest_purifier/slot_canister");
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
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, SLOT_CANISTER);
    }
}
