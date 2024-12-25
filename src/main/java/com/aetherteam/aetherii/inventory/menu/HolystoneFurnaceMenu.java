package com.aetherteam.aetherii.inventory.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;

public class HolystoneFurnaceMenu extends AbstractFurnaceMenu {
    public HolystoneFurnaceMenu(int containerId, Inventory playerInventory) {
        super(AetherIIMenuTypes.HOLYSTONE_FURNACE.get(), RecipeType.SMELTING, RecipePropertySet.FURNACE_INPUT, RecipeBookType.FURNACE, containerId, playerInventory);
    }

    public HolystoneFurnaceMenu(int containerId, Inventory playerInventory, Container furnaceContainer, ContainerData data) {
        super(AetherIIMenuTypes.HOLYSTONE_FURNACE.get(), RecipeType.SMELTING,RecipePropertySet.FURNACE_INPUT,  RecipeBookType.FURNACE, containerId, playerInventory, furnaceContainer, data);
    }
}