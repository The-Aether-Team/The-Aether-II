package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.AetherIIDamageStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;

public class GravititeHammerItem extends TieredHammerItem {
    public GravititeHammerItem(Properties properties) {
        super(properties.attributes(AetherIIDamageStats.merge(TieredHammerItem.createAttributes(AetherIIItemTiers.GRAVITITE, 3, -2.4F), AetherIIDamageStats.GRAVITITE_HAMMER)));
    }
}
