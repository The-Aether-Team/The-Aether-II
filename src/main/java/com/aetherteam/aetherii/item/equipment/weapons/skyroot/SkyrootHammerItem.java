package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.SkyrootWeapon;
import net.minecraft.world.item.Item;

public class SkyrootHammerItem extends TieredHammerItem implements SkyrootWeapon {
    public SkyrootHammerItem() {
        super(AetherIIItemTiers.SKYROOT, new Item.Properties().attributes(TieredHammerItem.createAttributes(AetherIIItemTiers.SKYROOT, 3, -2.4F)));
    }
}
