package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AltarRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AltarScreen extends AbstractContainerScreen<AltarMenu> implements RecipeUpdateListener {
    private static final ResourceLocation ALTAR_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/altar.png");
    private static final ResourceLocation OUTPUT_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/output_progress");
    private static final ResourceLocation CHARGE_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/charge");
    private static final ResourceLocation CHARGE_HORIZONTAL_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/charge_horizontal");
    private static final ResourceLocation CHARGE_VERTICAL_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/charge_vertical");
    private static final ResourceLocation CHARGE_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/charge_slot");

    public final AltarRecipeBookComponent recipeBookComponent;
    private boolean widthTooNarrow;
    
    public AltarScreen(AltarMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.recipeBookComponent = new AltarRecipeBookComponent();
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 176;
        this.imageHeight = 214;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.widthTooNarrow = this.width < 379;
        this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.addRenderableWidget(new ImageButton(this.leftPos + this.imageWidth - 38, this.height / 2 - 17, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, button -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            button.setPosition(this.leftPos + this.imageWidth - 38, this.height / 2 - 17);
        }));
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.recipeBookComponent.tick();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
            this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
            this.recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick);
        } else {
            super.render(guiGraphics, mouseX, mouseY, partialTick);
            this.recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick);
            this.recipeBookComponent.renderGhostRecipe(guiGraphics, this.leftPos, this.topPos, true, partialTick);
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.recipeBookComponent.renderTooltip(guiGraphics, this.leftPos, this.topPos, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        ItemStack input = this.menu.getInputStack();

        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(ALTAR_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);

        int slotX = i + 49;
        int slotY = j + 24;
        Direction slotDirection = Direction.WEST;
        for (int index = 1; index <= this.getMenu().getRecipeFuelCount(input); index++) {
            guiGraphics.blitSprite(RenderType::guiTextured, CHARGE_SLOT_SPRITE, slotX, slotY, 20, 20);
            if (index % 2 == 0) {
                slotDirection = slotDirection.getCounterClockWise();
            }
            slotX += (32 * slotDirection.getStepX());
            slotY += (32 * slotDirection.getStepZ());
        }

        int chargeX = i + 75;
        int chargeY = j + 34;
        Direction chargeDirection = Direction.WEST;
        for (int index = 0; index < this.getMenu().getRecipeFuelCount(input); index++) {
            if (index == 0) {
                guiGraphics.blitSprite(RenderType::guiTextured, CHARGE_SPRITE, i + 57, j + 44, 4, 7);
            } else {
                if (chargeDirection.getStepX() != 0) {
                    guiGraphics.blitSprite(RenderType::guiTextured, CHARGE_HORIZONTAL_SPRITE, chargeX - 6, chargeY - 2, 12, 4);
                } else {
                    guiGraphics.blitSprite(RenderType::guiTextured, CHARGE_VERTICAL_SPRITE, chargeX - 2, chargeY - 6, 4, 12);
                }
            }
            if (index % 2 == 1) {
                chargeDirection = chargeDirection.getCounterClockWise();
                chargeX += (16 * chargeDirection.getStepX()) + (16 * chargeDirection.getClockWise().getStepX());
                chargeY += (16 * chargeDirection.getStepZ()) + (16 * chargeDirection.getClockWise().getStepZ());
            } else {
                chargeX += (32 * chargeDirection.getStepX());
                chargeY += (32 * chargeDirection.getStepZ());
            }
        }

        int j1 = Mth.ceil(this.menu.getProcessingProgress() * 26.0F);
        guiGraphics.blitSprite(RenderType::guiTextured, OUTPUT_PROGRESS_SPRITE, 26, 16, 0, 0, i + 107, j + 58, j1, 16);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return this.widthTooNarrow && this.recipeBookComponent.isVisible() || super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);
        this.recipeBookComponent.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return !this.recipeBookComponent.keyPressed(keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        boolean flag = mouseX < (double) guiLeft || mouseY < (double) guiTop || mouseX >= (double) (guiLeft + this.imageWidth) || mouseY >= (double) (guiTop + this.imageHeight);
        return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return this.recipeBookComponent.charTyped(codePoint, modifiers) || super.charTyped(codePoint, modifiers);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookComponent.recipesUpdated();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookComponent;
    }
}
