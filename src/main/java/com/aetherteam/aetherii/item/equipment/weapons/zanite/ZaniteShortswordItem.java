package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIDamageStats;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public class ZaniteShortswordItem extends TieredShortswordItem implements ZaniteWeapon {
    public ZaniteShortswordItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(AetherIIDamageStats.merge(TieredShortswordItem.createAttributes(AetherIIItemTiers.ZANITE, 3, -2.4F), AetherIIDamageStats.ZANITE_SHORTSWORD)));
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, this.increaseDamage(AetherIIAttributes.SLASH_DAMAGE, stack.getAttributeModifiers(), stack));
        return super.isDamaged(stack);
    }
}
