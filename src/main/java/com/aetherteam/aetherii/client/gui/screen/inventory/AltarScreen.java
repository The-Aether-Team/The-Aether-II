package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AltarRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class AltarScreen extends BaseAetherRecipeBookScreen<AltarMenu> {
    private static final ResourceLocation ALTAR_TEXTURE = new ResourceLocation(AetherII.MODID, "textures/gui/menu/altar.png");
    private static final ResourceLocation OUTPUT_PROGRESS_SPRITE = new ResourceLocation(AetherII.MODID, "container/altar/output_progress");
    private static final ResourceLocation CHARGE_SPRITE = new ResourceLocation(AetherII.MODID, "container/altar/charge");
    private static final ResourceLocation CHARGE_HORIZONTAL_SPRITE = new ResourceLocation(AetherII.MODID, "container/altar/charge_horizontal");
    private static final ResourceLocation CHARGE_VERTICAL_SPRITE = new ResourceLocation(AetherII.MODID, "container/altar/charge_vertical");
    private static final ResourceLocation CHARGE_SLOT_SPRITE = new ResourceLocation(AetherII.MODID, "container/altar/charge_slot");

    public AltarScreen(AltarMenu menu, Inventory inventory, Component title) {
        super(menu, new AltarRecipeBookComponent(), inventory, title);
    }

    @Override
    protected void init() {
        this.imageWidth = 176;
        this.imageHeight = 214;
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected int getRecipeBookButtonXOffset() {
        return this.imageWidth - 38;
    }

    @Override
    protected int getRecipeBookButtonY() {
        return this.height / 2 - 17;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(ALTAR_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        int slotX = x + 49;
        int slotY = y + 24;
        Direction slotDirection = Direction.WEST;
        for (int index = 1; index <= this.getMenu().getFuelCount(); index++) {
            AetherIIGuiGraphics.blitSprite(guiGraphics, CHARGE_SLOT_SPRITE, slotX, slotY, 20, 20);
            if (index % 2 == 0) {
                slotDirection = slotDirection.getCounterClockWise();
            }
            slotX += 32 * slotDirection.getStepX();
            slotY += 32 * slotDirection.getStepZ();
        }

        int chargeX = x + 75;
        int chargeY = y + 34;
        Direction chargeDirection = Direction.WEST;
        for (int index = 0; index < this.getMenu().getFuelCount(); index++) {
            if (index == 0) {
                AetherIIGuiGraphics.blitSprite(guiGraphics, CHARGE_SPRITE, x + 57, y + 44, 4, 7);
            } else if (chargeDirection.getStepX() != 0) {
                AetherIIGuiGraphics.blitSprite(guiGraphics, CHARGE_HORIZONTAL_SPRITE, chargeX - 6, chargeY - 2, 12, 4);
            } else {
                AetherIIGuiGraphics.blitSprite(guiGraphics, CHARGE_VERTICAL_SPRITE, chargeX - 2, chargeY - 6, 4, 12);
            }

            if (index % 2 == 1) {
                chargeDirection = chargeDirection.getCounterClockWise();
                chargeX += 16 * chargeDirection.getStepX() + 16 * chargeDirection.getClockWise().getStepX();
                chargeY += 16 * chargeDirection.getStepZ() + 16 * chargeDirection.getClockWise().getStepZ();
            } else {
                chargeX += 32 * chargeDirection.getStepX();
                chargeY += 32 * chargeDirection.getStepZ();
            }
        }

        int progress = Mth.ceil(this.menu.getProcessingProgress() * 26.0F);
        AetherIIGuiGraphics.blitSprite(guiGraphics, OUTPUT_PROGRESS_SPRITE, 26, 16, 0, 0, x + 107, y + 58, progress, 16);
    }
}


