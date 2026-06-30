package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.SkyrootCraftingTableRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.SkyrootCraftingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SkyrootCraftingScreen extends BaseAetherRecipeBookScreen<SkyrootCraftingMenu> {
    private static final ResourceLocation CRAFTING_TABLE_LOCATION = new ResourceLocation("textures/gui/container/crafting_table.png");

    public SkyrootCraftingScreen(SkyrootCraftingMenu menu, Inventory inventory, Component title) {
        super(menu, new SkyrootCraftingTableRecipeBookComponent(), inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 29;
    }

    @Override
    protected int getRecipeBookButtonXOffset() {
        return 5;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(CRAFTING_TABLE_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}


