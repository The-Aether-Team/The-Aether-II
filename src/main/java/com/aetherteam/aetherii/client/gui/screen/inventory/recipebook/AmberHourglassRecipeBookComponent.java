package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.inventory.menu.AmberHourglassMenu;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public class AmberHourglassRecipeBookComponent extends RecipeBookComponent {
    private static final Component FILTER_NAME = Component.translatable("gui.aether_ii.recipebook.toggleRecipes.restorable");

    public AmberHourglassRecipeBookComponent() {
        super();
    }

    public AmberHourglassRecipeBookComponent(AmberHourglassMenu menu, List<?> tabs) {
        this();
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        if (!(recipe instanceof HourglassRestoringRecipe hourglassRecipe)) {
            super.setupGhostRecipe(recipe, slots);
            return;
        }

        this.ghostRecipe.setRecipe(recipe);
        this.addOutputGhost(hourglassRecipe.results().output1(), slots.get(2));
        this.ghostRecipe.addIngredient(hourglassRecipe.ingredient(), slots.get(0).x, slots.get(0).y);
        Slot fuelSlot = slots.get(1);
        if (fuelSlot.getItem().isEmpty()) {
            this.ghostRecipe.addIngredient(Ingredient.of(AetherIIDataMaps.amberHourglassFuelItems().map(ItemStack::new)), fuelSlot.x, fuelSlot.y);
        }
        this.addOutputGhost(hourglassRecipe.results().output2(), slots.get(3));
        this.addOutputGhost(hourglassRecipe.results().output3(), slots.get(4));
    }

    private void addOutputGhost(OutputEntry.BaseEntry output, Slot slot) {
        List<ItemStack> stacks = output.list().stream()
                .map(ItemStackTemplate::create)
                .filter(stack -> !stack.isEmpty())
                .toList();
        if (!stacks.isEmpty()) {
            this.ghostRecipe.addIngredient(Ingredient.of(stacks.stream()), slot.x, slot.y);
        }
    }
}
