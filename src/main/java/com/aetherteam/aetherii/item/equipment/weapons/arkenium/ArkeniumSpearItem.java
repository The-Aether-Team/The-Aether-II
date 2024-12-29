package com.aetherteam.aetherii.item.equipment.weapons.arkenium;

import com.aetherteam.aetherii.AetherIIDamageStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;

public class ArkeniumSpearItem extends TieredSpearItem {
    public ArkeniumSpearItem(Properties properties) {
        super(properties.attributes(AetherIIDamageStats.merge(TieredSpearItem.createAttributes(AetherIIItemTiers.ARKENIUM, 3, -2.4F), AetherIIDamageStats.ARKENIUM_SPEAR)));
    }
}
