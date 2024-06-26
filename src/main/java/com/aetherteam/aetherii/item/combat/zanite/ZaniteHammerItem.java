package com.aetherteam.aetherii.item.combat.zanite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.HammerItem;
import com.aetherteam.aetherii.item.combat.abilities.ZaniteWeapon;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

public class ZaniteHammerItem extends HammerItem implements ZaniteWeapon {
    public ZaniteHammerItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(HammerItem.createAttributes(AetherIIItemTiers.ZANITE, 3, -2.4F)));
    }

//    @Override
//    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
//        return this.increaseDamage(super.getAttributeModifiers(slot, stack), stack, slot);
//    }

    @Override
    public Triple<Double, Double, Double> getUniqueDamage(ItemStack itemStack, double slashDamage, double impactDamage, double pierceDamage) {
        impactDamage = this.calculateIncrease(itemStack, impactDamage);
        return Triple.of(slashDamage, impactDamage, pierceDamage);
    }
}
