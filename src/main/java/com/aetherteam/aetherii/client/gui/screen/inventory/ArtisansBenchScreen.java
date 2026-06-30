package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.inventory.menu.ArtisansBenchMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.StonecutterRecipe;

import java.util.List;

public class ArtisansBenchScreen extends AbstractContainerScreen<ArtisansBenchMenu> {
    private static final ResourceLocation BG_LOCATION = new ResourceLocation("textures/gui/container/stonecutter.png");
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    private boolean displayRecipes;

    public ArtisansBenchScreen(ArtisansBenchMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        menu.registerUpdateListener(this::containerChanged);
        this.titleLabelY--;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        this.renderBackground(guiGraphics);
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(BG_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);
        int scroll = (int) (41.0F * this.scrollOffs);
        guiGraphics.blit(BG_LOCATION, x + 119, y + 15 + scroll, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
        int recipeX = this.leftPos + 52;
        int recipeY = this.topPos + 14;
        int lastVisibleIndex = this.startIndex + 12;
        this.renderButtons(guiGraphics, mouseX, mouseY, recipeX, recipeY, lastVisibleIndex);
        this.renderRecipes(guiGraphics, recipeX, recipeY, lastVisibleIndex);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);
        if (this.displayRecipes) {
            int x = this.leftPos + 52;
            int y = this.topPos + 14;
            int lastVisibleIndex = this.startIndex + 12;
            List<StonecutterRecipe> recipes = this.menu.getRecipes();

            for (int index = this.startIndex; index < lastVisibleIndex && index < this.menu.getNumRecipes(); index++) {
                int visibleIndex = index - this.startIndex;
                int itemX = x + visibleIndex % 4 * 16;
                int itemY = y + visibleIndex / 4 * 18 + 2;
                if (mouseX >= itemX && mouseX < itemX + 16 && mouseY >= itemY && mouseY < itemY + 18) {
                    guiGraphics.renderTooltip(this.font, recipes.get(index).getResultItem(this.minecraft.level.registryAccess()), mouseX, mouseY);
                }
            }
        }
    }

    private void renderButtons(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, int lastVisibleIndex) {
        for (int index = this.startIndex; index < lastVisibleIndex && index < this.menu.getNumRecipes(); index++) {
            int visibleIndex = index - this.startIndex;
            int buttonX = x + visibleIndex % 4 * 16;
            int row = visibleIndex / 4;
            int buttonY = y + row * 18 + 2;
            int textureY = this.imageHeight;
            if (index == this.menu.getSelectedRecipeIndex()) {
                textureY += 18;
            } else if (mouseX >= buttonX && mouseY >= buttonY && mouseX < buttonX + 16 && mouseY < buttonY + 18) {
                textureY += 36;
            }

            guiGraphics.blit(BG_LOCATION, buttonX, buttonY - 1, 0, textureY, 16, 18);
        }
    }

    private void renderRecipes(GuiGraphics guiGraphics, int x, int y, int lastVisibleIndex) {
        List<StonecutterRecipe> recipes = this.menu.getRecipes();
        for (int index = this.startIndex; index < lastVisibleIndex && index < this.menu.getNumRecipes(); index++) {
            int visibleIndex = index - this.startIndex;
            int itemX = x + visibleIndex % 4 * 16;
            int row = visibleIndex / 4;
            int itemY = y + row * 18 + 2;
            guiGraphics.renderItem(recipes.get(index).getResultItem(this.minecraft.level.registryAccess()), itemX, itemY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        if (this.displayRecipes) {
            int x = this.leftPos + 52;
            int y = this.topPos + 14;
            int lastVisibleIndex = this.startIndex + 12;

            for (int index = this.startIndex; index < lastVisibleIndex; index++) {
                int visibleIndex = index - this.startIndex;
                double itemX = mouseX - (double) (x + visibleIndex % 4 * 16);
                double itemY = mouseY - (double) (y + visibleIndex / 4 * 18);
                if (itemX >= 0.0 && itemY >= 0.0 && itemX < 16.0 && itemY < 18.0 && this.menu.clickMenuButton(this.minecraft.player, index)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(AetherIISoundEvents.UI_ARTISANS_BENCH_SELECT_RECIPE.get(), 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, index);
                    return true;
                }
            }

            x = this.leftPos + 119;
            y = this.topPos + 9;
            if (mouseX >= (double) x && mouseX < (double) (x + 12) && mouseY >= (double) y && mouseY < (double) (y + 54)) {
                this.scrolling = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling && this.isScrollBarActive()) {
            int top = this.topPos + 14;
            int bottom = top + 54;
            this.scrollOffs = ((float) mouseY - (float) top - 7.5F) / ((float) (bottom - top) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int) ((double) (this.scrollOffs * (float) this.getOffscreenRows()) + 0.5) * 4;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.isScrollBarActive()) {
            int rows = this.getOffscreenRows();
            float amount = (float) delta / (float) rows;
            this.scrollOffs = Mth.clamp(this.scrollOffs - amount, 0.0F, 1.0F);
            this.startIndex = (int) ((double) (this.scrollOffs * (float) rows) + 0.5) * 4;
        }
        return true;
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.menu.getNumRecipes() > 12;
    }

    protected int getOffscreenRows() {
        return (this.menu.getNumRecipes() + 4 - 1) / 4 - 3;
    }

    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }
    }
}


