package com.aetherteam.aetherii.recipe.display;

import net.minecraft.world.item.ItemStack;

public interface DisplayContentsFactory<T> {
    interface ForStacks<T> extends DisplayContentsFactory<T> {
        T forStack(ItemStack stack);
    }
}
