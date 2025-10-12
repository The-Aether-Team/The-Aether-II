package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import com.aetherteam.aetherii.recipe.display.AlkahestPurifierRecipeDisplay;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

import java.util.List;

public class AlkahestPurifierRecipeBookComponent extends RecipeBookComponent<AlkahestPurifierMenu> {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/purifier_filter_enabled"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/purifier_filter_disabled"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/purifier_filter_enabled_highlighted"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/purifier_filter_disabled_highlighted"));
    private static final Component FILTER_NAME = Component.translatable("gui.aether_ii.recipebook.toggleRecipes.purifiable");

    public AlkahestPurifierRecipeBookComponent(AlkahestPurifierMenu menu, List<TabInfo> tabs) {
        super(menu, tabs);
    }

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        return slot.index == 5;
    }

    @Override
    public void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipeDisplay, ContextMap contextMap) {
        ghostSlots.setResult(this.menu.getSlot(5), contextMap, recipeDisplay.result());
        if (recipeDisplay instanceof AlkahestPurifierRecipeDisplay altarRecipeDisplay) {
            ghostSlots.setInput(this.menu.getSlot(0), contextMap, altarRecipeDisplay.ingredient());
            ghostSlots.setInput(this.menu.getSlot(6), contextMap, altarRecipeDisplay.byproduct());
        }
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection recipeCollection, StackedItemContents stackedItemContents) {
        recipeCollection.selectRecipes(stackedItemContents, (display) -> display instanceof AlkahestPurifierRecipeDisplay);
    }
}
