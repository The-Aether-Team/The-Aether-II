package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AlkahestPurifierRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import net.minecraft.client.gui.GuiGraphics;
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

public class AlkahestPurifierScreen extends AbstractRecipeBookScreen<AlkahestPurifierMenu> implements RecipeUpdateListener {
    private static final Identifier ALKAHEST_PURIFIER_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/alkahest_purifier.png");
    private static final Identifier OUTPUT_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/output_progress");
    private static final Identifier BUBBLES_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/bubbles");
    private static final Identifier ALKAHEST_1_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/alkahest_1");
    private static final Identifier ALKAHEST_2_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/alkahest_2");
    private static final Identifier ALKAHEST_3_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/alkahest_3");
    private static final Identifier ALKAHEST_4_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/alkahest_4");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
            new RecipeBookComponent.TabInfo(Items.COMPASS, AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_SEARCH),
            new RecipeBookComponent.TabInfo(AetherIIItems.IRRADIATED_CHUNK.get(), AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_ITEMS.get()),
            new RecipeBookComponent.TabInfo(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.asItem(), AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_BLOCKS.get()));
    private static final int[] BUBBLE_LENGTHS = new int[]{0, 3, 7, 12, 18};

    public AlkahestPurifierScreen(AlkahestPurifierMenu menu, Inventory inventory, Component title) {
        super(menu, new AlkahestPurifierRecipeBookComponent(menu, TABS), inventory, title);
    }

    @Override
    protected void init() {
        this.imageWidth = 176;
        this.imageHeight = 193;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
        super.init();
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + this.imageWidth - 34, this.height / 2 - 78);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ALKAHEST_PURIFIER_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        int alkahestRenderLevels = Mth.ceil(this.menu.getAlkahestLevels() / 3.0);
        if (alkahestRenderLevels >= 1) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ALKAHEST_1_SPRITE, i + 60, j + 78, 56, 16);
        }
        if (alkahestRenderLevels >= 2) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ALKAHEST_2_SPRITE, i + 60, j + 65, 56, 15);
        }
        if (alkahestRenderLevels >= 3) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ALKAHEST_3_SPRITE, i + 60, j + 52, 56, 15);
        }
        if (alkahestRenderLevels >= 4) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ALKAHEST_4_SPRITE, i + 60, j + 41, 56, 13);
        }

        float alkahestRenderLevelTop = (this.menu.getAlkahestLevels() / 3.0F) % 1;
        int maxIndex;
        if (alkahestRenderLevelTop > 0.7 || alkahestRenderLevelTop == 0.0) {
            maxIndex = 4;
        } else if (alkahestRenderLevelTop > 0.4) {
            maxIndex = 3;
        } else {
            maxIndex = 2;
        }
        int width = BUBBLE_LENGTHS[Mth.ceil(this.menu.getProcessingProgress() * 64.0F) % (maxIndex + 1)];

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUBBLES_SPRITE, 18, 10, 0, 0, i + 119, j + 37, width, 10);

        int processingProgress = Mth.ceil(this.menu.getProcessingProgress() * 18.0F);
        if (processingProgress > 0) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, OUTPUT_PROGRESS_SPRITE, 18, 8, 0, 0, i + 119, j + 49, processingProgress, 9);
        }
    }
}
