package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AmberHourglassRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.AmberHourglassMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class AmberHourglassScreen extends BaseAetherRecipeBookScreen<AmberHourglassMenu> {
    private static final ResourceLocation AMBER_HOURGLASS_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/gui/menu/amber_hourglass.png");
    private static final ResourceLocation FUEL_BAR_LEFT_SPRITE = new ResourceLocation(AetherII.MODID, "container/amber_hourglass/fuel_bar_left");
    private static final ResourceLocation FUEL_BAR_RIGHT_SPRITE = new ResourceLocation(AetherII.MODID, "container/amber_hourglass/fuel_bar_right");
    private static final ResourceLocation PROGRESS_BAR_LEFT_SPRITE = new ResourceLocation(AetherII.MODID, "container/amber_hourglass/progress_bar_left");
    private static final ResourceLocation PROGRESS_BAR_RIGHT_SPRITE = new ResourceLocation(AetherII.MODID, "container/amber_hourglass/progress_bar_right");

    public AmberHourglassScreen(AmberHourglassMenu menu, Inventory inventory, Component title) {
        super(menu, new AmberHourglassRecipeBookComponent(), inventory, title);
    }

    @Override
    protected void init() {
        this.imageWidth = 176;
        this.imageHeight = 222;
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected int getRecipeBookButtonXOffset() {
        return 9;
    }

    @Override
    protected int getRecipeBookButtonY() {
        return this.height / 2 - 50;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(AMBER_HOURGLASS_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        if (this.menu.isPowered()) {
            int height = Mth.ceil(this.menu.getPowerProgress() * 19.0F) + 1;
            AetherIIGuiGraphics.blitSprite(guiGraphics, FUEL_BAR_LEFT_SPRITE, 4, 20, 0, 20 - height, x + 71, y + 60 + 20 - height, 4, height);
            AetherIIGuiGraphics.blitSprite(guiGraphics, FUEL_BAR_RIGHT_SPRITE, 4, 20, 0, 20 - height, x + 101, y + 60 + 20 - height, 4, height);
        }

        int progress = Mth.ceil(this.menu.getProcessingProgress() * 47.0F);
        AetherIIGuiGraphics.blitSprite(guiGraphics, PROGRESS_BAR_LEFT_SPRITE, 28, 47, 0, 0, x + 41, y + 23, 28, progress);
        AetherIIGuiGraphics.blitSprite(guiGraphics, PROGRESS_BAR_RIGHT_SPRITE, 28, 47, 0, 0, x + 107, y + 23, 28, progress);
    }
}


