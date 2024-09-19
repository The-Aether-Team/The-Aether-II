package com.aetherteam.aetherii.item.equipment.tools.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import net.minecraft.world.item.AxeItem;

public class HolystoneAxeItem extends AxeItem implements HolystoneTool {
    public HolystoneAxeItem() {
        super(AetherIIItemTiers.HOLYSTONE, new Properties().attributes(AxeItem.createAttributes(AetherIIItemTiers.HOLYSTONE, 7.0F, -3.2F)));
    }
}
