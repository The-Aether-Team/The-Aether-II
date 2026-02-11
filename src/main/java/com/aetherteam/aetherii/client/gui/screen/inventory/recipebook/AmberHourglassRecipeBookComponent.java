package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.AmberHourglassMenu;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.RecipeBookComponentAccessor;
import com.aetherteam.aetherii.recipe.display.AmberHourglassRecipeDisplay;
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

public class AmberHourglassRecipeBookComponent extends RecipeBookComponent<AmberHourglassMenu> {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/hourglass_filter_enabled"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/hourglass_filter_disabled"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/hourglass_filter_enabled_highlighted"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "recipe_book/hourglass_filter_disabled_highlighted"));
    private static final Component FILTER_NAME = Component.translatable("gui.aether_ii.recipebook.toggleRecipes.restorable");

    public AmberHourglassRecipeBookComponent(AmberHourglassMenu menu, List<TabInfo> tabInfos) {
        super(menu, tabInfos);
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
        return slot.index <= 1;
    }

    @Override
    protected void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipeDisplay, ContextMap contextMap) {
        if (recipeDisplay instanceof AmberHourglassRecipeDisplay amberHourglassRecipeDisplay) {
            ghostSlots.setInput(this.menu.getSlot(0), contextMap, amberHourglassRecipeDisplay.ingredient());
            Slot slot = this.menu.getSlot(1);
            if (slot.getItem().isEmpty()) {
                ghostSlots.setInput(slot, contextMap, amberHourglassRecipeDisplay.fuel());
            }
            ghostSlots.setResult(this.menu.getSlot(2), contextMap, amberHourglassRecipeDisplay.result1());
            ghostSlots.setResult(this.menu.getSlot(3), contextMap, amberHourglassRecipeDisplay.result2());
            ghostSlots.setResult(this.menu.getSlot(4), contextMap, amberHourglassRecipeDisplay.result3());
        }
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection recipeCollection, StackedItemContents stackedItemContents) {
        recipeCollection.selectRecipes(stackedItemContents, (display) -> display instanceof AmberHourglassRecipeDisplay);
    }
}
