package com.aetherteam.aetherii.recipe.input;

import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class SingleRecipeInputWithRandom extends SimpleContainer {
    private final RandomSource randomSource;

    public SingleRecipeInputWithRandom(ItemStack item, RandomSource randomSource) {
        super(item);
        this.randomSource = randomSource;
    }

    public ItemStack item() {
        return this.getItem(0);
    }

    public RandomSource randomSource() {
        return this.randomSource;
    }
}
