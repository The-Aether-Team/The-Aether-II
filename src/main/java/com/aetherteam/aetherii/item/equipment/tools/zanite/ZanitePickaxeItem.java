package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.world.item.PickaxeItem;

public class ZanitePickaxeItem extends PickaxeItem implements ZaniteTool {
    public ZanitePickaxeItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(PickaxeItem.createAttributes(AetherIIItemTiers.ZANITE, 1.0F, -2.8F)));
    }
}
