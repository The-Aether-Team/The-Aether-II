package com.aetherteam.aetherii.item.equipment.weapons.holystone;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class HolystoneCrossbowItem extends TieredCrossbowItem {
    public HolystoneCrossbowItem(Properties properties) {
        super(AetherIIToolMaterials.HOLYSTONE, properties);
    }

    @Override
    public float getProjectileSpread(ServerLevel level, ItemStack tool, Entity entity, float projectileSpread) {
        if (AetherIIDataAttachments.get(entity, AetherIIDataAttachments.ABILITY_BEHAVIOR).isCrossbowSpecial()) {
            return 10.0F;
        }
        return super.getProjectileSpread(level, tool, entity, projectileSpread);
    }

    @Override
    public int getProjectileCount(ServerLevel level, ItemStack tool, Entity entity, int projectileCount) {
        if (AetherIIDataAttachments.get(entity, AetherIIDataAttachments.ABILITY_BEHAVIOR).isCrossbowSpecial()) {
            return 3;
        }
        return super.getProjectileCount(level, tool, entity, projectileCount);
    }
}
