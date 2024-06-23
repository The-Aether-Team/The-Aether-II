package com.aetherteam.aetherii.item.tools.arkenium;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import net.minecraft.world.item.HoeItem;

public class ArkeniumTrowelItem extends HoeItem {
    public ArkeniumTrowelItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(HoeItem.createAttributes(AetherIIItemTiers.ARKENIUM, -3, 0)));
    }
}
