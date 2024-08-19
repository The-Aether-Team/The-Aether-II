package com.aetherteam.aetherii.item.equipment.weapons.abilities;

import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

public interface UniqueDamage { //todo may be made unnecessary by switching damage types to attributes
    Triple<Double, Double, Double> getUniqueDamage(ItemStack itemStack, double slashDamage, double impactDamage, double pierceDamage);
}
