package com.aetherteam.aetherii.item.equipment.tools.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import net.minecraft.world.item.ShovelItem;

public class HolystoneShovelItem extends ShovelItem implements HolystoneTool {
    public HolystoneShovelItem(Properties properties) {
        super(AetherIIItemTiers.HOLYSTONE, 1.5F, -3.0F, properties);
    }
}
