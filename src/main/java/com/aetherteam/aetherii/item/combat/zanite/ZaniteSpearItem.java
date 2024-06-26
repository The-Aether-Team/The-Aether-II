package com.aetherteam.aetherii.item.combat.zanite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.SpearItem;
import com.aetherteam.aetherii.item.combat.abilities.ZaniteWeapon;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

public class ZaniteSpearItem extends SpearItem implements ZaniteWeapon {
    public ZaniteSpearItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(SpearItem.createAttributes(AetherIIItemTiers.ZANITE, 3, -2.4F)));
    }

//    @Override
//    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
//        return this.increaseDamage(super.getAttributeModifiers(slot, stack), stack, slot);
//    }

    @Override
    public Triple<Double, Double, Double> getUniqueDamage(ItemStack itemStack, double slashDamage, double impactDamage, double pierceDamage) {
        pierceDamage = this.calculateIncrease(itemStack, pierceDamage);
        return Triple.of(slashDamage, impactDamage, pierceDamage);
    }
}
