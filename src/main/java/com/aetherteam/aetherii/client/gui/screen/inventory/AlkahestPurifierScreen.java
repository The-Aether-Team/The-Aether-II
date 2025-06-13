package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AlkahestPurifierRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;

import java.util.List;

public class AlkahestPurifierScreen extends AbstractRecipeBookScreen<AlkahestPurifierMenu> implements RecipeUpdateListener {
    private static final ResourceLocation ALKAHEST_PURIFIER_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/alkahest_purifier.png");
    private static final ResourceLocation OUTPUT_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/output_progress");
    private static final ResourceLocation ACID_1_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/acid_1");
    private static final ResourceLocation ACID_2_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/acid_2");
    private static final ResourceLocation ACID_3_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/acid_3");
    private static final ResourceLocation ACID_4_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/alkahest_purifier/acid_4");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
            new RecipeBookComponent.TabInfo(Items.COMPASS, AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_SEARCH),
            new RecipeBookComponent.TabInfo(AetherIIItems.IRRADIATED_CHUNK.get(), AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_ITEMS.get()),
            new RecipeBookComponent.TabInfo(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.asItem(), AetherIIRecipeBookCategories.ALKAHEST_PURIFIER_BLOCKS.get()));

    public AlkahestPurifierScreen(AlkahestPurifierMenu menu, Inventory inventory, Component title) {
        super(menu, new AlkahestPurifierRecipeBookComponent(menu, TABS), inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 176;
        this.imageHeight = 193;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + this.imageWidth - 34, this.height / 2 - 78);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderType::guiTextured, ALKAHEST_PURIFIER_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        int alkahestRenderLevels = Mth.floor(this.menu.getAlkahestLevels() / 3.0);
        if (alkahestRenderLevels >= 1) {
            guiGraphics.blitSprite(RenderType::guiTextured, ACID_1_SPRITE, i + 60, j + 78, 56, 16);
        }
        if (alkahestRenderLevels >= 2) {
            guiGraphics.blitSprite(RenderType::guiTextured, ACID_2_SPRITE, i + 60, j + 65, 56, 15);
        }
        if (alkahestRenderLevels >= 3) {
            guiGraphics.blitSprite(RenderType::guiTextured, ACID_3_SPRITE, i + 60, j + 52, 56, 15);
        }
        if (alkahestRenderLevels >= 4) {
            guiGraphics.blitSprite(RenderType::guiTextured, ACID_4_SPRITE, i + 60, j + 41, 56, 13);
        }

        int j1 = Mth.ceil(this.menu.getProcessingProgress() * 15.0F);
        guiGraphics.blitSprite(RenderType::guiTextured, OUTPUT_PROGRESS_SPRITE, 15, 10, 0, 0, i + 121, j + 48, j1, 10);
    }
}
