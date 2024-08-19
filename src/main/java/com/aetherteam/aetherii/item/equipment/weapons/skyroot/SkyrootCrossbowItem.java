package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;

public class SkyrootCrossbowItem extends TieredCrossbowItem {
    public SkyrootCrossbowItem() {
        super(AetherIIItemTiers.SKYROOT, new Properties());
    }

//    @Override
//    public int getProjectileCount(ServerLevel level, ItemStack tool, Entity entity, int projectileCount) { //todo special charge data component check.
//        return 2;
//    }
}
