package com.aetherteam.aetherii.item.equipment.weapons.gravitite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;

public class GravititeShortswordItem extends TieredShortswordItem {
    public GravititeShortswordItem() {
        super(AetherIIItemTiers.GRAVITITE, new Properties().attributes(TieredShortswordItem.createAttributes(AetherIIItemTiers.GRAVITITE, 3, -2.4F)));
    }
}
