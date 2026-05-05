package com.aetherteam.aetherii.item.equipment.weapons.arkenium;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;

public class ArkeniumSpearItem extends TieredSpearItem {
    public ArkeniumSpearItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.ARKENIUM, 3, -2.95F, 0.95F, 0.95F, 0.6F, 2.5F, 11.0F, 6.75F, 11.25F, AetherIIStats.ARKENIUM_SPEAR));
    }
}
