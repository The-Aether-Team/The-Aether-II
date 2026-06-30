package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.SkyrootWeapon;

public class SkyrootShortswordItem extends TieredShortswordItem implements SkyrootWeapon {
    public SkyrootShortswordItem(Properties properties) {
        super(AetherIIToolMaterials.SKYROOT, 3, -2.4F, AetherIIStats.SKYROOT_SHORTSWORD, properties);
    }
}
