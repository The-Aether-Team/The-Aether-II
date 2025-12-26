package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.RecipeBookComponentAccessor;
import com.aetherteam.aetherii.recipe.display.AlkahestPurifierRecipeDisplay;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.SlotSelectTime;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
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
        RecipeBookComponentAccessor componentAccessor = (RecipeBookComponentAccessor) this;
        SlotSelectTime slotSelectTime = () -> Mth.floor(componentAccessor.aether_ii$getTime() / 30.0F);
        componentAccessor.aether_ii$setRecipeBookPage(new AetherRecipeBookPage(this, slotSelectTime));
    }

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        return slot.index <= 6;
    }

    @Override
    public void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipeDisplay, ContextMap contextMap) {
        ghostSlots.setResult(this.menu.getSlot(5), contextMap, recipeDisplay.result());
        if (recipeDisplay instanceof AlkahestPurifierRecipeDisplay purifierRecipeDisplay) {
            ghostSlots.setInput(this.menu.getSlot(0), contextMap, purifierRecipeDisplay.ingredient());
            ghostSlots.setInput(this.menu.getSlot(1), contextMap, purifierRecipeDisplay.fuel());
            ghostSlots.setInput(this.menu.getSlot(6), contextMap, purifierRecipeDisplay.byproduct());
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
