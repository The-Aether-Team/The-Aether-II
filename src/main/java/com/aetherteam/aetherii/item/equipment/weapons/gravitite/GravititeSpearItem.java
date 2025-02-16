package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;

public class GravititeSpearItem extends TieredSpearItem {
    public GravititeSpearItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.GRAVITITE, 3, -2.4F, AetherIIStats.GRAVITITE_SPEAR));
    }
}
