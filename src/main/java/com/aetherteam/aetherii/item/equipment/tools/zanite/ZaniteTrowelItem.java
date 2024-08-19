package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.world.item.HoeItem;

public class ZaniteTrowelItem extends HoeItem implements ZaniteTool {
    public ZaniteTrowelItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(HoeItem.createAttributes(AetherIIItemTiers.ZANITE, -2.0F, -1.0F)));
    }
}
