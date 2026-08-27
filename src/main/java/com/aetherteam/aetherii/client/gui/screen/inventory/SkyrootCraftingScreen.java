package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.SkyrootCraftingTableRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.SkyrootCraftingMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class SkyrootCraftingScreen extends AbstractRecipeBookScreen<SkyrootCraftingMenu> {
    private static final Identifier CRAFTING_TABLE_LOCATION = Identifier.withDefaultNamespace("textures/gui/container/crafting_table.png");

    public SkyrootCraftingScreen(SkyrootCraftingMenu menu, Inventory playerInventory, Component title) {
        super(menu, new SkyrootCraftingTableRecipeBookComponent(menu), playerInventory, title);
    }

    protected void init() {
        super.init();
        this.titleLabelX = 29;
    }

    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 5, this.height / 2 - 49);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CRAFTING_TABLE_LOCATION, i, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }
}
