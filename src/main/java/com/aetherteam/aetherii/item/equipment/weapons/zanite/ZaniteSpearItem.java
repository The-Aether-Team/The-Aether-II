package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;

public class ZaniteSpearItem extends TieredSpearItem implements ZaniteWeapon {
    public ZaniteSpearItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.ZANITE, 3, -2.4F, AetherIIStats.ZANITE_SPEAR));
    }
}
