package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import com.aetherteam.aetherii.recipe.recipes.OutputEntry;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public class AlkahestPurifierRecipeBookComponent extends RecipeBookComponent {
    private static final Component FILTER_NAME = Component.translatable("gui.aether_ii.recipebook.toggleRecipes.purifiable");

    public AlkahestPurifierRecipeBookComponent() {
        super();
    }

    public AlkahestPurifierRecipeBookComponent(AlkahestPurifierMenu menu, List<?> tabs) {
        this();
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        if (!(recipe instanceof AlkahestPurificationRecipe purifierRecipe)) {
            super.setupGhostRecipe(recipe, slots);
            return;
        }

        this.ghostRecipe.setRecipe(recipe);
        List<ItemStack> outputs = purifierRecipe.irradiatedResultTemplate()
                .map(template -> List.of(template.create()))
                .orElseGet(() -> displayableStacks(purifierRecipe.results()));
        this.addOutputGhost(outputs, slots.get(5));
        this.ghostRecipe.addIngredient(purifierRecipe.ingredient(), slots.get(0).x, slots.get(0).y);
        Ingredient fuel = Ingredient.of(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
        for (int i = 1; i <= 4; i++) {
            Slot fuelSlot = slots.get(i);
            if (fuelSlot.getItem().isEmpty()) {
                this.ghostRecipe.addIngredient(fuel, fuelSlot.x, fuelSlot.y);
            }
        }
        this.addOutputGhost(displayableStacks(purifierRecipe.byproducts()), slots.get(6));
    }

    private void addOutputGhost(List<ItemStack> stacks, Slot slot) {
        List<ItemStack> displayableStacks = stacks.stream().filter(stack -> !stack.isEmpty()).toList();
        if (!displayableStacks.isEmpty()) {
            this.ghostRecipe.addIngredient(Ingredient.of(displayableStacks.stream()), slot.x, slot.y);
        }
    }

    private static List<ItemStack> displayableStacks(OutputEntry.BaseEntry output) {
        return output.list().stream()
                .map(ItemStackTemplate::create)
                .filter(stack -> !stack.isEmpty())
                .toList();
    }
}
