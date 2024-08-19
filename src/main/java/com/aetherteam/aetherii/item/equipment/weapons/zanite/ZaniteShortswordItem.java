package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

public class ZaniteShortswordItem extends TieredShortswordItem implements ZaniteWeapon {
    public ZaniteShortswordItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(TieredShortswordItem.createAttributes(AetherIIItemTiers.ZANITE, 3, -2.4F)));
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseDamage(stack.getAttributeModifiers(), stack));
        return super.isDamaged(stack);
    }

    @Override
    public Triple<Double, Double, Double> getUniqueDamage(ItemStack itemStack, double slashDamage, double impactDamage, double pierceDamage) {
        slashDamage = this.calculateIncrease(itemStack, slashDamage);
        return Triple.of(slashDamage, impactDamage, pierceDamage);
    }
}
