package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIDamageStats;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public class ZaniteHammerItem extends TieredHammerItem implements ZaniteWeapon {
    public ZaniteHammerItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.ZANITE, 3, -2.4F, AetherIIDamageStats.ZANITE_HAMMER));
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseDamage(AetherIIAttributes.IMPACT_DAMAGE, stack.getAttributeModifiers(), stack));
        return super.isDamaged(stack);
    }
}
