package com.aetherteam.aetherii.item.combat.skyroot;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.AetherIICrossbowItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class SkyrootCrossbowItem extends AetherIICrossbowItem {
    public SkyrootCrossbowItem() {
        super(AetherIIItemTiers.SKYROOT, new Properties());
    }

//    @Override
//    public int getProjectileCount(ServerLevel level, ItemStack tool, Entity entity, int projectileCount) { //todo special charge data component check.
//        return 2;
//    }
}
