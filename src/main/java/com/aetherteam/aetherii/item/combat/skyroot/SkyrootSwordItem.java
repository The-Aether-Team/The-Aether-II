package com.aetherteam.aetherii.item.combat.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class SkyrootSwordItem extends SwordItem {
    public SkyrootSwordItem() {
        super(AetherIIItemTiers.SKYROOT, 3, -2.4F, new Item.Properties());
    }
}
