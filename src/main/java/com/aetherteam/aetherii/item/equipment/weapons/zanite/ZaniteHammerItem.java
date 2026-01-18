package com.aetherteam.aetherii.item.equipment.weapons.zanite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.ZaniteWeapon;

public class ZaniteHammerItem extends TieredHammerItem implements ZaniteWeapon {
    public ZaniteHammerItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.ZANITE, 3, -2.4F, AetherIIStats.ZANITE_HAMMER));
    }
}
