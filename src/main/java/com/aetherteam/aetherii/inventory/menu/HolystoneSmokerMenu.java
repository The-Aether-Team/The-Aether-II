package com.aetherteam.aetherii.inventory.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

public class HolystoneSmokerMenu extends AbstractFurnaceMenu {
    public HolystoneSmokerMenu(int containerId, Inventory playerInventory) {
        super(AetherIIMenuTypes.HOLYSTONE_SMOKER.get(), RecipeType.SMOKING, RecipeBookType.SMOKER, containerId, playerInventory);
    }

    public HolystoneSmokerMenu(int containerId, Inventory playerInventory, Container furnaceContainer, ContainerData data) {
        super(AetherIIMenuTypes.HOLYSTONE_SMOKER.get(), RecipeType.SMOKING, RecipeBookType.SMOKER, containerId, playerInventory, furnaceContainer, data);
    }
}
