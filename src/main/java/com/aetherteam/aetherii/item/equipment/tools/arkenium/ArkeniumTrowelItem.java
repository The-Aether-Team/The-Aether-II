package com.aetherteam.aetherii.item.equipment.tools.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import net.minecraft.world.item.HoeItem;

public class ArkeniumTrowelItem extends HoeItem {
    public ArkeniumTrowelItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(HoeItem.createAttributes(AetherIIItemTiers.ARKENIUM, -2.0F, 0.0F)));
    }
}
