package com.aetherteam.aetherii.item.tools.arkenium;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import net.minecraft.world.item.AxeItem;

public class ArkeniumAxeItem extends AxeItem {
    public ArkeniumAxeItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(AxeItem.createAttributes(AetherIIItemTiers.ARKENIUM, 5.0F, -3.0F)));
    }
}
