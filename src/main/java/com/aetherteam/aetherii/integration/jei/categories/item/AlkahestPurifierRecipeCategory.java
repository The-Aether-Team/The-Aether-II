package com.aetherteam.aetherii.integration.jei.categories.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
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

public class AlkahestPurifierRecipeCategory extends AbstractRecipeCategory<AlkahestPurificationRecipe> {
    public static final RecipeType<AlkahestPurificationRecipe> ALKAHEST_PURIFICATION = RecipeType.create(AetherII.MODID, "alkahest_purification", AlkahestPurificationRecipe.class);
    private final IDrawable background;
    private final IDrawable arrow;
    private final IDrawable bubbles;

    public AlkahestPurifierRecipeCategory(IGuiHelper helper) {
        super(ALKAHEST_PURIFICATION, Component.translatable("gui.aether_ii.jei.alkahest_purifier"), helper.createDrawableItemLike(AetherIIBlocks.ALKAHEST_PURIFIER.get()), 160, 81);
        this.background = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/jei/alkahest_purifier.png"), 0, 0, 122, 79).setTextureSize(122, 79).build();
        this.arrow = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/alkahest_purifier/output_progress.png"), 0, 0, 18, 9).setTextureSize(18, 9).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
        this.bubbles = helper.drawableBuilder(new ResourceLocation(AetherII.MODID, "textures/gui/sprites/container/alkahest_purifier/bubbles.png"), 0, 0, 18, 10).setTextureSize(18, 10).buildAnimated(20, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(AlkahestPurificationRecipe recipe, IRecipeSlotsView view, GuiGraphics graphics, double mouseX, double mouseY) {
        this.background.draw(graphics, 4, 2);
        this.arrow.draw(graphics, 108, 34);
        this.bubbles.draw(graphics, 108, 23);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlkahestPurificationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 68, 6).addIngredients(recipe.ingredient()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 5, 6).addItemLike(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 27, 6).addItemLike(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 27, 61).addItemLike(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 5, 61).addItemLike(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 137, 32).addItemStacks(resultStacks(recipe)).setOutputSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 137, 60).addItemStacks(stacks(recipe.byproducts())).setStandardSlotBackground();
    }

    private static List<ItemStack> resultStacks(AlkahestPurificationRecipe recipe) {
        return recipe.irradiatedResultTemplate().map(template -> List.of(template.create())).orElseGet(() -> stacks(recipe.results()));
    }

    private static List<ItemStack> stacks(OutputEntry.BaseEntry entry) {
        return entry.list().stream().map(ItemStackTemplate::create).filter(stack -> !stack.isEmpty()).toList();
    }
}
