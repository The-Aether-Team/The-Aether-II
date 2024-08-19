package com.aetherteam.aetherii.item.equipment.tools.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import net.minecraft.world.item.ShovelItem;

public class ArkeniumShovelItem extends ShovelItem {
    public ArkeniumShovelItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(ShovelItem.createAttributes(AetherIIItemTiers.ARKENIUM, 1.5F, -3.0F)));
    }
}
