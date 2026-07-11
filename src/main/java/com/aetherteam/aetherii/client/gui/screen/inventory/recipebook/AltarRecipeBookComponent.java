package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.RecipeBookComponentAccessor;
import com.aetherteam.aetherii.recipe.display.AltarRecipeDisplay;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.SlotSelectTime;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

import java.util.List;

public class AltarRecipeBookComponent extends RecipeBookComponent<AltarMenu> {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/altar_filter_enabled"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/altar_filter_disabled"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/altar_filter_enabled_highlighted"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "recipe_book/altar_filter_disabled_highlighted"));
    private static final Component FILTER_NAME = Component.translatable("gui.aether_ii.recipebook.toggleRecipes.enchantable");

    public AltarRecipeBookComponent(AltarMenu menu, List<TabInfo> tabs) {
        super(menu, tabs);
        RecipeBookComponentAccessor componentAccessor = (RecipeBookComponentAccessor) this;
        SlotSelectTime slotSelectTime = () -> Mth.floor(componentAccessor.aether_ii$getTime() / 30.0F);
        componentAccessor.aether_ii$setGhostSlots(new AltarGhostSlots(slotSelectTime));
        componentAccessor.aether_ii$setRecipeBookPage(new AetherRecipeBookPage(this, slotSelectTime));
    }

    @Override
    protected WidgetSprites getFilterButtonTextures() {
        return FILTER_SPRITES;
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        return slot.index <= 9;
    }

    @Override
    public void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipeDisplay, ContextMap contextMap) {
        ghostSlots.setResult(this.menu.getSlot(9), contextMap, recipeDisplay.result());
        if (recipeDisplay instanceof AltarRecipeDisplay altarRecipeDisplay) {
            ghostSlots.setInput(this.menu.getSlot(0), contextMap, altarRecipeDisplay.ingredient());
            for (int i = 1; i <= altarRecipeDisplay.fuelCount(); i++) {
                Slot fuelSlot = this.menu.getSlot(i);
                if (fuelSlot.getItem().isEmpty()) {
                    ghostSlots.setInput(fuelSlot, contextMap, altarRecipeDisplay.fuel());
                }
            }
        }
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection recipeCollection, StackedItemContents stackedItemContents) {
        recipeCollection.selectRecipes(stackedItemContents, (display) -> display instanceof AltarRecipeDisplay);
    }
}
