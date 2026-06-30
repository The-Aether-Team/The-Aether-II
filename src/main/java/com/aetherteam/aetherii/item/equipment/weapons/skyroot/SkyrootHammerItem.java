package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.SkyrootWeapon;

public class SkyrootHammerItem extends TieredHammerItem implements SkyrootWeapon {
    public SkyrootHammerItem(Properties properties) {
        super(AetherIIToolMaterials.SKYROOT, 3, -2.4F, AetherIIStats.SKYROOT_HAMMER, properties);
    }
}
