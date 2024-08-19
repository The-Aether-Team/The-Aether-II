package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;

public class GravititeSpearItem extends TieredSpearItem {
    public GravititeSpearItem() {
        super(AetherIIItemTiers.GRAVITITE, new Properties().attributes(TieredSpearItem.createAttributes(AetherIIItemTiers.GRAVITITE, 3, -2.4F)));
    }
}
