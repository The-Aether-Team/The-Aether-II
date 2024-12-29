package com.aetherteam.aetherii.item.equipment.tools.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.HolystoneTool;
import net.minecraft.world.item.HoeItem;

public class HolystoneTrowelItem extends HoeItem implements HolystoneTool {
    public HolystoneTrowelItem(Properties properties) {
        super(AetherIIItemTiers.HOLYSTONE, -1.0F, -2.0F, properties);
    }
}
