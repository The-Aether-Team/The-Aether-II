package com.aetherteam.aetherii.item.combat.gravitite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.HammerItem;

public class GravititeHammerItem extends HammerItem {
    public GravititeHammerItem() {
        super(AetherIIItemTiers.GRAVITITE, new Properties().attributes(HammerItem.createAttributes(AetherIIItemTiers.GRAVITITE, 3, -2.4F)));
    }
}
