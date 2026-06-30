package com.aetherteam.aetherii.integration.jei.categories.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AmberHourglassRecipeCategory extends AbstractRecipeCategory<HourglassRestoringRecipe> {
    public static final RecipeType<HourglassRestoringRecipe> HOURGLASS_RESTORING = RecipeType.create(AetherII.MODID, "hourglass_restoring", HourglassRestoringRecipe.class);
    private final IDrawable background;
    private final IDrawable leftProgress;
    private final IDrawable rightProgress;
    private final IDrawable leftFuel;
    private final IDrawable rightFuel;

    public AmberHourglassRecipeCategory(IGuiHelper helper) {
        super(HOURGLASS_RESTORING, Component.translatable("gui.aether_ii.jei.amber_hourglass"), helper.createDrawableItemLike(AetherIIBlocks.AMBER_HOURGLASS.get()), 110, 110);
        this.background = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/jei/amber_hourglass.png"), 0, 0, 108, 108).setTextureSize(108, 108).build();
        this.leftProgress = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/amber_hourglass/progress_bar_left.png"), 0, 0, 28, 47).setTextureSize(28, 47).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, false);
        this.rightProgress = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/amber_hourglass/progress_bar_right.png"), 0, 0, 28, 47).setTextureSize(28, 47).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, false);
        this.leftFuel = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/amber_hourglass/fuel_bar_left.png"), 0, 0, 4, 20).setTextureSize(4, 20).buildAnimated(400, IDrawableAnimated.StartDirection.TOP, true);
        this.rightFuel = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/amber_hourglass/fuel_bar_right.png"), 0, 0, 4, 20).setTextureSize(4, 20).buildAnimated(400, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void draw(HourglassRestoringRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        this.background.draw(graphics, 1, 1);
        this.leftProgress.draw(graphics, 8, 8);
        this.rightProgress.draw(graphics, 74, 8);
        this.leftFuel.draw(graphics, 38, 45);
        this.rightFuel.draw(graphics, 68, 45);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, HourglassRestoringRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 47, 15).addIngredients(recipe.ingredient()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.INPUT, 47, 47).addItemStacks(createFuels()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 15, 79).addItemStacks(stacks(recipe.results().output1())).setOutputSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 47, 79).addItemStacks(stacks(recipe.results().output2())).setOutputSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 79, 79).addItemStacks(stacks(recipe.results().output3())).setOutputSlotBackground();
    }

    private static List<ItemStack> createFuels() {
        return AetherIIDataMaps.amberHourglassFuelItems().map(ItemStack::new).toList();
    }

    private static List<ItemStack> stacks(OutputEntry.BaseEntry entry) {
        return entry.list().stream().map(ItemStackTemplate::create).filter(stack -> !stack.isEmpty()).toList();
    }
}
