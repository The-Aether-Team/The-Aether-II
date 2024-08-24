package com.aetherteam.aetherii.item.equipment.tools.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import net.minecraft.world.item.HoeItem;

public class HolystoneTrowelItem extends HoeItem implements HolystoneTool {
    public HolystoneTrowelItem() {
        super(AetherIIItemTiers.HOLYSTONE, new Properties().attributes(HoeItem.createAttributes(AetherIIItemTiers.HOLYSTONE, -1.0F, -2.0F)));
    }
}
