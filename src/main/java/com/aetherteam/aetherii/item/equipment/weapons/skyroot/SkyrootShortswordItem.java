package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.AetherIIDamageStats;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.SkyrootWeapon;

public class SkyrootShortswordItem extends TieredShortswordItem implements SkyrootWeapon {
    public SkyrootShortswordItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.SKYROOT, 3, -2.4F, AetherIIDamageStats.SKYROOT_SHORTSWORD));
    }
}
