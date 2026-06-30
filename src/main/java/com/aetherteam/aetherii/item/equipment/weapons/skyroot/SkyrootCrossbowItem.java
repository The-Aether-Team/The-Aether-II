package com.aetherteam.aetherii.item.equipment.weapons.skyroot;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class SkyrootCrossbowItem extends TieredCrossbowItem {
    public SkyrootCrossbowItem(Properties properties) {
        super(AetherIIToolMaterials.SKYROOT, properties);
    }

    @Override
    public int getProjectileCount(ServerLevel level, ItemStack tool, Entity entity, int projectileCount) {
        if (AetherIIDataAttachments.get(entity, AetherIIDataAttachments.ABILITY_BEHAVIOR).isCrossbowSpecial()) {
            return 2;
        }
        return super.getProjectileCount(level, tool, entity, projectileCount);
    }
}
