package com.aetherteam.aetherii.recipe.input;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class SingleRecipeInput extends SimpleContainer {
    public SingleRecipeInput(ItemStack item) {
        super(item);
    }

    public ItemStack item() {
        return this.getItem(0);
    }
}
