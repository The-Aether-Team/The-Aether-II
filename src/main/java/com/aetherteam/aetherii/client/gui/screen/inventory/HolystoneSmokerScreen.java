package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.HolystoneSmokerRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.HolystoneSmokerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HolystoneSmokerScreen extends AbstractFurnaceScreen<HolystoneSmokerMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AetherII.MODID, "textures/gui/menu/holystone_furnace.png");
    private static final ResourceLocation VANILLA_PROGRESS_TEXTURE = new ResourceLocation("textures/gui/container/smoker.png");

    public HolystoneSmokerScreen(HolystoneSmokerMenu menu, Inventory inventory, Component title) {
        super(menu, new HolystoneSmokerRecipeBookComponent(), inventory, title, TEXTURE);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        if (this.menu.isLit()) {
            int progress = this.menu.getLitProgress() + 1;
            guiGraphics.blit(VANILLA_PROGRESS_TEXTURE, x + 56, y + 36 + 14 - progress, 176, 14 - progress, 14, progress);
        }
        int progress = this.menu.getBurnProgress();
        guiGraphics.blit(VANILLA_PROGRESS_TEXTURE, x + 79, y + 34, 176, 14, progress + 1, 16);
    }
}


