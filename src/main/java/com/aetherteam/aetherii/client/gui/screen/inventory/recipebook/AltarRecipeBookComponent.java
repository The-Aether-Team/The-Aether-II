package com.aetherteam.aetherii.client.gui.screen.inventory.recipebook;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AltarRecipeBookComponent extends RecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            new ResourceLocation("recipe_book/furnace_filter_enabled"),
            new ResourceLocation("recipe_book/furnace_filter_disabled"),
            new ResourceLocation("recipe_book/furnace_filter_enabled_highlighted"),
            new ResourceLocation("recipe_book/furnace_filter_disabled_highlighted")
    );
    private static final Component FILTER_NAME = Component.translatable("gui.aether.recipebook.toggleRecipes.enchantable");
    @Nullable
    private Ingredient fuels;

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.index < this.menu.getSize()) {
            this.ghostRecipe.clear();
        }
    }

    @Override
    public void setupGhostRecipe(RecipeHolder<?> recipe, List<Slot> slots) {
        ItemStack output = recipe.value().getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(recipe);
        this.ghostRecipe.addIngredient(Ingredient.of(output), slots.get(9).x, slots.get(9).y);
        NonNullList<Ingredient> ingredientsList = recipe.value().getIngredients();

        if (recipe.value() instanceof AltarEnchantingRecipe altarEnchantingRecipe) {
            for (int i = 1; i <= altarEnchantingRecipe.getFuelCount(); i++) {
                Slot fuelSlot = slots.get(i);
                if (fuelSlot.getItem().isEmpty()) {
                    if (this.fuels == null) {
                        this.fuels = Ingredient.of(this.getFuelItems().stream().map(ItemStack::new));
                    }
                    this.ghostRecipe.addIngredient(this.fuels, fuelSlot.x, fuelSlot.y);
                }
            }
        }

        Iterator<Ingredient> ingredients = ingredientsList.iterator();
        for (int i = 0; i < 2; ++i) {
            if (!ingredients.hasNext()) {
                return;
            }
            Ingredient ingredient = ingredients.next();
            if (!ingredient.isEmpty()) {
                Slot slot1 = slots.get(i);
                this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
            }
        }
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    protected Set<Item> getFuelItems() {
        return StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(AetherIITags.Items.ALTAR_FUEL).spliterator(), false).map(Holder::value).collect(Collectors.toSet());
    }
}
