package com.aetherteam.aetherii.item.equipment.weapons.arkenium;

import com.aetherteam.aetherii.AetherIIDamageStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;

public class ArkeniumHammerItem extends TieredHammerItem {
    public ArkeniumHammerItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(AetherIIDamageStats.merge(TieredHammerItem.createAttributes(AetherIIItemTiers.ARKENIUM, 3, -2.4F), AetherIIDamageStats.ARKENIUM_HAMMER)));
    }
}
