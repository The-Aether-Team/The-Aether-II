package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.SkyrootWeapon;

public class SkyrootSpearItem extends TieredSpearItem implements SkyrootWeapon {
    public SkyrootSpearItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.SKYROOT, 3, -2.46F, 0.65F, 0.7F, 0.75F, 5.0F, 14.0F, 10.0F, 15.0F, AetherIIStats.SKYROOT_SPEAR));
    }
}
