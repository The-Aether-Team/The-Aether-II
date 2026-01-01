package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.world.item.AxeItem;

public class ZaniteAxeItem extends AxeItem implements ZaniteTool {
    public ZaniteAxeItem(Properties properties) {
        super(AetherIIItemTiers.ZANITE, 6.0F, -3.1F, properties);
    }
}
