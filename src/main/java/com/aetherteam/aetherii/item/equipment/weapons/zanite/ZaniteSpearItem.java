package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class ZaniteSpearItem extends TieredSpearItem implements ZaniteWeapon {
    public ZaniteSpearItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.ZANITE, 3, -2.95F, 0.95F, 0.95F, 0.6F, 2.5F, 11.0F, 6.75F, 11.25F, AetherIIStats.ZANITE_SPEAR));
    }

    @Override
    public Holder<Attribute> getDamageType() {
        return AetherIIAttributes.JAB_DAMAGE;
    }
}
