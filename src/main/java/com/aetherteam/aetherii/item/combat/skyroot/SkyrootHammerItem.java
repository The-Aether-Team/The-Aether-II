package com.aetherteam.aetherii.item.combat.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.HammerItem;
import com.aetherteam.aetherii.item.combat.abilities.SkyrootWeapon;
import net.minecraft.world.item.Item;

public class SkyrootHammerItem extends HammerItem implements SkyrootWeapon {
    public SkyrootHammerItem() {
        super(AetherIIItemTiers.SKYROOT, new Item.Properties().attributes(HammerItem.createAttributes(AetherIIItemTiers.SKYROOT, 3, -2.4F)));
    }
}
