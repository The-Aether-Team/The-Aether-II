package com.aetherteam.aetherii.item.equipment.weapons.arkenium;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;

public class ArkeniumShortswordItem extends TieredShortswordItem {
    public ArkeniumShortswordItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.ARKENIUM, 3, -2.4F, AetherIIStats.ARKENIUM_SHORTSWORD));
    }
}
