package com.aetherteam.aetherii.item.equipment.weapons.arkenium;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;

public class ArkeniumHammerItem extends TieredHammerItem {
    public ArkeniumHammerItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIToolMaterials.ARKENIUM, 3, -2.4F, AetherIIStats.ARKENIUM_HAMMER));
    }
}
