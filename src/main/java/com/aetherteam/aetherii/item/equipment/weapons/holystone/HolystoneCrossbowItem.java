package com.aetherteam.aetherii.item.equipment.weapons.holystone;

import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;

public class HolystoneCrossbowItem extends TieredCrossbowItem {
    public HolystoneCrossbowItem() {
        super(AetherIIItemTiers.HOLYSTONE, new Properties());
    }

//    @Override
//    public float getProjectileSpread(ServerLevel level, ItemStack tool, Entity entity, float projectileSpread) { //todo special charge data component check.
//        return 10.0F;
//    }
//
//    @Override
//    public int getProjectileCount(ServerLevel level, ItemStack tool, Entity entity, int projectileCount) { //todo special charge data component check.
//        return 3;
//    }
}
