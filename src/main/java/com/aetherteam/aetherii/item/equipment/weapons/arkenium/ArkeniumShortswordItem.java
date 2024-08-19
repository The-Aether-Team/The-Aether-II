package com.aetherteam.aetherii.item.equipment.weapons.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;

public class ArkeniumShortswordItem extends TieredShortswordItem {
    public ArkeniumShortswordItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(TieredShortswordItem.createAttributes(AetherIIItemTiers.ARKENIUM, 3, -2.4F)));
    }
}
