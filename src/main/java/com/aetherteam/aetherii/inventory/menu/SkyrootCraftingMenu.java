package com.aetherteam.aetherii.inventory.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;

public class SkyrootCraftingMenu extends CraftingMenu {
    public SkyrootCraftingMenu(int containerId, Inventory playerInventory) {
        super(containerId, playerInventory);
    }

    public SkyrootCraftingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(containerId, playerInventory, access);
    }

    @Override
    public MenuType<?> getType() {
        return AetherIIMenuTypes.SKYROOT_CRAFTING_TABLE.get();
    }
}
