package com.aetherteam.aetherii.item.components;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public record ChargedProjectiles(List<ItemStack> itemCopies) {
    public static final ChargedProjectiles EMPTY = new ChargedProjectiles(List.of());

    public static ChargedProjectiles of(ItemStack stack) {
        return stack.isEmpty() ? EMPTY : new ChargedProjectiles(List.of(stack.copy()));
    }

    public static ChargedProjectiles of(ItemStackTemplate template) {
        return of(template.create());
    }

    public boolean isEmpty() {
        return this.itemCopies.isEmpty();
    }
}
