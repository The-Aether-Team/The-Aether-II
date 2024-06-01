package com.aetherteam.aetherii.item.tools.zanite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.ZaniteTool;
import net.minecraft.world.item.PickaxeItem;

public class ZanitePickaxeItem extends PickaxeItem implements ZaniteTool {
    public ZanitePickaxeItem() {
        super(AetherIIItemTiers.ZANITE, 1, -2.8F, new Properties());
    }
}
