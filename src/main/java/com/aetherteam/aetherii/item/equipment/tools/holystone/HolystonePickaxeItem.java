package com.aetherteam.aetherii.item.equipment.tools.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import net.minecraft.world.item.PickaxeItem;

public class HolystonePickaxeItem extends PickaxeItem implements HolystoneTool {
    public HolystonePickaxeItem() {
        super(AetherIIItemTiers.HOLYSTONE, new Properties().attributes(PickaxeItem.createAttributes(AetherIIItemTiers.HOLYSTONE, 1.0F, -2.8F)));
    }
}
