package com.aetherteam.aetherii.item.tools.holystone;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.HolystoneTool;
import net.minecraft.world.item.AxeItem;

public class HolystoneAxeItem extends AxeItem implements HolystoneTool {
    public HolystoneAxeItem() {
        super(AetherIIItemTiers.HOLYSTONE, 7.0F, -3.2F, new Properties());
    }
}
