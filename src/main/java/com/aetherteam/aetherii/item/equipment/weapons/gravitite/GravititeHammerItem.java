package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;

public class GravititeHammerItem extends TieredHammerItem {
    public GravititeHammerItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.GRAVITITE, 3, -2.4F, AetherIIStats.GRAVITITE_HAMMER));
    }
}
