package com.aetherteam.aetherii.item.equipment.tools.zanite;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.tools.abilities.ZaniteTool;
import net.minecraft.world.item.Item;

public class ZanitePickaxeItem extends Item implements ZaniteTool {
    public ZanitePickaxeItem(Properties properties) {
        super(properties.pickaxe(AetherIIItemTiers.ZANITE, 1.0F, -2.8F));
    }
}
