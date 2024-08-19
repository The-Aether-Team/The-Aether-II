package com.aetherteam.aetherii.item.equipment.tools.arkenium;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import net.minecraft.world.item.PickaxeItem;

public class ArkeniumPickaxeItem extends PickaxeItem {
    public ArkeniumPickaxeItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(PickaxeItem.createAttributes(AetherIIItemTiers.ARKENIUM, 1, -2.8F)));
    }
}
