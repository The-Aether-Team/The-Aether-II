package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.world.item.ShovelItem;

public class ZaniteShovelItem extends ShovelItem implements ZaniteTool {
    public ZaniteShovelItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(ShovelItem.createAttributes(AetherIIItemTiers.ZANITE, 1.5F, -3.0F)));
    }
}
