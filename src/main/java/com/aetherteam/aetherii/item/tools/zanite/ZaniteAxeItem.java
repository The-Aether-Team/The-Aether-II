package com.aetherteam.aetherii.item.tools.zanite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.ZaniteTool;
import net.minecraft.world.item.AxeItem;

public class ZaniteAxeItem extends AxeItem implements ZaniteTool {
    public ZaniteAxeItem() {
        super(AetherIIItemTiers.ZANITE, 6.0F, -3.1F, new Properties());
    }
}
