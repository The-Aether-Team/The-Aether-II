package com.aetherteam.aetherii.item.combat.arkenium;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.SpearItem;

public class ArkeniumSpearItem extends SpearItem {
    public ArkeniumSpearItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(SpearItem.createAttributes(AetherIIItemTiers.ARKENIUM, 3, -2.4F)));
    }
}
