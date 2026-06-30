package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public class AltarRecipeBookComponent extends RecipeBookComponent {
    private static final Component FILTER_NAME = Component.translatable("gui.aether_ii.recipebook.toggleRecipes.enchantable");

    public AltarRecipeBookComponent() {
        super();
    }

    public AltarRecipeBookComponent(AltarMenu menu, List<?> tabs) {
        this();
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        if (!(recipe instanceof AltarEnchantingRecipe altarRecipe)) {
            super.setupGhostRecipe(recipe, slots);
            return;
        }

        this.ghostRecipe.setRecipe(recipe);
        this.ghostRecipe.addIngredient(Ingredient.of(recipe.getResultItem(this.minecraft.level.registryAccess())), slots.get(9).x, slots.get(9).y);
        this.ghostRecipe.addIngredient(altarRecipe.input(), slots.get(0).x, slots.get(0).y);
        Ingredient fuel = Ingredient.of(AetherIITags.Items.ALTAR_FUEL);
        for (int i = 1; i <= altarRecipe.fuelCount() && i < 9; i++) {
            Slot fuelSlot = slots.get(i);
            if (fuelSlot.getItem().isEmpty()) {
                this.ghostRecipe.addIngredient(fuel, fuelSlot.x, fuelSlot.y);
            }
        }
    }
}
