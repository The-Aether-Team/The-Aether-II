package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AmberHourglassRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.AmberHourglassMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;

import java.util.List;

public class AmberHourglassScreen extends AbstractRecipeBookScreen<AmberHourglassMenu> implements RecipeUpdateListener {
    private static final Identifier AMBER_HOURGLASS_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/amber_hourglass.png");
    private static final Identifier FUEL_BAR_LEFT_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/amber_hourglass/fuel_bar_left");
    private static final Identifier FUEL_BAR_RIGHT_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/amber_hourglass/fuel_bar_right");
    private static final Identifier PROGRESS_BAR_LEFT_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/amber_hourglass/progress_bar_left");
    private static final Identifier PROGRESS_BAR_RIGHT_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/amber_hourglass/progress_bar_right");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
            new RecipeBookComponent.TabInfo(Items.COMPASS, AetherIIRecipeBookCategories.AMBER_HOURGLASS_SEARCH),
            new RecipeBookComponent.TabInfo(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIRecipeBookCategories.AMBER_HOURGLASS_RESTORATION.get()),
            new RecipeBookComponent.TabInfo(AetherIIItems.SKYROOT_PICKAXE.get(), AetherIIRecipeBookCategories.AMBER_HOURGLASS_UNCRAFTING.get()));

    public AmberHourglassScreen(AmberHourglassMenu menu, Inventory inventory, Component title) {
        super(menu, new AmberHourglassRecipeBookComponent(menu, TABS), inventory, title);
    }

    @Override
    protected void init() {
        this.imageWidth = 176;
        this.imageHeight = 222;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
        super.init();
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 9, this.height / 2 - 50);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, AMBER_HOURGLASS_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        if (this.menu.isPowered()) {
            int l = Mth.ceil(this.menu.getPowerProgress() * 19.0F) + 1;
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FUEL_BAR_LEFT_SPRITE, 4, 20, 0, 20 - l, i + 71, j + 60 + 20 - l, 4, l);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FUEL_BAR_RIGHT_SPRITE, 4, 20, 0, 20 - l, i + 101, j + 60 + 20 - l, 4, l);
        }

        int j1 = Mth.ceil(this.menu.getProcessingProgress() * 47.0F);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_BAR_LEFT_SPRITE, 28, 47, 0, 0, i + 41, j + 23, 28, j1);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_BAR_RIGHT_SPRITE, 28, 47, 0, 0, i + 107, j + 23, 28, j1);
    }
}
