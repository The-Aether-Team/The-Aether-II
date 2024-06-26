package com.aetherteam.aetherii.item.combat.gravitite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.ShortswordItem;

public class GravititeShortswordItem extends ShortswordItem {
    public GravititeShortswordItem() {
        super(AetherIIItemTiers.GRAVITITE, new Properties().attributes(ShortswordItem.createAttributes(AetherIIItemTiers.GRAVITITE, 3, -2.4F)));
    }
}
