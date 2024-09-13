package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class SkyrootCrossbowItem extends TieredCrossbowItem {
    public SkyrootCrossbowItem() {
        super(AetherIIItemTiers.SKYROOT, new Properties());
    }

    @Override
    public int getProjectileCount(ServerLevel level, ItemStack tool, Entity entity, int projectileCount) {
        if (tool.has(AetherIIDataComponents.CROSSBOW_SPECIAL) && Boolean.TRUE.equals(tool.get(AetherIIDataComponents.CROSSBOW_SPECIAL))) {
            return 2;
        }
        return super.getProjectileCount(level, tool, entity, projectileCount);
    }
}
