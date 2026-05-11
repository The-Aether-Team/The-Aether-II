package com.aetherteam.aetherii.item.equipment.weapons.abilities;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public interface HolystoneWeapon {
    default void dropAmbrosium(LivingEntity target, LivingEntity attacker) {
        if (target.level() instanceof ServerLevel serverLevel) {
            if (EquipmentUtil.isFullStrength(attacker)) {
                if (!target.getType().builtInRegistryHolder().is(AetherIITags.EntityTypes.NO_AMBROSIUM_DROPS) && (serverLevel.getRandom().nextInt(25) == 0 || target.getHealth() <= 0)) {
                    target.spawnAtLocation(serverLevel, AetherIIItems.AMBROSIUM_SHARD.get());
                }
            }
        }
    }
}
