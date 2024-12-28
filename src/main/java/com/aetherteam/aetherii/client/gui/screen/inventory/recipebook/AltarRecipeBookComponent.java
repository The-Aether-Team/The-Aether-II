package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.aetherteam.aetherii.recipe.display.AltarRecipeDisplay;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.FurnaceRecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AltarRecipeBookComponent extends RecipeBookComponent<AltarMenu> {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled_highlighted"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled_highlighted")
    );
    private static final Component FILTER_NAME = Component.translatable("gui.aether.recipebook.toggleRecipes.enchantable");

    public AltarRecipeBookComponent(AltarMenu menu, List<TabInfo> tabs) {
        super(menu, tabs);
    }

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        return slot.index == 9;
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
