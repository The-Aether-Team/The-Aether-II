package com.aetherteam.aetherii.item.tools.zanite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.ZaniteTool;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;

public class ZanitePickaxeItem extends PickaxeItem implements ZaniteTool {
    public ZanitePickaxeItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(PickaxeItem.createAttributes(AetherIIItemTiers.ZANITE, 1.0F, -2.8F)));
    }
}
