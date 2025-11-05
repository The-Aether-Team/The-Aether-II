package com.aetherteam.aetherii.item.equipment.weapons.loot;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;

public class DemolitionHammerItem extends TieredHammerItem {
    public DemolitionHammerItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.DEMOLITION, 3, -2.4F, AetherIIStats.DEMOLITION_HAMMER));
    }
}
