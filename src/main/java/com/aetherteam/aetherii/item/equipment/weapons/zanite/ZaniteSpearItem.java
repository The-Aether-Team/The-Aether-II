package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

public class ZaniteSpearItem extends TieredSpearItem implements ZaniteWeapon {
    public ZaniteSpearItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(TieredSpearItem.createAttributes(AetherIIItemTiers.ZANITE, 3, -2.4F)));
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseDamage(stack.getAttributeModifiers(), stack));
        return super.isDamaged(stack);
    }

    @Override
    public Triple<Double, Double, Double> getUniqueDamage(ItemStack itemStack, double slashDamage, double impactDamage, double pierceDamage) {
        pierceDamage = this.calculateIncrease(itemStack, pierceDamage);
        return Triple.of(slashDamage, impactDamage, pierceDamage);
    }
}
