package com.aetherteam.aetherii.item.combat.zanite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.ShortswordItem;
import com.aetherteam.aetherii.item.combat.abilities.ZaniteWeapon;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

public class ZaniteShortswordItem extends ShortswordItem implements ZaniteWeapon {
    public ZaniteShortswordItem() {
        super(AetherIIItemTiers.ZANITE, 3, -2.4F, new Properties());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return this.increaseDamage(super.getAttributeModifiers(slot, stack), stack, slot);
    }

    @Override
    public Triple<Double, Double, Double> getUniqueDamage(ItemStack itemStack, double slashDamage, double impactDamage, double pierceDamage) {
        slashDamage = this.calculateIncrease(itemStack, slashDamage);
        return Triple.of(slashDamage, impactDamage, pierceDamage);
    }
}
