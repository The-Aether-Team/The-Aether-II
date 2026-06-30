package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AlkahestPurifierRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class AlkahestPurifierScreen extends BaseAetherRecipeBookScreen<AlkahestPurifierMenu> {
    private static final ResourceLocation ALKAHEST_PURIFIER_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/gui/menu/alkahest_purifier.png");
    private static final ResourceLocation OUTPUT_PROGRESS_SPRITE = new ResourceLocation(AetherII.MODID, "container/alkahest_purifier/output_progress");
    private static final ResourceLocation BUBBLES_SPRITE = new ResourceLocation(AetherII.MODID, "container/alkahest_purifier/bubbles");
    private static final ResourceLocation ALKAHEST_1_SPRITE = new ResourceLocation(AetherII.MODID, "container/alkahest_purifier/alkahest_1");
    private static final ResourceLocation ALKAHEST_2_SPRITE = new ResourceLocation(AetherII.MODID, "container/alkahest_purifier/alkahest_2");
    private static final ResourceLocation ALKAHEST_3_SPRITE = new ResourceLocation(AetherII.MODID, "container/alkahest_purifier/alkahest_3");
    private static final ResourceLocation ALKAHEST_4_SPRITE = new ResourceLocation(AetherII.MODID, "container/alkahest_purifier/alkahest_4");
    private static final int[] BUBBLE_LENGTHS = new int[]{0, 3, 7, 12, 18};

    public AlkahestPurifierScreen(AlkahestPurifierMenu menu, Inventory inventory, Component title) {
        super(menu, new AlkahestPurifierRecipeBookComponent(), inventory, title);
    }

    @Override
    protected void init() {
        this.imageWidth = 176;
        this.imageHeight = 193;
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected int getRecipeBookButtonXOffset() {
        return this.imageWidth - 34;
    }

    @Override
    protected int getRecipeBookButtonY() {
        return this.height / 2 - 78;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(ALKAHEST_PURIFIER_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        int alkahestRenderLevels = Mth.ceil(this.menu.getAlkahestLevels() / 3.0);
        if (alkahestRenderLevels >= 1) {
            AetherIIGuiGraphics.blitSprite(guiGraphics, ALKAHEST_1_SPRITE, x + 60, y + 78, 56, 16);
        }
        if (alkahestRenderLevels >= 2) {
            AetherIIGuiGraphics.blitSprite(guiGraphics, ALKAHEST_2_SPRITE, x + 60, y + 65, 56, 15);
        }
        if (alkahestRenderLevels >= 3) {
            AetherIIGuiGraphics.blitSprite(guiGraphics, ALKAHEST_3_SPRITE, x + 60, y + 52, 56, 15);
        }
        if (alkahestRenderLevels >= 4) {
            AetherIIGuiGraphics.blitSprite(guiGraphics, ALKAHEST_4_SPRITE, x + 60, y + 41, 56, 13);
        }

        float alkahestRenderLevelTop = (this.menu.getAlkahestLevels() / 3.0F) % 1;
        int maxIndex;
        if (alkahestRenderLevelTop > 0.7F || alkahestRenderLevelTop == 0.0F) {
            maxIndex = 4;
        } else if (alkahestRenderLevelTop > 0.4F) {
            maxIndex = 3;
        } else {
            maxIndex = 2;
        }
        int width = BUBBLE_LENGTHS[Mth.ceil(this.menu.getProcessingProgress() * 64.0F) % (maxIndex + 1)];
        AetherIIGuiGraphics.blitSprite(guiGraphics, BUBBLES_SPRITE, 18, 10, 0, 0, x + 119, y + 37, width, 10);

        int processingProgress = Mth.ceil(this.menu.getProcessingProgress() * 18.0F);
        if (processingProgress > 0) {
            AetherIIGuiGraphics.blitSprite(guiGraphics, OUTPUT_PROGRESS_SPRITE, 18, 8, 0, 0, x + 119, y + 49, processingProgress, 9);
        }
    }
}


