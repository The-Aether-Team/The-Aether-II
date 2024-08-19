package com.aetherteam.aetherii.item.equipment.weapons.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;

public class ArkeniumSpearItem extends TieredSpearItem {
    public ArkeniumSpearItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(TieredSpearItem.createAttributes(AetherIIItemTiers.ARKENIUM, 3, -2.4F)));
    }
}
