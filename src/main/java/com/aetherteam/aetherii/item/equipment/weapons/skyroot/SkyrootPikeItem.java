package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredPikeItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.SkyrootWeapon;

public class SkyrootPikeItem extends TieredPikeItem implements SkyrootWeapon {
    public SkyrootPikeItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIToolMaterials.SKYROOT, 3, -2.4F, AetherIIStats.SKYROOT_PIKE));
    }
}
