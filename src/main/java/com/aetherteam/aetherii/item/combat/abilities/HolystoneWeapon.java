package com.aetherteam.aetherii.item.combat.abilities;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;

public interface HolystoneWeapon {
    default void dropAmbrosium(LivingEntity target, LivingEntity attacker) {
        if (EquipmentUtil.isFullStrength(attacker)) {
            if (!target.getType().is(AetherIITags.Entities.NO_AMBROSIUM_DROPS) && (target.level().getRandom().nextInt(25) == 0 || target.getHealth() <= 0)) {
                target.spawnAtLocation(AetherIIItems.AMBROSIUM_SHARD.get());
            }
        }
    }
}
