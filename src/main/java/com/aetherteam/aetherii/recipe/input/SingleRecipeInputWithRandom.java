package com.aetherteam.aetherii.recipe.input;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record SingleRecipeInputWithRandom(ItemStack item, RandomSource randomSource) implements RecipeInput {
    public ItemStack getItem(int p_345528_) {
        if (p_345528_ != 0) {
            throw new IllegalArgumentException("No item for index " + p_345528_);
        } else {
            return this.item;
        }
    }

    public int size() {
        return 1;
    }
}
