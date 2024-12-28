package com.aetherteam.aetherii.client.gui.screen.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.gui.screen.inventory.recipebook.AltarRecipeBookComponent;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.book.AetherIIRecipeBookCategories;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeBookCategories;

import java.util.List;

public class AltarScreen extends AbstractRecipeBookScreen<AltarMenu> implements RecipeUpdateListener {
    private static final ResourceLocation ALTAR_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/gui/menu/altar.png");
    private static final ResourceLocation OUTPUT_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/output_progress");
    private static final ResourceLocation CHARGE_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/charge");
    private static final ResourceLocation CHARGE_HORIZONTAL_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/charge_horizontal");
    private static final ResourceLocation CHARGE_VERTICAL_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/charge_vertical");
    private static final ResourceLocation CHARGE_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/altar/charge_slot");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
            new RecipeBookComponent.TabInfo(Items.COMPASS, AetherIIRecipeBookCategories.ALTAR_SEARCH),
            new RecipeBookComponent.TabInfo(AetherIIItems.ENCHANTED_BLUEBERRY.get(), AetherIIRecipeBookCategories.ALTAR_FOOD.get()),
            new RecipeBookComponent.TabInfo(AetherIIBlocks.QUICKSOIL_GLASS.asItem(), AetherIIRecipeBookCategories.ALTAR_BLOCKS.get()),
            new RecipeBookComponent.TabInfo(AetherIIItems.SKYROOT_PICKAXE.get(), AetherIIRecipeBookCategories.ALTAR_REPAIRING.get()),
            new RecipeBookComponent.TabInfo(AetherIIItems.GRAVITITE_PLATE.get(), AetherIIRecipeBookCategories.ALTAR_MISC.get()));
    
    public AltarScreen(AltarMenu menu, Inventory inventory, Component title) {
        super(menu, new AltarRecipeBookComponent(menu, TABS), inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 176;
        this.imageHeight = 214;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + this.imageWidth - 38, this.height / 2 - 17);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderType::guiTextured, ALTAR_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        int slotX = i + 49;
        int slotY = j + 24;
        Direction slotDirection = Direction.WEST;
        for (int index = 1; index <= this.getMenu().getFuelCount(); index++) {
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
        for (int index = 0; index < this.getMenu().getFuelCount(); index++) {
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
}
