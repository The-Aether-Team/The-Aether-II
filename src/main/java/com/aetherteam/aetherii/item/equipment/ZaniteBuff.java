package com.aetherteam.aetherii.item.equipment;

import net.minecraft.world.item.ItemStack;

public interface ZaniteBuff {
    default double calculateZaniteBuff(ItemStack stack, double baseValue) {
        return baseValue * (2.0 * ((double) stack.getDamageValue()) / ((double) stack.getMaxDamage()) + 0.5);
    }
}
