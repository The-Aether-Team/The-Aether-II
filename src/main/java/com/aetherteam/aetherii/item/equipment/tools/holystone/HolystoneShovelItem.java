package com.aetherteam.aetherii.item.equipment.tools.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import net.minecraft.world.item.ShovelItem;

public class HolystoneShovelItem extends ShovelItem implements HolystoneTool {
    public HolystoneShovelItem() {
        super(AetherIIItemTiers.HOLYSTONE,  new Properties().attributes(ShovelItem.createAttributes(AetherIIItemTiers.HOLYSTONE, 1.5F, -3.0F)));
    }
}
