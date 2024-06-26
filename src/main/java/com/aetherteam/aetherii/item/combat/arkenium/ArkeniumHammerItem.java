package com.aetherteam.aetherii.item.combat.arkenium;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.HammerItem;

public class ArkeniumHammerItem extends HammerItem {
    public ArkeniumHammerItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(HammerItem.createAttributes(AetherIIItemTiers.ARKENIUM, 3, -2.4F)));
    }
}
