package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.AetherIIDamageStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredSpearItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.SkyrootWeapon;

public class SkyrootSpearItem extends TieredSpearItem implements SkyrootWeapon {
    public SkyrootSpearItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.SKYROOT, 3, -2.4F, AetherIIDamageStats.SKYROOT_SPEAR));
    }
}
