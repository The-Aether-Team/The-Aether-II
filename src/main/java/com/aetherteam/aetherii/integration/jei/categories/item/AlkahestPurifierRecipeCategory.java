package com.aetherteam.aetherii.integration.jei.categories.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.stream.Collectors;

public class AlkahestPurifierRecipeCategory extends AbstractRecipeCategory<AlkahestPurificationRecipe> {
    public static final IRecipeType<AlkahestPurificationRecipe> ALKAHEST_PURIFICATION = IRecipeType.create(AetherII.MODID, "alkahest_purification", AlkahestPurificationRecipe.class);
    private final IDrawable background;
    private final IDrawable arrow;
    private final IDrawable bubbles;

    public AlkahestPurifierRecipeCategory(IGuiHelper helper) {
        super(ALKAHEST_PURIFICATION, Component.translatable("gui.aether_ii.jei.alkahest_purifier"), helper.createDrawableItemLike(AetherIIBlocks.ALKAHEST_PURIFIER), 160, 81);
        this.background = helper.drawableBuilder(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/jei/alkahest_purifier.png"), 0, 0, 122, 79).setTextureSize(122, 79).build();
        this.arrow = helper.drawableBuilder(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/sprites/container/alkahest_purifier/output_progress.png"), 0, 0, 18, 9).setTextureSize(18, 9).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
        this.bubbles = helper.drawableBuilder(Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/gui/sprites/container/alkahest_purifier/bubbles.png"), 0, 0, 18, 10).setTextureSize(18, 10).buildAnimated(20, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(AlkahestPurificationRecipe recipe, IRecipeSlotsView view, GuiGraphicsExtractor graphics, double mouseX, double mouseY) {
        this.background.draw(graphics, 4, 2);
        this.arrow.draw(graphics, 108, 34);
        this.bubbles.draw(graphics, 108, 23);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlkahestPurificationRecipe recipe, IFocusGroup focuses) {
        SlotDisplay resultDisplay = new SlotDisplay.Composite(recipe.results().list().stream().map(SlotDisplay.ItemStackSlotDisplay::new).collect(Collectors.toUnmodifiableList()));
        HolderSet<Item> ingredients = recipe.ingredient().getValues();
        Holder<Item> item = ingredients.get(0);
        if (item.is(AetherIITags.Items.IRRADIATED_ITEM)) {
            Identifier location = item.getKey().identifier().withSuffix("_result");
            resultDisplay = new SlotDisplay.ItemStackSlotDisplay(new ItemStackTemplate(item, 1, DataComponentPatch.builder()
                    .set(DataComponents.ITEM_MODEL, location)
                    .set(DataComponents.ITEM_NAME, Component.translatable(Util.makeDescriptionId("item", location)))
                    .build()
            ));
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 68, 6).add(recipe.ingredient()).setStandardSlotBackground();
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 5, 6).add(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 27, 6).add(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 27, 61).add(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 5, 61).add(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 137, 32).add(resultDisplay).setOutputSlotBackground();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 137, 60).add(new SlotDisplay.Composite(recipe.byproducts().list().stream().map(SlotDisplay.ItemStackSlotDisplay::new).collect(Collectors.toUnmodifiableList()))).setStandardSlotBackground();
    }
}
