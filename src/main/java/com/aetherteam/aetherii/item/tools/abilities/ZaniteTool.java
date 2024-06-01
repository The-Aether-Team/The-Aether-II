package com.aetherteam.aetherii.item.tools.abilities;

import net.minecraft.world.item.ItemStack;

public interface ZaniteTool {
    default float increaseSpeed(ItemStack stack, float speed) {
        return (float) this.calculateZaniteBuff(stack, speed);
    }

    default double calculateZaniteBuff(ItemStack stack, double baseValue) {
        return baseValue * (2.0 * ((double) stack.getDamageValue()) / ((double) stack.getMaxDamage()) + 0.5);
    }
}
