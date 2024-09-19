package com.aetherteam.aetherii.item.equipment.weapons.holystone;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class HolystoneCrossbowItem extends TieredCrossbowItem {
    public HolystoneCrossbowItem() {
        super(AetherIIItemTiers.HOLYSTONE, new Properties());
    }

    @Override
    public float getProjectileSpread(ServerLevel level, ItemStack tool, Entity entity, float projectileSpread) {
        if (tool.has(AetherIIDataComponents.CROSSBOW_SPECIAL) && Boolean.TRUE.equals(tool.get(AetherIIDataComponents.CROSSBOW_SPECIAL))) {
            return 10.0F;
        }
        return super.getProjectileSpread(level, tool, entity, projectileSpread);
    }

    @Override
    public int getProjectileCount(ServerLevel level, ItemStack tool, Entity entity, int projectileCount) {
        if (tool.has(AetherIIDataComponents.CROSSBOW_SPECIAL) && Boolean.TRUE.equals(tool.get(AetherIIDataComponents.CROSSBOW_SPECIAL))) {
            return 3;
        }
        return super.getProjectileCount(level, tool, entity, projectileCount);
    }
}
