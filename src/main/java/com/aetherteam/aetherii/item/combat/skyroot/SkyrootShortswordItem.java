package com.aetherteam.aetherii.item.combat.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.ShortswordItem;
import com.aetherteam.aetherii.item.combat.abilities.SkyrootWeapon;
import net.minecraft.world.item.Item;

public class SkyrootShortswordItem extends ShortswordItem implements SkyrootWeapon {
    public SkyrootShortswordItem() {
        super(AetherIIItemTiers.SKYROOT, new Item.Properties().attributes(ShortswordItem.createAttributes(AetherIIItemTiers.SKYROOT, 3, -2.4F)));
    }
}
