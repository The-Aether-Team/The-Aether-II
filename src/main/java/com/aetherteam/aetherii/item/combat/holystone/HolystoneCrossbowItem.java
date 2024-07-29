package com.aetherteam.aetherii.item.combat.holystone;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.AetherIICrossbowItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class HolystoneCrossbowItem extends AetherIICrossbowItem {
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
