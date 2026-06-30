package com.aetherteam.aetherii.recipe.book;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class RecipeBookCategory {
    private final String name;
    private final Supplier<ItemStack[]> icons;
    private RecipeBookCategories category;

    public RecipeBookCategory(String name, Supplier<ItemStack[]> icons) {
        this.name = name;
        this.icons = icons;
    }

    public RecipeBookCategories get() {
        if (this.category == null) {
            this.category = RecipeBookCategories.create(this.name, this.icons.get());
        }
        return this.category;
    }
}
