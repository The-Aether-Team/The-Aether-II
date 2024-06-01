package com.aetherteam.aetherii.item.combat.abilities;

import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

public interface UniqueDamage {
    Triple<Double, Double, Double> getUniqueDamage(ItemStack itemStack, double slashDamage, double impactDamage, double pierceDamage);
}
