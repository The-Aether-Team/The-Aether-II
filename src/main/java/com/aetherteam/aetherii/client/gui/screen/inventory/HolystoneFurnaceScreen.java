package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.HolystoneFurnaceMenu;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HolystoneFurnaceScreen extends AbstractFurnaceScreen<HolystoneFurnaceMenu> {
    private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation(AetherII.MODID, "textures/gui/menu/holystone_furnace.png");
    private static final ResourceLocation LIT_PROGRESS_TEXTURE = new ResourceLocation("container/furnace/lit_progress");
    private static final ResourceLocation BURN_PROGRESS_TEXTURE = new ResourceLocation("container/furnace/burn_progress");

    public HolystoneFurnaceScreen(HolystoneFurnaceMenu menu, Inventory inventory, Component title) {
        super(menu, new SmeltingRecipeBookComponent(), inventory, title, FURNACE_GUI_TEXTURES, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE);
    }
}