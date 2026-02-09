package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AltarRecipeBookComponent;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AmberHourglassRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.aetherteam.aetherii.inventory.menu.AmberHourglassMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;

import java.util.List;

public class AmberHourglassScreen extends AbstractRecipeBookScreen<AmberHourglassMenu> implements RecipeUpdateListener {
    private static final ResourceLocation AMBER_HOURGLASS_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/amber_hourglass.png");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
            new RecipeBookComponent.TabInfo(Items.COMPASS, AetherIIRecipeBookCategories.AMBER_HOURGLASS_SEARCH),
            new RecipeBookComponent.TabInfo(AetherIIItems.ZANITE_GEMSTONE.get(), AetherIIRecipeBookCategories.AMBER_HOURGLASS_ITEMS.get()),
//            new RecipeBookComponent.TabInfo(AetherIIBlocks.QUICKSOIL_GLASS.asItem(), AetherIIRecipeBookCategories.AMBER_HOURGLASS_BLOCKS.get()),
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
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, AMBER_HOURGLASS_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }
}
