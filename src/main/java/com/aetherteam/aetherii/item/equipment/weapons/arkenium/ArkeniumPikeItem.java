package com.aetherteam.aetherii.item.equipment.weapons.arkenium;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredPikeItem;

public class ArkeniumPikeItem extends TieredPikeItem {
    public ArkeniumPikeItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.ARKENIUM, 3, -2.4F, AetherIIStats.ARKENIUM_SPEAR));
    }
}
