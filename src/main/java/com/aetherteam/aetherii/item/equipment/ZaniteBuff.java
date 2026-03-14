package com.aetherteam.aetherii.item.equipment;

import net.minecraft.world.item.ItemStack;

public interface ZaniteBuff {
    /// Added Multiplier buff (1 => + 100%, .5 => + 50%, etc.)
    double buff = 1.0;

    default double calculateZaniteBuff(ItemStack stack) {
        return buff * stack.getDamageValue() / (double) stack.getMaxDamage();
    }
}
