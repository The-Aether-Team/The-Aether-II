package com.aetherteam.aetherii.integration.jei.categories.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AltarRecipeCategory extends AbstractRecipeCategory<AltarEnchantingRecipe> {
    public static final RecipeType<AltarEnchantingRecipe> ALTAR_ENCHANTING = RecipeType.create(AetherII.MODID, "altar_enchanting", AltarEnchantingRecipe.class);
    private final IDrawable background;
    private final IDrawable progress;
    private final IDrawable chargeSlot;
    private final IDrawable normalCharge;
    private final IDrawable horizontalCharge;
    private final IDrawable verticalCharge;

    public AltarRecipeCategory(IGuiHelper helper) {
        super(ALTAR_ENCHANTING, Component.translatable("gui.aether_ii.jei.altar"), helper.createDrawableItemLike(AetherIIBlocks.ALTAR.get()), 160, 102);
        this.background = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/jei/altar.png"), 0, 0, 158, 100).setTextureSize(158, 100).build();
        this.progress = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/altar/output_progress.png"), 0, 0, 26, 16).setTextureSize(26, 16).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        this.chargeSlot = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/altar/charge_slot.png"), 0, 0, 20, 20).setTextureSize(20, 20).build();
        this.normalCharge = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/altar/charge.png"), 0, 0, 4, 7).setTextureSize(4, 7).build();
        this.horizontalCharge = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/altar/charge_horizontal.png"), 0, 0, 12, 4).setTextureSize(12, 4).build();
        this.verticalCharge = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/altar/charge_vertical.png"), 0, 0, 4, 12).setTextureSize(4, 12).build();
    }

    @Override
    public void draw(AltarEnchantingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        this.background.draw(graphics, 1, 1);
        this.progress.draw(graphics, 99, 43);
        int slotX = 41;
        int slotY = 9;
        Direction slotDirection = Direction.WEST;
        for (int index = 1; index <= recipe.fuelCount(); index++) {
            this.chargeSlot.draw(graphics, slotX, slotY);
            if (index % 2 == 0) {
                slotDirection = slotDirection.getCounterClockWise();
            }
            slotX += 32 * slotDirection.getStepX();
            slotY += 32 * slotDirection.getStepZ();
        }

        int chargeX = 67;
        int chargeY = 19;
        Direction chargeDirection = Direction.WEST;
        for (int index = 0; index < recipe.fuelCount(); index++) {
            if (index == 0) {
                this.normalCharge.draw(graphics, 49, 29);
            } else if (chargeDirection.getStepX() != 0) {
                this.horizontalCharge.draw(graphics, chargeX - 6, chargeY - 2);
            } else {
                this.verticalCharge.draw(graphics, chargeX - 2, chargeY - 6);
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
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AltarEnchantingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 43, 43).addIngredients(recipe.input()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 43).addItemStack(recipe.result().create()).setOutputSlotBackground();
        int slotX = 43;
        int slotY = 11;
        Direction slotDirection = Direction.WEST;
        for (int index = 1; index <= recipe.fuelCount(); index++) {
            builder.addSlot(RecipeIngredientRole.INPUT, slotX, slotY).setStandardSlotBackground().addItemLike(AetherIIItems.AMBROSIUM_SHARD.get());
            if (index % 2 == 0) {
                slotDirection = slotDirection.getCounterClockWise();
            }
            slotX += 32 * slotDirection.getStepX();
            slotY += 32 * slotDirection.getStepZ();
        }
    }
}
