package com.aetherteam.aetherii.item.combat.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.SpearItem;
import com.aetherteam.aetherii.item.combat.abilities.SkyrootWeapon;
import net.minecraft.world.item.Item;

public class SkyrootSpearItem extends SpearItem implements SkyrootWeapon {
    public SkyrootSpearItem() {
        super(AetherIIItemTiers.SKYROOT, new Item.Properties().attributes(SpearItem.createAttributes(AetherIIItemTiers.SKYROOT, 3, -2.4F)));
    }
}
