package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIDamageStats;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public class ZaniteSpearItem extends TieredSpearItem implements ZaniteWeapon {
    public ZaniteSpearItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(AetherIIDamageStats.merge(TieredSpearItem.createAttributes(AetherIIItemTiers.ZANITE, 3, -2.4F), AetherIIDamageStats.ZANITE_SPEAR)));
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseDamage(AetherIIAttributes.PIERCE_DAMAGE, stack.getAttributeModifiers(), stack));
        return super.isDamaged(stack);
    }
}
