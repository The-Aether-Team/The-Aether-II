package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.SkyrootWeapon;
import net.minecraft.world.item.Item;

public class SkyrootSpearItem extends TieredSpearItem implements SkyrootWeapon {
    public SkyrootSpearItem() {
        super(AetherIIItemTiers.SKYROOT, new Item.Properties().attributes(TieredSpearItem.createAttributes(AetherIIItemTiers.SKYROOT, 3, -2.4F)));
    }
}
