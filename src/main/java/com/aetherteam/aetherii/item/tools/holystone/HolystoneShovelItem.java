package com.aetherteam.aetherii.item.tools.holystone;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.HolystoneTool;
import net.minecraft.world.item.ShovelItem;

public class HolystoneShovelItem extends ShovelItem implements HolystoneTool {
    public HolystoneShovelItem() {
        super(AetherIIItemTiers.HOLYSTONE,  new Properties().attributes(ShovelItem.createAttributes(AetherIIItemTiers.HOLYSTONE, 1.5F, -3.0F)));
    }
}
