package com.aetherteam.aetherii.item.tools.holystone;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.HolystoneTool;
import net.minecraft.world.item.PickaxeItem;

public class HolystonePickaxeItem extends PickaxeItem implements HolystoneTool {
    public HolystonePickaxeItem() {
        super(AetherIIItemTiers.HOLYSTONE, 1, -2.8F, new Properties());
    }
}
