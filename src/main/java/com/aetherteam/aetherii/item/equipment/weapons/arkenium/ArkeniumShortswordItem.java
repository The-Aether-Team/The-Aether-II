package com.aetherteam.aetherii.item.equipment.weapons.arkenium;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;

public class ArkeniumShortswordItem extends TieredShortswordItem {
    public ArkeniumShortswordItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIToolMaterials.ARKENIUM, 3, -2.4F, AetherIIStats.ARKENIUM_SHORTSWORD));
    }
}
