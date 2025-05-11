package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;

public class GravititeShortswordItem extends TieredShortswordItem {
    public GravititeShortswordItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.GRAVITITE, 3, -2.4F, AetherIIStats.GRAVITITE_SHORTSWORD));
    }
}
